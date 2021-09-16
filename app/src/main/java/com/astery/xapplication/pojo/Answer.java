package com.astery.xapplication.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * sets of answers to questions. If the user choose this answer and if it has an item - it will be shown to the user.
 * */
@Entity
public class Answer {
    @PrimaryKey
    private String id;
    private String text;
    private String item;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }
}
