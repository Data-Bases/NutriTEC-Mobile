package com.example.nutritec_mobile;

import java.util.ArrayList;

public class ProductToAdd {
    public static ArrayList<ProductToAdd> products_to_add = new ArrayList<>();
    private int id;
    private int servings;

    public ProductToAdd() {
    }

    public ProductToAdd(int id, int servings) {
        this.id = id;
        this.servings = servings;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getServings() {
        return servings;
    }

    public void setServings(int servings) {
        this.servings = servings;
    }

}
