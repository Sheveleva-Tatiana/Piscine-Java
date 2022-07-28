package edu.school21.reflection.classes;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String firstName;
    private String lastName;
    private int drivingExperiens;


    public User(String firstName, String lastName, int drivingExperience) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.drivingExperiens = drivingExperience;
    }

    public User() {
    }

    public int upDrivingExperience(int years) {
        return drivingExperiens + years;
    }

    public Double upDrivingExperience(Double years) {
        return years;
    }


    @Override
    public String toString() {
        return "User{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", drivingExperiens=" + drivingExperiens + " year(s)" +
                '}';
    }
}
