package com.example.algorhitmproject1;

import java.util.List;

class information {
    private int numCities;
    private String startCity;
    private String endCity;
    private List<adj> cityInfoMap; // list of city

    private int[][] dpTable;

    public information(int numCities, String startCity, String endCity, List<adj> cityInfoMap) {
        this.numCities = numCities;
        this.startCity = startCity;
        this.endCity = endCity;
        this.cityInfoMap = cityInfoMap;
    }

    public int getNumCities() {
        return numCities;
    }

    public void setNumCities(int numCities) {
        this.numCities = numCities;
    }

    public String getStartCity() {
        return startCity;
    }

    public void setStartCity(String startCity) {
        this.startCity = startCity;
    }

    public String getEndCity() {
        return endCity;
    }

    public void setEndCity(String endCity) {
        this.endCity = endCity;
    }

    public List<adj> getCityInfoMap() {
        return cityInfoMap;
    }

    public void setCityInfoMap(List<adj> cityInfoMap) {
        this.cityInfoMap = cityInfoMap;
    }

    public int[][] getDpTable() {
        return dpTable;
    }

    public void setDpTable(int[][] dpTable) {
        this.dpTable = dpTable;
    }

}
