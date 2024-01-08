package com.mamun.onlinebooklibrary.controller;


import com.mamun.onlinebooklibrary.entity.Borrowed;
import com.mamun.onlinebooklibrary.entity.User;
import com.mamun.onlinebooklibrary.service.services.AdminService;
import com.mamun.onlinebooklibrary.service.services.CommonService;
import com.mamun.onlinebooklibrary.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/users/{userId}")
public class UserController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private CommonService commonService;
    @Autowired
    private UserService userService;

    @GetMapping("")
    public ResponseEntity<Optional<User>> findById(@PathVariable int userId) {
        return new ResponseEntity<>(adminService.getUserById(userId), HttpStatus.OK);
    }

    @GetMapping("/books")
    public ResponseEntity<Set<?>> findBorrowedBooksByUser(@PathVariable int userId) {
        return new ResponseEntity<>(commonService.borrowedBooksByUser(userId), HttpStatus.OK);
    }

    @GetMapping("/borrowed-books")
    public ResponseEntity<Set<?>> findCurrentBorrowedBooksByUser(@PathVariable int userId) {
        return new ResponseEntity<>(commonService.currentlyBorrowedBooks(userId), HttpStatus.OK);
    }

    @GetMapping("/history")
    public ResponseEntity<List<Borrowed>> findUserHistory(@PathVariable int userId) {
        return new ResponseEntity<>(userService.borrowHistory(userId), HttpStatus.OK);
    }
}
