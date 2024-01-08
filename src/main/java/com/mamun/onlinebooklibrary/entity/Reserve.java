package com.mamun.onlinebooklibrary.entity;

import jakarta.persistence.*;
import lombok.Getter;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.LocalDate;

@Entity
@Table(name = "Reserve")
@Component
public class Reserve {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reserve_id")
    private Integer reviewId;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "book_id")
    private Book book;
    @Column(name = "reservation_date")
    private Date reservationDate;

    public enum ReservationStatus {
        Reserved,
        Cancelled
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "reservation_status")
    private ReservationStatus reservationStatus;

    public enum BookStatus {
        Available,
        Borrowed
    }

    @Enumerated(EnumType.STRING)
    @Column(name = "book_status")
    private BookStatus bookStatus;

    @PrePersist
    public void prePersist() {
        LocalDate currentDate = LocalDate.now();
        reservationDate = Date.valueOf(currentDate);
        reservationStatus = ReservationStatus.Reserved;
        bookStatus = BookStatus.Borrowed;
    }

    public Reserve() {

    }

    public Reserve(User user, Book book, Date reservationDate,
                   ReservationStatus reservationStatus,
                   BookStatus bookStatus) {
        this.user = user;
        this.book = book;
        this.reservationDate = reservationDate;
        this.reservationStatus = reservationStatus;
        this.bookStatus = bookStatus;
    }

    public Integer getReviewId() {
        return reviewId;
    }

    public void setReviewId(Integer reviewId) {
        this.reviewId = reviewId;
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

    public Date getReservationDate() {
        return reservationDate;
    }

    public void setReservationDate(Date reservationDate) {
        this.reservationDate = reservationDate;
    }

    public ReservationStatus getReservationStatus() {
        return reservationStatus;
    }

    public void setReservationStatus(ReservationStatus reservationStatus) {
        this.reservationStatus = reservationStatus;
    }

    public void setBookStatus(BookStatus bookStatus) {
        this.bookStatus = bookStatus;
    }

    public BookStatus getBookStatus() {
        return bookStatus;
    }
}

