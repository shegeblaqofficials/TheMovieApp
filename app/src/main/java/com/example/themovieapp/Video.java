package com.example.themovieapp;

/**
 * Created by BLAQ on 5/14/2017.
 */

public class Video {

    private String name,key,label;
    private int VideoID;

    public Video()
    {

    }

    public Video(String name, String label, String key, int VideoID)
    {
        this.name=name;
        this.label=label;
        this.key=key;
        this.VideoID=VideoID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public int getVideoID() {
        return VideoID;
    }

    public void setVideoID(String videoID) {
        this.VideoID = VideoID;
    }

}
