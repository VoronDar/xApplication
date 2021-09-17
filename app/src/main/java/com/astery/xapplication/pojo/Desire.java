package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;

import org.jetbrains.annotations.NotNull;

import java.util.List;

/**
 * used for cases when a user wants to do something and asks for advises.
 */
@Entity
@TypeConverters(ArrayConverter.class)
public class Desire {
    @NonNull
    @PrimaryKey
    private String id;
    private String text;

    @ColumnInfo(name = "key_words")
    private List<String> keyWords;

    @Ignore
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
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

}
