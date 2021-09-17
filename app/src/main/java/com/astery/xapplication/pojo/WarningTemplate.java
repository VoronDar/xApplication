package com.astery.xapplication.pojo;

import android.graphics.Bitmap;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import androidx.room.TypeConverters;

import com.astery.xapplication.data_source.local.database.converter.ArrayConverter;
import com.astery.xapplication.data_source.local.database.converter.EventCategoryConverter;
import com.astery.xapplication.data_source.local.database.converter.WarningCategoryConverter;
import com.astery.xapplication.data_source.local.database.converter.WarningConditionConverter;
import com.astery.xapplication.pojo.enums.EventCategory;
import com.astery.xapplication.pojo.enums.WarningCategory;
import com.astery.xapplication.pojo.enums.WarningConditionExpression;
import com.google.gson.Gson;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * used for cases when a user completed something and looking for a feedback
 */
@Entity
@TypeConverters({ArrayConverter.class, WarningCategoryConverter.class, WarningConditionConverter.class})
public class WarningTemplate {
    @NonNull
    @PrimaryKey
    private String id;
    private String text;


    @ColumnInfo(name = "warning_category")
    private WarningCategory warningCategory;

    private WarningCondition condition;

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

    public WarningCategory getWarningCategory() {
        return warningCategory;
    }

    public void setWarningCategory(WarningCategory warningCategory) {
        this.warningCategory = warningCategory;
    }

    public WarningCondition getCondition() {
        return condition;
    }

    public void setCondition(WarningCondition condition) {
        this.condition = condition;
    }

    public static class WarningCondition{
        @SerializedName("eventTypeId")
        @Expose
        private String eventTypeId;
        @SerializedName("time")
        @Expose
        private int time;
        @SerializedName("period")
        @Expose
        private int period;
        @SerializedName("condition")
        @Expose
        private WarningConditionExpression condition;

        public String getEventTypeId() {
            return eventTypeId;
        }

        public void setEventTypeId(String eventTypeId) {
            this.eventTypeId = eventTypeId;
        }

        public int getTime() {
            return time;
        }

        public void setTime(int time) {
            this.time = time;
        }

        public int getPeriod() {
            return period;
        }

        public void setPeriod(int period) {
            this.period = period;
        }

        public WarningConditionExpression getCondition() {
            return condition;
        }

        public void setCondition(WarningConditionExpression condition) {
            this.condition = condition;
        }
    }


}
