package com.mamun.onlinebooklibrary.controller;


import com.mamun.onlinebooklibrary.entity.Review;
import com.mamun.onlinebooklibrary.model.ReviewDto;
import com.mamun.onlinebooklibrary.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books/{bookId}/reviews")
public class ReviewController {
    @Autowired
    private UserService userService;

    @PostMapping("/create")
    public ResponseEntity<String> createReview(@RequestBody ReviewDto reviewDto,
                                               @PathVariable int bookId) {
        userService.createReview(reviewDto, bookId);
        return new ResponseEntity<>("Review created successfully!", HttpStatus.CREATED);
    }

    @GetMapping("")
    public ResponseEntity<List<Review>> getReviewByBookId(@PathVariable int bookId) {
        return new ResponseEntity<>(userService.getReviewsByBookId(bookId), HttpStatus.OK);
    }

    @PutMapping("/{reviewId}/update")
    public ResponseEntity<String> updateReview(@RequestBody ReviewDto reviewDto,
                                               @PathVariable int bookId,
                                               @PathVariable int reviewId) {
        userService.updateReview(reviewDto, bookId, reviewId);
        return new ResponseEntity<>("Review updated successfully!", HttpStatus.OK);
    }

    @DeleteMapping("/{reviewId}/delete")
    public ResponseEntity<String> deleteReview(@PathVariable int bookId,
                                               @PathVariable int reviewId) {
        userService.deleteReview(bookId, reviewId);
        return new ResponseEntity<>("Review deleted successfully!", HttpStatus.OK);
    }
}
