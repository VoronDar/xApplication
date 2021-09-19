package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.room.ColumnInfo;
import androidx.room.Ignore;
import androidx.room.Relation;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.TimeStampConverter;
import com.astery.xapplication.pojo.only_for_db.ItemEntity;

import java.sql.Timestamp;
import java.util.List;

/**
 * the hugest thing. Has several items. Can be found in article pool.
 * */
@TypeConverters({ArrayConverter.class, TimeStampConverter.class})
public class Article {
    private String id;
    @Ignore
    private Bitmap image;

    private String name;
    private String description;
    private int likes;
    private int dislikes;
    private int watched;
    private Timestamp timestamp;

    @ColumnInfo(name = "wide_tags")
    private List<String> wideTags;
    @ColumnInfo(name = "key_words")
    private List<String> keyWords;

    @Relation(parentColumn = "id", entityColumn = "parent_id")
    private List<ItemEntity> itemsEntity;

    @Ignore
    private List<Item> items;


    @Ignore
    public Article(){

    }

    public Article(String id, String name, String description, int likes, int dislikes, int watched, Timestamp timestamp, List<String> wideTags, List<String> keyWords) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.watched = watched;
        this.timestamp = timestamp;
        this.wideTags = wideTags;
        this.keyWords = keyWords;
    }

    @Ignore
    public Article(String id, Bitmap image, String name, String description, int likes, int dislikes, int watched, Timestamp timestamp, List<String> wideTags, List<String> keyWords, List<ItemEntity> itemsEntity, List<Item> items) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.description = description;
        this.likes = likes;
        this.dislikes = dislikes;
        this.watched = watched;
        this.timestamp = timestamp;
        this.wideTags = wideTags;
        this.keyWords = keyWords;
        this.itemsEntity = itemsEntity;
        this.items = items;
    }

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

    public List<String> getKeyWords() {
        return keyWords;
    }

    public void setKeyWords(List<String> keyWords) {
        this.keyWords = keyWords;
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

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
