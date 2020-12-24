package com.appplication.firebase_imagdrawer.helpers;

public class Favmessages {
    String name;
    String imageUrl;
    String description;
    String availableon;
    String cast;
    String fav,genre,imagebackground,ratings,time,trailer;

    public Favmessages() {
    }

    public Favmessages(String name, String imageUrl, String description, String availableon, String cast, String fav, String genre, String imagebackground, String ratings, String time, String trailer, String id) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.availableon = availableon;
        this.cast = cast;
        this.fav = fav;
        this.genre = genre;
        this.imagebackground = imagebackground;
        this.ratings = ratings;
        this.time = time;
        this.trailer = trailer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAvailableon() {
        return availableon;
    }

    public void setAvailableon(String availableon) {
        this.availableon = availableon;
    }

    public String getCast() {
        return cast;
    }

    public void setCast(String cast) {
        this.cast = cast;
    }

    public String getFav() {
        return fav;
    }

    public void setFav(String fav) {
        this.fav = fav;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getImagebackground() {
        return imagebackground;
    }

    public void setImagebackground(String imagebackground) {
        this.imagebackground = imagebackground;
    }

    public String getRatings() {
        return ratings;
    }

    public void setRatings(String ratings) {
        this.ratings = ratings;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }


}
