package com.testem.maxm.testem.connectivity;

/**
 * Created by Mr_95 on Jan 24, 2017.
 */

public final class User {
    Integer id;
    String name;
    String surname;
    String secondName;
    String group;
    String email;
    String password;
    String cellNumber;
    String deviceID;

    public User(Integer id, String name, String surname, String secondName, String group, String email, String password, String cellNumber, String deviceID) {
        this.id = id;
        this.name = name;
        this.surname = surname;
        this.secondName = secondName;
        this.group = group;
        this.email = email;
        this.password = password;
        this.cellNumber = cellNumber;
        this.deviceID = deviceID;
    }
}
