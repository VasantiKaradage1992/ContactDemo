package com.contact.demo.model;

import android.graphics.Bitmap;

import java.util.ArrayList;

public class Contact {

    public String id;
    public String name;
    public  String photo;
    public  String number;

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }



    public Contact(String id, String name,String photo,String number) {
        this.id = id;
        this.name = name;
        this.photo=photo;
        this.number=number;

    }

}
