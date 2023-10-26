package com.example.hike_app.models;

import java.util.List;

public class HikeModel {
    private int id;
    private String hikeName;
    private String hikeLocation;
    private String hikeDate;
    private boolean hikeParking;
    private double hikeLength;
    private String hikeLevel;
    private String description;

    private List<ObservationsModel> observations;
    public HikeModel(int id, String hikeName, String hikeLocation, String hikeDate, boolean hikeParking, double hikeLength, String hikeLevel, String description) {
        this.id = id;
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
        this.hikeDate = hikeDate;
        this.hikeParking = hikeParking;
        this.hikeLength = hikeLength;
        this.hikeLevel = hikeLevel;
        this.description = description;
    }

    public HikeModel(int id, String hikeName, String hikeLocation) {
        this.id = id;
        this.hikeName = hikeName;
        this.hikeLocation = hikeLocation;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHikeName() {
        return hikeName;
    }

    public void setHikeName(String hikeName) {
        this.hikeName = hikeName;
    }

    public String getHikeLocation() {
        return hikeLocation;
    }

    public void setHikeLocation(String hikeLocation) {
        this.hikeLocation = hikeLocation;
    }

    public String getHikeDate() {
        return hikeDate;
    }

    public void setHikeDate(String hikeDate) {
        this.hikeDate = hikeDate;
    }

    public boolean isHikeParking() {
        return hikeParking;
    }

    public void setHikeParking(boolean hikeParking) {
        this.hikeParking = hikeParking;
    }

    public double getHikeLength() {
        return hikeLength;
    }

    public void setHikeLength(double hikeLength) {
        this.hikeLength = hikeLength;
    }

    public String getHikeLevel() {
        return hikeLevel;
    }

    public void setHikeLevel(String hikeLevel) {
        this.hikeLevel = hikeLevel;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<ObservationsModel> getObservations() {
        return observations;
    }

    public void setObservations(List<ObservationsModel> observations) {
        this.observations = observations;
    }

    @Override
    public String toString() {
        return "HikeModel{" +
                "id=" + id +
                ", hikeName='" + hikeName + '\'' +
                ", hikeLocation='" + hikeLocation + '\'' +
                ", hikeDate=" + hikeDate +
                ", hikeParking=" + hikeParking +
                ", hikeLength=" + hikeLength +
                ", hikeLevel='" + hikeLevel + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}

