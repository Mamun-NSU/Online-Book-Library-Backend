package com.mamun.onlinebooklibrary.repository;


import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.entity.Reserve;
import com.mamun.onlinebooklibrary.entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Integer> {
    List<Review> findByBook(Book book);
}