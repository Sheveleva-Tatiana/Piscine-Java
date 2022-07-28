package edu.school21.reflection.classes;

public class Car {
    private String model;
    private int year;
    private double maxSpeed;
    private boolean isSold;

    public Car(String model, int year, double maxSpeed, boolean isSold) {
        this.model = model;
        this.year = year;
        this.maxSpeed = maxSpeed;
        this.isSold = isSold;
    }

    public Car() {
    }

    public void toSale() {
        isSold = true;
    }


    @Override
    public String toString() {
        return "Car{" +
                "model='" + model + '\'' +
                ", year=" + year +
                ", maxSpeed=" + maxSpeed +
                ", isSold=" + isSold +
                '}';
    }
}
