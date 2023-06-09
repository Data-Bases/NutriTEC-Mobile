package com.example.nutritec_mobile;

import java.util.ArrayList;

public class Recipe {
    public static ArrayList<Recipe> recipes = new ArrayList<>();

    private int id;
    private String name;

    public Recipe() {
    }

    public Recipe(int id, String name) {
        this.id = id;
        this.name = name;
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

}
