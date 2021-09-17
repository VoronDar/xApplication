package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.Relation;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.EventCategoryConverter;
import com.astery.xapplication.pojo.enums.EventCategory;

import org.jetbrains.annotations.NotNull;

import java.sql.Timestamp;
import java.util.List;

/**
 * used for cases when a user completed something and looking for a feedback
 */
@Entity
@TypeConverters({ArrayConverter.class, EventCategoryConverter.class})
public class EventTemplate {
    @NonNull
    @PrimaryKey
    private String id;

    private String name;

    @Ignore
    private Bitmap image;

    @ColumnInfo(name = "event_category")
    private EventCategory eventCategory;

    @Ignore
    private List<Question> questions;

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public EventCategory getEventCategory() {
        return eventCategory;
    }

    public void setEventCategory(EventCategory eventCategory) {
        this.eventCategory = eventCategory;
    }

}
