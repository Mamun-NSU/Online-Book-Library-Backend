package com.mamun.onlinebooklibrary.service.services;

import com.mamun.onlinebooklibrary.entity.Borrowed;
import com.mamun.onlinebooklibrary.model.BookDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CommonService {
    Set<?> borrowedBooksByUser(int userId);

    Set<?> currentlyBorrowedBooks(int userId);

    List<?> getAllBooks();

    Optional<?> getBookById(int bookId);
}
