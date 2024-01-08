package com.mamun.onlinebooklibrary.repository;

import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.Borrowed;
import com.mamun.onlinebooklibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BorrowedRepository extends JpaRepository<Borrowed, Integer> {
    Optional<Borrowed> findByUserAndBookAndStatus(User user, Book book, String status);

    List<Borrowed> findByUser(User user);

    List<Borrowed> findByUserAndStatus(User user, String status);
}

