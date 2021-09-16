package com.astery.xapplication.pojo;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

/** simple item
 * just text like "don't forget to ues lubricant, count of people who agree or disagree with this advice"
 * */

@Entity
public class Advise {

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
