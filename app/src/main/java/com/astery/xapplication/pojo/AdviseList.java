package com.astery.xapplication.pojo;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

/** set of advises. like
 *    - "don't forget to use lubricants" 30 likes, 10 dislikes
 *    - "have fun" - 100 likes, 0 dislikes
 */
@Entity
public class AdviseList {
    @PrimaryKey
    private String id;
    private List<String> advises;
    private int gender;
    private String text;
    private int type;



    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
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

    public List<String> getAdvises() {
        return advises;
    }

    public void setAdvises(List<String> advises) {
        this.advises = advises;
    }
}
