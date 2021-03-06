package com.astery.xapplication.pojo;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.astery.xapplication.data_source.remote.utils.FbUsable;

import org.jetbrains.annotations.NotNull;

/** simple item
 * just text like "don't forget to ues lubricant, count of people who agree or disagree with this advice"
 * */

@Entity
public class Advise implements FbUsable {

    @NonNull
    @PrimaryKey
    private String id;

    @ColumnInfo(name = "agree_count")
    private int agreeCount;
    @ColumnInfo(name = "disagree_count")
    private int disagreeCount;

    private int type;
    private String text;

    @ColumnInfo(name = "parent_id")
    private String parentId;

    @Ignore
    public Advise() {
    }

    public Advise(@NonNull String id, int agreeCount, int disagreeCount, int type, String text, String parentId) {
        this.id = id;
        this.agreeCount = agreeCount;
        this.disagreeCount = disagreeCount;
        this.type = type;
        this.text = text;
        this.parentId = parentId;
    }


    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAgreeCount() {
        return agreeCount;
    }

    public void setAgreeCount(int agreeCount) {
        this.agreeCount = agreeCount;
    }

    public int getDisagreeCount() {
        return disagreeCount;
    }

    public void setDisagreeCount(int disagreeCount) {
        this.disagreeCount = disagreeCount;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @NotNull
    public String getId() {
        return id;
    }

    public void setId(@NotNull String id) {
        this.id = id;
    }

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }


    @Override
    public String toString() {
        return "Advise{" +
                "id='" + id + '\'' +
                ", agreeCount=" + agreeCount +
                ", disagreeCount=" + disagreeCount +
                ", type=" + type +
                ", text='" + text + '\'' +
                ", parentId='" + parentId + '\'' +
                '}';
    }
}
