package com.example.algorhitmproject1;

public class City {
    // cit attributes
    private String cityName;
    private int petrolCost;
    private int hotelCost;

    public City(String cityName, int petrolCost, int hotelCost) {
        this.cityName = cityName;
        this.petrolCost = petrolCost;
        this.hotelCost = hotelCost;
    }

    public String getCityName() {
        return cityName;
    }

    public int getPetrolCost() {
        return petrolCost;
    }

    public int getHotelCost() {
        return hotelCost;
    }
}
