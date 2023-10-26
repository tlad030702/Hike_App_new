package com.example.hike_app.models;

public class ObservationsModel {
    private int idObs;
    private String nameObs;
    private String dateObs;
    private String cmtObs;
    private int idHike;
    private HikeModel hike;
    public ObservationsModel(int idObs,String nameObs, String dateObs, String cmtObs, int idHike) {
        this.idObs = idObs;
        this.nameObs = nameObs;
        this.dateObs = dateObs;
        this.cmtObs = cmtObs;
        this.idHike = idHike;
    }

    public int getIdObs() {
        return idObs;
    }

    public void setIdObs(int idObs) {
        this.idObs = idObs;
    }

    public String getNameObs() {
        return nameObs;
    }

    public void setNameObs(String nameObs) {
        this.nameObs = nameObs;
    }

    public String getDateObs() {
        return dateObs;
    }

    public void setDateObs(String dateObs) {
        this.dateObs = dateObs;
    }

    public String getCmtObs() { return cmtObs; }

    public void setCmtObs(String cmtObs) {
        this.cmtObs = cmtObs;
    }

    public HikeModel getHike() {
        return hike;
    }

    public void setHike(HikeModel hike) {
        this.hike = hike;
    }
    public int getIdHike() {
        return idHike;
    }

    public void setIdHike(int idHike) {
        this.idHike = idHike;
    }
}
