package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.DateConverter;
import com.astery.xapplication.data_source.local.database.converter.EventDescriptionConverter;
import com.astery.xapplication.pojo.serialazable.EventDescription;

import org.jetbrains.annotations.NotNull;

import java.util.Date;


@Entity
@TypeConverters({ArrayConverter.class, DateConverter.class, EventDescriptionConverter.class})
public class Event {
    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "template_id")
    private String templateId;

    @ColumnInfo(name = "description")
    private EventDescription eventDescription;

    private Date date;

    @Ignore
    private Bitmap bitmap;

    @Ignore
    private EventTemplate template;


    public Event() {
    }

    @Ignore
    public Event(@NonNull String id, String templateId, EventDescription eventDescription, Date date) {
        this.id = id;
        this.templateId = templateId;
        this.eventDescription = eventDescription;
        this.date = date;
    }



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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }

    public EventTemplate getTemplate() {
        return template;
    }

    public void setTemplate(EventTemplate template) {
        this.template = template;
    }

    public boolean isTips(){
        if (template == null || template.getQuestions() == null)
            return false;
        for (Question question: template.getQuestions()){
            if (question.getSelectedAnswer().getItemOb() == null) continue;
            return true;
        }
        return false;
    }


    @NotNull
    @Override
    public String toString() {
        return "Event{" +
                "id='" + id + '\'' +
                ", templateId='" + templateId + '\'' +
                ", eventDescription=" + eventDescription +
                ", date=" + date +
                ", bitmap=" + bitmap +
                ", template=" + template +
                '}';
    }
}


