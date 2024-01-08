package com.mamun.onlinebooklibrary.model;


public class ReviewDto {
    private Float rating;
    private String comment;

    public ReviewDto() {

    }

    public ReviewDto(Float rating, String comment) {
        this.rating = rating;
        this.comment = comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRating(Float rating) {
        this.rating = rating;
    }

    public Float getRating() {
        return rating;
    }

    public String getComment() {
        return comment;
    }
}
