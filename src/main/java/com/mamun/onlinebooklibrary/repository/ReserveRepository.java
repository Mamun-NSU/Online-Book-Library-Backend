package com.mamun.onlinebooklibrary.repository;

import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.Reserve;
import com.mamun.onlinebooklibrary.entity.Review;
import com.mamun.onlinebooklibrary.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReserveRepository extends JpaRepository<Reserve, Integer> {
    Optional<Reserve> findByUserAndBook(User user, Book book);

    List<Reserve> findByBookAndBookStatus(Book book, Reserve.BookStatus bookStatus);
}
