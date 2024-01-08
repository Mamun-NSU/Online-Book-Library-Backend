package com.mamun.onlinebooklibrary.entity;


import jakarta.persistence.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Calendar;
//import java.util.Date;
import java.sql.Date;

@Entity
@Table(name = "Borrowed")
@Component
public class Borrowed {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "borrowed_id")
    private Integer borrowedId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "borrowed_date")
    private Date borrowedDate;
    @Column(name = "due_date")
    private Date dueDate;
    @Column(name = "return_date")
    private Date returnDate;
    @Column(name = "status")
    private String status;

    @PrePersist
    public void prePersist() {
        LocalDate currentDate = LocalDate.now();
        borrowedDate = Date.valueOf(currentDate);
        LocalDate dueDate = currentDate.plusDays(7);
        this.dueDate = Date.valueOf(dueDate);
        status = "Borrowed";
    }

    public Borrowed() {

    }

    public Borrowed(User user, Book book, Date borrowedDate, Date dueDate, String status) {
        this.user = user;
        this.book = book;
        this.borrowedDate = borrowedDate;
        this.dueDate = dueDate;
        this.status = status;
    }

    public Date getReturnDate() {
        return returnDate;
    }

    public void setReturnDate(Date returnDate) {
        this.returnDate = returnDate;
    }

    public Integer getBorrowedId() {
        return borrowedId;
    }

    public void setBorrowedId(Integer borrowedId) {
        this.borrowedId = borrowedId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }

    public Date getBorrowedDate() {
        return borrowedDate;
    }

    public void setBorrowedDate(Date borrowedDate) {
        this.borrowedDate = borrowedDate;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
