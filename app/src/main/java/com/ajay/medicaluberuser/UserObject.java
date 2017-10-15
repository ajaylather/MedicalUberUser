package com.ajay.medicaluberuser;

/**
 * Created by ajay on 10/15/17.
 */

public class UserObject {
    double lat;
    double lng;
    String id;

    UserObject(){}

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
