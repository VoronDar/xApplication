package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.room.Relation;

import com.astery.xapplication.pojo.only_for_db.ItemEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * the hugest thing. Has several items. Can be found in article pool.
 * */
public class Article {
    private String id;
    private Bitmap image;

    private String name;
    private String description;
    private int likes;
    private int dislikes;
    private int watched;

    private Timestamp timestamp;

    private List<String> wideTags;
    private List<String> closestTags;

    @Relation(parentColumn = "id", entityColumn = "parent_id")
    private List<ItemEntity> itemsEntity;

    private List<Item> items;

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

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public List<ItemEntity> getItemsEntity() {
        return itemsEntity;
    }

    public void setItemsEntity(List<ItemEntity> items) {
        this.itemsEntity = items;
    }
}
