package com.qa.restassured.resources;

//enum is special class in java which has collection of constants or methods
public enum APIResources {
    AddPlaceAPI("/maps/api/place/add/json"),
    GetPlaceAPI("/maps/api/place/get/json"),
    DeletePlaceAPI("/maps/api/place/delete/json");
    private String resource;

    APIResources(String resource) {
        this.resource = resource;
    }

    public String getResource() {
        return resource;
    }
}
