package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Relation;

import com.astery.xapplication.data_source.remote.utils.FbUsable;

import java.util.List;

/**
 * one item in article
 * may has text, picture, and sets of advises.
 * */
public class Item implements FbUsable {
    private String id;

    @Ignore
    private Bitmap image;
    private String text;

    @ColumnInfo(name="parent_id")
    private String parentId;

    @ColumnInfo(name = "category_id")
    private String categoryId;

    @Relation(parentColumn = "id", entityColumn = "parent_id")
    private List<Advise> advises;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Item() {
    }

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

    public List<Advise> getAdvises() {
        return advises;
    }

    public void setAdvises(List<Advise> advises) {
        this.advises = advises;
    }

    public Item(String id, String text, String parentId) {
        this.id = id;
        this.text = text;
        this.parentId = parentId;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
}
