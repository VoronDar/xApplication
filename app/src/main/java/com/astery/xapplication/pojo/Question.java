package com.astery.xapplication.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;

import java.util.List;

/**
 * narrow questions for desire and consequence that allow to know more
 * */
public class Question {
    private String id;
    private String text;

    @ColumnInfo(name = "parent_id")
    private String parentId;

    @Relation(parentColumn = "id", entityColumn = "parent_id")
    private List<Answer> answers;

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }
}
