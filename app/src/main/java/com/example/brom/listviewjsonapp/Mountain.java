package com.example.brom.listviewjsonapp;

/**
 * Created by colon on 2018-04-26.
 */

public class Mountain {
    Integer id  = 88;
    String name = "Mount Shoehorn";
    String location = "Where it's needed";
    Integer  height = 1;
    String imageURL = "https://ind5.ccio.co/lE/E2/YB/f9c0c455cabb8299943074a4cf54e57a.jpn";
    String wikiURL = "https://www.youtube.com/watch?v=T0Wd0eXZTWk";

    public void setHeight(Integer height) {
        this.height = height;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWikiURL(String wikiURL) {
        this.wikiURL = wikiURL;
    }

    public Integer getHeight() {
        return height;
    }

    public Integer getId() {
        return id;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getLocation() {
        return location;
    }

    public String getName() {
        return name;
    }

    public String getWikiURL() {
        return wikiURL;
    }
}
