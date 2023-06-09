package com.example.nutritec_mobile;

public class Client {
    public static Client currentClient;

    private int id;
    private String name;
    private String userType;

    public Client() {
    }

    public Client(int id, String name, String userType) {
        this.id = id;
        this.name = name;
        this.userType = userType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }



}
