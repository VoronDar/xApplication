package com.astery.xapplication.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * narrow questions for desire and consequence that allow to know more
 * */
@Entity
public class Question {
    @PrimaryKey
    private String id;
    private List<String> answers;
    private String text;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getAnswers() {
        return answers;
    }

    public void setAnswers(List<String> answers) {
        this.answers = answers;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
