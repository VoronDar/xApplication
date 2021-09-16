package com.astery.xapplication.pojo.only_for_db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;

/**
 * narrow questions for desire and consequence that allow to know more
 * */
@Entity
@TypeConverters(ArrayConverter.class)
public class QuestionEntity {
    @PrimaryKey
    private String id;
    private String text;

    @ColumnInfo(name="parent_id")
    private String parentId;

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
}
