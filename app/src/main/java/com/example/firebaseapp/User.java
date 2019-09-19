package com.example.firebaseapp;

public class User {

    String id;
    String name;
    String status;
    String image;
    String thumb_image;

    public User() { }

    public User(String name, String status, String image, String thumb_image, String id) {

        this.name = name;
        this.status = status;
        this.image = image;
        this.thumb_image = thumb_image;
        this.id = id;
    }

    //Getters

    public String getName() {
        return name;
    }

    public String getStatus() {
        return status;
    }

    public String getImage() {
        return image;
    }

    public String getThumb_image() {
        return thumb_image;
    }

    //Setters

    public void setName(String name) { this.name = name; }

    public void setStatus(String status) { this.status = status; }

    public void setImage(String image) { this.image = image; }

    public void setThumb_image(String thumb_image) { this.thumb_image = thumb_image; }

}
