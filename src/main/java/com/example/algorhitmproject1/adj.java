package com.example.algorhitmproject1;
import java.util.List;

public class adj {
    private String cityName ;
    private List<City> cities ;

    public adj(String cityName, List<City> cities) {
        this.cityName = cityName;
        this.cities = cities;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public List<City> getCities() {
        return cities;
    }

    public void setCities(List<City> cities) {
        this.cities = cities;
    }
}
