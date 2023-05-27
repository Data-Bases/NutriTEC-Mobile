package com.example.nutritec_mobile;

import java.util.ArrayList;

public class ClassService {
    public static ArrayList<ClassService> classArrayList = new ArrayList<>();
    private String name;

    private String description;
    private String startTime;
    private String endTime;
    private String date;
    private int capacity;
    private boolean group;
    private String instructor;
    private String branch;

    private int ID;
    public ClassService(String name,
                        String description,
                        String startTime,
                        String endTime,
                        String date,
                        int capacity,
                        boolean group,
                        String instructor,
                        String branch,
                        int ID) {
        this.name = name;
        this.description = description;
        this.startTime = startTime;
        this.endTime = endTime;
        this.date = date;
        this.capacity = capacity;
        this.group = group;
        this.instructor = instructor;
        this.branch = branch;
        this.ID = ID;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }
    public String getName() {
        return name;
    }

    public void setName(String Name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public boolean isGroup() {
        return group;
    }

    public void setGroup(boolean group) {
        this.group = group;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
