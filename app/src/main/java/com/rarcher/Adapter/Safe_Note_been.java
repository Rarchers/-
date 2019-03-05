package com.rarcher.Adapter;

/**
 * Created by 25532 on 2019/3/5.
 */

public class Safe_Note_been {
    private String title;
    private String uid;
    private String context;
    private String name;

    public String getTitle() {
        return title;
    }

    public String getContext() {
        return context;
    }

    public String getUid() {
        return uid;
    }

    public String getName() {
        return name;
    }

    public Safe_Note_been(String title, String uid, String context, String name) {

        this.title = title;
        this.uid = uid;
        this.context = context;
        this.name = name;
    }
}
