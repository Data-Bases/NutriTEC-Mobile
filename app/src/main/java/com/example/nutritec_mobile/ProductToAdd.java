package com.example.nutritec_mobile;

import java.util.ArrayList;

public class ProductToAdd {
    public static ArrayList<ProductToAdd> products_to_add = new ArrayList<>();
    public static ArrayList<ProductToAdd> products_to_edit = new ArrayList<>();
    public static ArrayList<ProductToAdd> products_to_edit_after = new ArrayList<>();
    public static ArrayList<ProductToAdd> products_to_add_after = new ArrayList<>();
    public static ArrayList<ProductToAdd> products_to_delete_after = new ArrayList<>();


    public static int currentRecipe;

    private int id;
    private String name;
    private double servings;

    public ProductToAdd() {
    }

    public ProductToAdd(int id, String name, double servings) {
        this.id = id;
        this.name = name;
        this.servings = servings;
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

    public double getServings() {
        return servings;
    }

    public void setServings(double servings) {
        this.servings = servings;
    }
}
