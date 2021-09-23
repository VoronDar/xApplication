package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.astery.xapplication.data_source.remote.utils.FbUsable;

/**
 * sets of answers to questions. If the user choose this answer and if it has an item - it will be shown to the user.
 * */
@Entity
public class Answer implements FbUsable {
    @NonNull
    @PrimaryKey
    private String id;
    private String text;
    private String item;
    @Ignore
    private Item itemOb;
    @ColumnInfo(name = "parent_id")
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

    public String getItem() {
        return item;
    }

    public void setItem(String item) {
        this.item = item;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public Item getItemOb() {
        return itemOb;
    }

    public void setItemOb(Item itemOb) {
        this.itemOb = itemOb;
    }

    @Override
    public String toString() {
        return "Answer{" +
                "id='" + id + '\'' +
                ", text='" + text + '\'' +
                ", item='" + item + '\'' +
                ", itemOb=" + itemOb +
                ", parentId='" + parentId + '\'' +
                '}';
    }

    public Answer() {
    }

    @Ignore
    public Answer(@NonNull String id, String text, String item, String parentId) {
        this.id = id;
        this.text = text;
        this.item = item;
        this.parentId = parentId;
    }
}
