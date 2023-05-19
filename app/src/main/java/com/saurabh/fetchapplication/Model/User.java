package com.saurabh.fetchapplication.Model;

import java.io.Serializable;

public class User implements Serializable {
    private int user_Id;
    private int user_ItemId;
    private String user_Name;

    public User(int user_Id, int user_ItemId, String user_Name) {
        this.user_Id = user_Id;
        this.user_ItemId = user_ItemId;
        this.user_Name = user_Name;
    }

    public int getUser_Id() {
        return user_Id;
    }

    public int getUser_ItemId() {
        return user_ItemId;
    }

    public String getUser_Name() {
        return user_Name;
    }
}
