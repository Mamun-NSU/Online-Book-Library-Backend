package com.mamun.onlinebooklibrary.service.impl;

import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.Borrowed;
import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.exception.IllegalOperationException;
import com.mamun.onlinebooklibrary.model.BookDto;
import com.mamun.onlinebooklibrary.repository.BookRepository;
import com.mamun.onlinebooklibrary.repository.BorrowedRepository;
import com.mamun.onlinebooklibrary.repository.UserRepository;
import com.mamun.onlinebooklibrary.service.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private BorrowedRepository borrowedRepository;

    @Override
    public Optional<User> getUserById(int userId) {
        if (userId < 0) {
            throw new IllegalArgumentException("User Id");
        }
        Optional<User> optionalUser = userRepository.findById(userId);
        if (optionalUser.isEmpty()) {
            throw new EmptyResultDataAccessException("User", 1);
        } else {
            return optionalUser;
        }
    }

    @Override
    public void createBook(Book book) {
        book.setStatus(Book.Status.Available);
        bookRepository.save(book);
    }

    @Override
    public void updateBook(BookDto bookDto) {
        if (bookDto.getBookId() < 0) {
            throw new IllegalArgumentException("Book Id");
        }
        if (bookDto.getBookId() == null) {
            throw new EmptyResultDataAccessException("Book", 1);
        }
        Optional<Book> optionalBook = bookRepository.findById(bookDto.getBookId());
        if (optionalBook.isPresent()) {
            Book book = optionalBook.get();
            if (bookDto.getTitle() != null) {
                book.setTitle(bookDto.getTitle());
            }
            if (bookDto.getAuthor() != null) {
                book.setAuthor(bookDto.getAuthor());
            }
            if (bookDto.getIsbn() != null) {
                book.setIsbn(bookDto.getIsbn());
            }
            if (bookDto.getImage() != null) {
                book.setImage(bookDto.getImage());
            }
            bookRepository.save(book);
        } else {
            throw new EmptyResultDataAccessException("Book", 1);
        }
    }

    @Override
    public void deleteBook(BookDto bookDto) {
        if (bookDto.getBookId() < 0) {
            throw new IllegalArgumentException(("Book Id"));
        }
        Optional<Book> optionalBook = bookRepository.findByBookId(bookDto.getBookId());
        if (optionalBook.isPresent()) {
            if (optionalBook.get().getStatus().equals(Book.Status.Available)) {
                optionalBook.get().setStatus(Book.Status.Deleted);
                bookRepository.save(optionalBook.get());
            } else {
                throw new IllegalOperationException("Error in deletion! Book is currently borrowed!");
            }
        } else {
            throw new EmptyResultDataAccessException("Book", 1);
        }
    }
}

