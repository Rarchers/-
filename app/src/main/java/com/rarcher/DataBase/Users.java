package com.rarcher.DataBase;

import java.io.ByteArrayInputStream;

/**
 * Created by 25532 on 2019/3/2.
 */

public class Users {
    private String phone;
    private String password;
    private String name;
    private String sex;
    private String ID;
    private ByteArrayInputStream image;

    public Users(String phone, String password, String name, String sex, String ID, ByteArrayInputStream image) {
        this.phone = phone;
        this.password = password;
        this.name = name;
        this.sex = sex;
        this.ID = ID;
        this.image = image;
    }

    public String getPhone() {
        return phone;
    }

    public String getPassword() {
        return password;
    }

    public String getName() {
        return name;
    }

    public String getSex() {
        return sex;
    }

    public String getID() {
        return ID;
    }

    public ByteArrayInputStream getImage() {
        return image;
    }
}
