package com.mamun.onlinebooklibrary.service.impl;

import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.Borrowed;
import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.exception.IllegalApiAccessException;
import com.mamun.onlinebooklibrary.model.BookAdminResponseDto;
import com.mamun.onlinebooklibrary.model.BookDto;
import com.mamun.onlinebooklibrary.repository.BookRepository;
import com.mamun.onlinebooklibrary.repository.BorrowedRepository;
import com.mamun.onlinebooklibrary.repository.UserRepository;
import com.mamun.onlinebooklibrary.service.services.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CommonServiceImpl implements CommonService {
    @Autowired
    private BorrowedRepository borrowedRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;

    @Override
    public Set<?> borrowedBooksByUser(int userId) {
        if(userId<0){
            throw new IllegalArgumentException("User ID");
        }
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<User> optionalTokenUser = getUser();
        if (userId != optionalTokenUser.get().getUserId() && optionalTokenUser.get().getRole().equals(User.Role.CUSTOMER)) {
            throw new IllegalApiAccessException();
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Borrowed> borrowedList = borrowedRepository.findByUser(user);
            if (optionalTokenUser.get().getRole().equals(User.Role.CUSTOMER)) {
                System.out.println("I am here!");
                Set<BookDto> borrowedBookSet = new HashSet<>();
                for (Borrowed borrowedBook : borrowedList) {
                    Book book = borrowedBook.getBook();
                    BookDto bookDto = new BookDto();
                    bookDto.setBookId(book.getBookId());
                    bookDto.setTitle(book.getTitle());
                    bookDto.setAuthor(book.getAuthor());
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setImage(book.getImage());
                    borrowedBookSet.add(bookDto);
                }
                return borrowedBookSet;
            } else {
                Set<BookAdminResponseDto> borrowedBookSet = new HashSet<>();
                for (Borrowed borrowedBook : borrowedList) {
                    Book book = borrowedBook.getBook();
                    BookAdminResponseDto bookAdminResponseDto = new BookAdminResponseDto();
                    bookAdminResponseDto.setBookId(book.getBookId());
                    bookAdminResponseDto.setTitle(book.getTitle());
                    bookAdminResponseDto.setAuthor(book.getAuthor());
                    bookAdminResponseDto.setIsbn(book.getIsbn());
                    bookAdminResponseDto.setImage(book.getImage());
                    bookAdminResponseDto.setStatus(book.getStatus());
                    borrowedBookSet.add(bookAdminResponseDto);
                }
                return borrowedBookSet;
            }
        }
        throw new EmptyResultDataAccessException("User", 1);
    }

    @Override
    public Set<?> currentlyBorrowedBooks(int userId) {
        if(userId<0){
            throw new IllegalArgumentException("User Id");
        }
        Optional<User> optionalUser = userRepository.findByUserId(userId);
        Optional<User> optionalTokenUser = getUser();
        if (userId != optionalTokenUser.get().getUserId() && optionalTokenUser.get().getRole().equals(User.Role.CUSTOMER)) {
            throw new IllegalApiAccessException();
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            List<Borrowed> borrowedList = borrowedRepository.findByUserAndStatus(user, "Borrowed");
            if (optionalTokenUser.get().getRole().equals(User.Role.CUSTOMER)) {
                Set<BookDto> borrowedBookSet = new HashSet<>();
                for (Borrowed borrowedBook : borrowedList) {
                    Book book = borrowedBook.getBook();
                    BookDto bookDto = new BookDto();
                    bookDto.setBookId(book.getBookId());
                    bookDto.setTitle(book.getTitle());
                    bookDto.setAuthor(book.getAuthor());
                    bookDto.setIsbn(book.getIsbn());
                    bookDto.setImage(book.getImage());
                    borrowedBookSet.add(bookDto);
                }
                return borrowedBookSet;
            } else {
                Set<BookAdminResponseDto> borrowedBookSet = new HashSet<>();
                for (Borrowed borrowedBook : borrowedList) {
                    Book book = borrowedBook.getBook();
                    BookAdminResponseDto bookAdminResponseDto = new BookAdminResponseDto();
                    bookAdminResponseDto.setBookId(book.getBookId());
                    bookAdminResponseDto.setTitle(book.getTitle());
                    bookAdminResponseDto.setAuthor(book.getAuthor());
                    bookAdminResponseDto.setIsbn(book.getIsbn());
                    bookAdminResponseDto.setImage(book.getImage());
                    bookAdminResponseDto.setStatus(book.getStatus());
                    borrowedBookSet.add(bookAdminResponseDto);
                }
                return borrowedBookSet;
            }
        }
        return null;
    }

    private Optional<User> getUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        return userRepository.findByEmail(authentication.getName());
    }

    public Optional<?> getBookById(int bookId) {
        return bookRepository.findById(bookId);
    }

    @Override
    public List<?> getAllBooks() {
        List<Book> bookList = bookRepository.findAll();
        Optional<User> optionalUser = getUser();
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (user.getRole().equals(User.Role.CUSTOMER)) {
                List<BookDto> bookDtoList = new ArrayList<>();
                for (Book book : bookList) {
                    if (!book.getStatus().equals(Book.Status.Deleted)) {
                        BookDto bookDto = new BookDto();
                        bookDto.setBookId(book.getBookId());
                        bookDto.setTitle(book.getTitle());
                        bookDto.setAuthor(book.getAuthor());
                        bookDto.setIsbn(book.getIsbn());
                        bookDto.setStatus(book.getStatus());
                        bookDto.setImage(book.getImage());
                        bookDtoList.add(bookDto);
                    }
                }
                return bookDtoList;
            } else {
                List<BookAdminResponseDto> bookAdminResponseDtoList = new ArrayList<>();
                for (Book book : bookList) {
                    BookAdminResponseDto bookAdminResponseDto = new BookAdminResponseDto();
                    bookAdminResponseDto.setBookId(book.getBookId());
                    bookAdminResponseDto.setTitle(book.getTitle());
                    bookAdminResponseDto.setAuthor(book.getAuthor());
                    bookAdminResponseDto.setIsbn(book.getIsbn());
                    bookAdminResponseDto.setImage(book.getImage());
                    bookAdminResponseDto.setStatus(book.getStatus());
                    bookAdminResponseDtoList.add(bookAdminResponseDto);
                }
                return bookAdminResponseDtoList;
            }
        }
        return null;
    }
}
