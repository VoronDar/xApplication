package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.room.Embedded;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * one item in article
 * may has text, picture, and sets of advises.
 * */
@Entity
public class Item {
    @PrimaryKey
    private String id;

    @Ignore
    private Bitmap image;

    @Embedded(prefix = "advises")
    private AdviseList advises;
    private String text;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public AdviseList getAdvises() {
        return advises;
    }

    public void setAdvises(AdviseList advises) {
        this.advises = advises;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
