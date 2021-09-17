package com.astery.xapplication.pojo.only_for_db;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;

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

    @ColumnInfo(name = "category_id")
    private String categoryId;


    public ItemEntity(@NonNull String id, String text, String parentId) {
        this.id = id;
        this.text = text;
        this.parentId = parentId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
