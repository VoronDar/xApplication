package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.List;

/**
 * the hugest thing. Has several items. Can be found in article pool.
 * */
@Entity
public class Article {

    @PrimaryKey
    private String id;

    @Ignore
    private Bitmap image;

    private String name;
    private String description;
    private List<String> items;
    private int likes;
    private int dislikes;
    private int watched;

    @ColumnInfo(name = "wide_tags")
    private List<String> wideTags;
    @ColumnInfo(name = "closestTags")
    private List<String> closestTags;

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getLikes() {
        return likes;
    }

    public void setLikes(int likes) {
        this.likes = likes;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes(int dislikes) {
        this.dislikes = dislikes;
    }

    public int getWatched() {
        return watched;
    }

    public void setWatched(int watched) {
        this.watched = watched;
    }

    public List<String> getWideTags() {
        return wideTags;
    }

    public void setWideTags(List<String> wideTags) {
        this.wideTags = wideTags;
    }

    public List<String> getClosestTags() {
        return closestTags;
    }

    public void setClosestTags(List<String> closestTags) {
        this.closestTags = closestTags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
