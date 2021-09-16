package com.astery.xapplication.pojo.only_for_db;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

/**
 * one item in article
 * may has text, picture, and sets of advises.
 * */
@Entity
public class ItemEntity {
    @NonNull
    @PrimaryKey
    private String id;
    private String text;

    @ColumnInfo(name = "parent_id")
    private String parentId;


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }
}
