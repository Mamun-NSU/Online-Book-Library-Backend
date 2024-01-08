package com.mamun.onlinebooklibrary.model;

import com.mamun.onlinebooklibrary.entity.Book;

import java.util.Objects;

public class BookAdminResponseDto {
    private Integer bookId;
    private String title;
    private String author;
    private String isbn;

    private String image;
    private Book.Status status;

    public BookAdminResponseDto() {

    }

    public BookAdminResponseDto(Integer bookId, String title, String author, String isbn, Book.Status status, String image) {
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.image = image;
        this.status = status;
    }

    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {this.image = image;}

    public Book.Status getStatus() {
        return status;
    }

    public void setStatus(Book.Status status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BookAdminResponseDto bookDto = (BookAdminResponseDto) o;
        return Objects.equals(bookId, bookDto.bookId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(bookId);
    }
}
