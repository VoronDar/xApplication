package com.astery.xapplication.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.remote.utils.FbUsable;

import java.util.List;

/**
 * narrow questions for desire and consequence that allow to know more
 * */
public class Question implements FbUsable {
    private String id;
    private String text;

    @ColumnInfo(name = "parent_id")
    private String parentId;

    @Relation(parentColumn = "id", entityColumn = "parent_id")
    private List<Answer> answers;

    @Ignore
    private Answer selectedAnswer;

    public Question(String id, String text, String parentId) {
        this.id = id;
        this.text = text;
        this.parentId = parentId;
    }

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


    public Answer getSelectedAnswer() {
        return selectedAnswer;
    }

    public void setSelectedAnswer(Answer selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", parentId='" + parentId + '\'' +
                ", answers=" + answers +
                ", selectedAnswer=" + selectedAnswer +
                '}';
    }

    @Ignore
    public Question(){

    }
}
