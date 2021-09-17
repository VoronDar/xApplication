package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.EventDescriptionConverter;
import com.astery.xapplication.data_source.local.database.converter.TimeStampConverter;
import com.astery.xapplication.pojo.serialazable.EventDescription;

import java.sql.Timestamp;

@Entity
@TypeConverters({ArrayConverter.class, TimeStampConverter.class, EventDescriptionConverter.class})
public class Event {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "template_id")
    private String templateId;

    @ColumnInfo(name = "description")
    private EventDescription eventDescription;

    private Timestamp date;

    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    public String getTemplateId() {
        return templateId;
    }

    public void setTemplateId(String templateId) {
        this.templateId = templateId;
    }

    public EventDescription getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(EventDescription eventDescription) {
        this.eventDescription = eventDescription;
    }

    public Timestamp getDate() {
        return date;
    }

    public void setDate(Timestamp date) {
        this.date = date;
    }
}


