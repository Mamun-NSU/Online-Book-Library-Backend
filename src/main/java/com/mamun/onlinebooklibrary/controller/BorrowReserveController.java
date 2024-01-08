package com.mamun.onlinebooklibrary.controller;


import com.mamun.onlinebooklibrary.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books/{bookId}")
public class BorrowReserveController {
    @Autowired
    private UserService userService;

    @PostMapping("/borrow")
    public ResponseEntity<String> borrowBook(@PathVariable int bookId) {
        userService.borrowBook(bookId);
        return new ResponseEntity<>("Successfully borrowed!", HttpStatus.OK);
    }

    @PutMapping("/return")
    public ResponseEntity<String> returnBorrowedBook(@PathVariable int bookId) {
        userService.returnBook(bookId);
        return new ResponseEntity<>("Book returned successfully!", HttpStatus.OK);
    }

    @PostMapping("/reserve")
    public ResponseEntity<String> reserveBook(@PathVariable int bookId) {
        userService.createReservation(bookId);
        return new ResponseEntity<>("Book reserved successfully!", HttpStatus.OK);
    }

    @PutMapping("/cancel-reservation")
    public ResponseEntity<String> cancelBookReservation(@PathVariable int bookId) {
        userService.cancelReservation(bookId);
        return new ResponseEntity<>("Book reservation cancelled successfully!", HttpStatus.OK);
    }
}
