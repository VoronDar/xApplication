package com.astery.xapplication.pojo.only_for_db;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.TimeStampConverter;

import java.sql.Timestamp;
import java.util.List;

/**
 * the hugest thing. Has several items. Can be found in article pool.
 * */
@Entity
@TypeConverters({ArrayConverter.class, TimeStampConverter.class})
public class ArticleEntity {

    @PrimaryKey
    private String id;

    private String name;
    private String description;
    private int likes;
    private int dislikes;
    private int watched;

    private Timestamp timestamp;

    @ColumnInfo(name = "wide_tags")
    private List<String> wideTags;
    @ColumnInfo(name = "closest_tags")
    private List<String> closestTags;


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
}
