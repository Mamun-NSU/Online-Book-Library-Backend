package com.mamun.onlinebooklibrary.service.services;

import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.model.BookDto;

import java.util.Optional;

public interface AdminService {
    Optional<User> getUserById(int userId);

    void createBook(Book book);

    void updateBook(BookDto bookDto);

    void deleteBook(BookDto bookDto);
}
