package com.example.book2u;

public class BookCarWash {

    String nickname;
    String carPlate;
    String carModel;
    String services;
    String carType;
    String timeSlot;

    public BookCarWash() {    }

    public BookCarWash(String nickname, String carPlate, String carModel, String services, String carType, String timeSlot) {
        this.nickname = nickname;
        this.carPlate = carPlate;
        this.carModel = carModel;
        this.services = services;
        this.carType = carType;
        this.timeSlot = timeSlot;
    }

    public String getNickname() {
        return nickname;
    }

    public String getCarPlate() {
        return carPlate;
    }

    public String getCarModel() {
        return carModel;
    }

    public String getServices() {
        return services;
    }

    public String getCarType() {
        return carType;
    }

    public String getTimeSlot() {
        return timeSlot;
    }
}