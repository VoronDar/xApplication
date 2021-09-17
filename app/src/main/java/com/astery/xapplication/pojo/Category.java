package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.remote.utils.FbUsable;

import java.util.List;

@Entity
@TypeConverters(ArrayConverter.class)
public class Category implements FbUsable {
    @NonNull
    @PrimaryKey
    private String id;
    // TODO - DELETE TAGS (MAYBE, I DNT KNOW)
    private List<String> tags;
    private String text;

    @ColumnInfo(name = "key_words")
    private List<String> keyWords;
    /** parent for category - another category */
    @ColumnInfo(name = "parent_id")
    private String parentId;

    @Ignore
    public Category() {
    }

    public Category(@NonNull String id, List<String> tags, String text, List<String> keyWords, String parentId) {
        this.id = id;
        this.tags = tags;
        this.text = text;
        this.keyWords = keyWords;
        this.parentId = parentId;
    }

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

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    @Override
    public String toString() {
        return "Category{" +
                "id='" + id + '\'' +
                ", tags=" + tags +
                ", text='" + text + '\'' +
                ", keyWords=" + keyWords +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
