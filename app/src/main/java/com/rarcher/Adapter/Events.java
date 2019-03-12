package com.rarcher.Adapter;

/**
 * Created by 25532 on 2019/2/11.
 */

public class Events {
    private String title;
    private int imageId;

    public Events(String tilte, int imageId){
        this.title = tilte;
        this.imageId = imageId;
    }
    public String getTitle() {
        return title;
    }

    public int getImageId() {
        return imageId;
    }
}
