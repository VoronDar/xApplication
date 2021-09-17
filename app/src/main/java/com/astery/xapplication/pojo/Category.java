package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;

import java.util.List;

@Entity
@TypeConverters(ArrayConverter.class)
public class Category {
    @NonNull
    @PrimaryKey
    private String id;
    private List<String> tags;
    private String text;

    @ColumnInfo(name = "key_words")
    private List<String> keyWords;
    @ColumnInfo(name = "parent_category_id")
    private String parentCatId;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
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

    public String getParentCatId() {
        return parentCatId;
    }

    public void setParentCatId(String parentCatId) {
        this.parentCatId = parentCatId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }
}
