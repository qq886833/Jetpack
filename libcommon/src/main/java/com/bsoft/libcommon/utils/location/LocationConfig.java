package com.bsoft.libcommon.utils.location;

import java.io.Serializable;

public class LocationConfig implements Serializable {
    public String name;
    public double latitude;
    public double longitude;

    public LocationConfig() {
    }

    public LocationConfig(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public LocationConfig(String name, double latitude, double longitude) {
        this.name = name;
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
