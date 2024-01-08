package com.mamun.onlinebooklibrary.service.impl;

import com.mamun.onlinebooklibrary.entity.*;
import com.mamun.onlinebooklibrary.exception.IllegalOperationException;
import com.mamun.onlinebooklibrary.exception.ReturnException;
import com.mamun.onlinebooklibrary.model.ReviewDto;
import com.mamun.onlinebooklibrary.repository.*;
import com.mamun.onlinebooklibrary.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BorrowedRepository borrowedRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ReserveRepository reserveRepository;

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();
    }

    private Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return userRepository.findByEmail(authentication.getName());
    }

    @Override
    public void borrowBook(int bookId) {
        if (bookId < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Optional<User> optionalUser = userRepository.findByEmail(authentication.getName());
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            if (borrowedRepository.findByUserAndStatus(optionalUser.get(), "Borrowed").isEmpty()) {
                if (optionalBook.get().getStatus().equals(Book.Status.Available)) {
                    Borrowed borrowed = new Borrowed();
                    borrowed.setBook(optionalBook.get());
                    borrowed.setUser(optionalUser.get());
                    optionalBook.get().setStatus(Book.Status.Borrowed);
                    bookRepository.save(optionalBook.get());
                    borrowedRepository.save(borrowed);
                    return;
                } else {
                    throw new IllegalOperationException("Error Borrowing! The book is not available.");
                }
            } else {
                throw new IllegalOperationException("Error! Please return the borrowed book first.");
            }
        }
        if (optionalUser.isEmpty() && optionalBook.isEmpty()) {
            throw new EmptyResultDataAccessException("User, Book", 1);
        }
        if (optionalUser.isEmpty()) {
            throw new EmptyResultDataAccessException("User", 1);
        }
        throw new EmptyResultDataAccessException("Book", 1);
    }

    @Override
    public void returnBook(int bookId) {
        if(bookId<0){
            throw new IllegalArgumentException(("Book ID"));
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            User user = optionalUser.get();
            Book book = optionalBook.get();
            Optional<Borrowed> borrowedOptional = borrowedRepository.findByUserAndBookAndStatus(user, book, "Borrowed");
            if (borrowedOptional.isPresent()) {
                borrowedOptional.get().setStatus("Returned");
                borrowedOptional.get().setReturnDate(Date.valueOf(LocalDate.now()));
                book.setStatus(Book.Status.Available);
                bookRepository.save(book);
                borrowedRepository.save(borrowedOptional.get());
                List<Reserve> reserveList = reserveRepository.findByBookAndBookStatus(book, Reserve.BookStatus.Borrowed);
                for (Reserve reserve : reserveList) {
                    reserve.setBookStatus(Reserve.BookStatus.Available);
                    reserveRepository.save(reserve);
                }
                return;
            } else {
                throw new ReturnException();
            }
        }
        if (optionalUser.isEmpty() && optionalBook.isEmpty()) {
            throw new EmptyResultDataAccessException("User, Book", 1);
        }
        if (optionalUser.isEmpty()) {
            throw new EmptyResultDataAccessException("User", 1);
        }
        throw new EmptyResultDataAccessException("Book", 1);
    }

    @Override
    public void createReview(ReviewDto reviewDto, int bookId) {
        if (bookId < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            Review review = new Review();
            review.setBook(optionalBook.get());
            review.setUser(optionalUser.get());
            review.setComment(reviewDto.getComment());
            review.setRating(reviewDto.getRating());
            reviewRepository.save(review);
            return;
        }
        if (optionalUser.isEmpty() && optionalBook.isEmpty()) {
            throw new EmptyResultDataAccessException("User, Book", 1);
        }
        if (optionalUser.isEmpty()) {
            throw new EmptyResultDataAccessException("User", 1);
        }
        throw new EmptyResultDataAccessException("Book", 1);
    }

    @Override
    public void updateReview(ReviewDto reviewDto, int bookId, int reviewId) {
        if (bookId < 0 && reviewId < 0) {
            throw new IllegalArgumentException("Book Id, Review Id");
        }
        if (bookId < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        if (reviewId < 0) {
            throw new IllegalArgumentException("Review Id");
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalUser.isPresent() && optionalBook.isPresent() && optionalReview.isPresent()) {
            if (reviewDto.getComment() != null) {
                optionalReview.get().setComment(reviewDto.getComment());
            }
            if (reviewDto.getRating() != null) {
                optionalReview.get().setRating(reviewDto.getRating());
            }
            reviewRepository.save(optionalReview.get());
        } else {
            throw new EmptyResultDataAccessException("User or Book or Review", 1);
        }
    }

    @Override
    public void deleteReview(int bookId, int reviewId) {
        if(bookId<0 || reviewId<0){
            throw new IllegalArgumentException("Book ID or Review ID");
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        Optional<Review> optionalReview = reviewRepository.findById(reviewId);
        if (optionalUser.isPresent() && optionalBook.isPresent() && optionalReview.isPresent()) {
            reviewRepository.deleteById(reviewId);
        }
    }

    @Override
    public List<Review> getReviewsByBookId(int bookId) {
        if (bookId < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        return optionalBook
                .map(book -> reviewRepository.findByBook(book))
                .orElseThrow(() -> new EmptyResultDataAccessException("Book", 1));
    }

    @Override
    public void createReservation(int bookId) {
        if (bookId < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            if (optionalBook.get().getStatus().equals(Book.Status.Available)) {
                throw new IllegalOperationException("Error Reserving! The book is available to borrow");
            }
            if (optionalBook.get().getStatus().equals(Book.Status.Deleted)) {
                throw new IllegalOperationException("Error Reserving! The book is not available");
            }
            Reserve reserve = new Reserve();
            reserve.setUser(optionalUser.get());
            reserve.setBook(optionalBook.get());
            reserveRepository.save(reserve);
            return;
        }
        throw new EmptyResultDataAccessException("User or Book", 1);
    }

    @Override
    public void cancelReservation(int bookId) {
        if(bookId<0){
            throw new IllegalArgumentException("Book Id");
        }
        Optional<User> optionalUser = getUser();
        Optional<Book> optionalBook = bookRepository.findByBookId(bookId);
        if (optionalUser.isPresent() && optionalBook.isPresent()) {
            User user = optionalUser.get();
            Book book = optionalBook.get();
            Optional<Reserve> optionalReserve = reserveRepository.findByUserAndBook(user, book);
            optionalReserve.ifPresent(reserve -> reserve.setReservationStatus(Reserve.ReservationStatus.Cancelled));
            return;
        }
        throw new EmptyResultDataAccessException("User or Book", 1);
    }

    @Override
    public List<Borrowed> borrowHistory(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("User Id");
        }
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        return optionalUser
                .map(user -> borrowedRepository.findByUser(user))
                .orElseThrow(() -> new EmptyResultDataAccessException("User", 1));
    }
}

