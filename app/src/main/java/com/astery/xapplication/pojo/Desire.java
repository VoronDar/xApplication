package com.astery.xapplication.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * used for cases when a user wants to do something and asks for advises.
 */
@Entity
public class Desire {
    @PrimaryKey
    private String id;
    private String text;
    private List<String> keyWords;
    private List<String> questions;

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

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
    }

    public List<String> getQuestions() {
        return questions;
    }

    public void setQuestions(List<String> questions) {
        this.questions = questions;
    }
}
