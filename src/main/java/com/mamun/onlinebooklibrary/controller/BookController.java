package com.mamun.onlinebooklibrary.controller;


import com.mamun.onlinebooklibrary.entity.Book;
import com.mamun.onlinebooklibrary.model.BookDto;
import com.mamun.onlinebooklibrary.service.services.AdminService;
import com.mamun.onlinebooklibrary.service.services.CommonService;
import com.mamun.onlinebooklibrary.service.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private AdminService adminService;
    @Autowired
    private UserService userService;
    @Autowired
    private CommonService commonService;

    @PostMapping("/create")
    public ResponseEntity<String> createNewBook(@RequestBody Book book) {
        adminService.createBook(book);
        return new ResponseEntity<>("Successfully created!", HttpStatus.CREATED);
    }

    @GetMapping("/{bookId}")
    public ResponseEntity<?> getBookById(@PathVariable int bookId) {
        Optional<?> bookOptional = commonService.getBookById(bookId);
        if (bookOptional.isPresent()) {
            return new ResponseEntity<>(bookOptional.get(), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<?>> getAllBooks() {
        return new ResponseEntity<>(commonService.getAllBooks(), HttpStatus.OK);
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateBook(@RequestBody BookDto bookDto) {
        adminService.updateBook(bookDto);
        return new ResponseEntity<>("Successfully updated!", HttpStatus.OK);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBook(@RequestBody BookDto bookDto) {
        adminService.deleteBook(bookDto);
        return new ResponseEntity<>("Successfully deleted!", HttpStatus.OK);
    }

}

