package com.astery.xapplication.pojo;

import java.util.List;

/**
 * pojo class for a user
 */
public class User {
    private String id;
    private int gender;

    /** lists with id of articles */
    private List<String> liked;
    private List<String> disliked;
    private List<String> watched;

    /** lists with id of advices */
    private List<String> agreed;
    private List<String> disagreed;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public List<String> getLiked() {
        return liked;
    }

    public void setLiked(List<String> liked) {
        this.liked = liked;
    }

    public List<String> getDisliked() {
        return disliked;
    }

    public void setDisliked(List<String> disliked) {
        this.disliked = disliked;
    }

    public List<String> getWatched() {
        return watched;
    }

    public void setWatched(List<String> watched) {
        this.watched = watched;
    }

    public List<String> getAgreed() {
        return agreed;
    }

    public void setAgreed(List<String> agreed) {
        this.agreed = agreed;
    }

    public List<String> getDisagreed() {
        return disagreed;
    }

    public void setDisagreed(List<String> disagreed) {
        this.disagreed = disagreed;
    }
}
