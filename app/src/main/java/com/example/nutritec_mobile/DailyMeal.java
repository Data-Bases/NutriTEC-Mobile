package com.example.nutritec_mobile;

import java.util.ArrayList;

public class DailyMeal {
    public static String currentDaily;
    public static ArrayList<DailyMeal> breakfast = new ArrayList<>();
    public static ArrayList<DailyMeal> lunch = new ArrayList<>();
    public static ArrayList<DailyMeal> dinner = new ArrayList<>();
    public static ArrayList<DailyMeal> snacks = new ArrayList<>();
    public static ArrayList<DailyMeal> recipes_products = new ArrayList<>();
    public static ArrayList<DailyMeal> add_recipes_products = new ArrayList<>();
    public static Double breakfast_cal;
    public static Double lunch_cal;
    public static Double dinner_cal;
    public static Double snacks_cal;


    private int id;
    private String name;
    private String type;

    private Double servings;

    public DailyMeal() {
    }

    public DailyMeal(int id, String name, String type, Double servings) {

        this.id = id;
        this.name = name;
        this.type = type;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Double getServings() {
        return servings;
    }

    public void setServings(Double servings) {
        this.servings = servings;
    }

}
