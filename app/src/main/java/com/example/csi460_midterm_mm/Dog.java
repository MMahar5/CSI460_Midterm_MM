package com.example.csi460_midterm_mm;

import android.graphics.Bitmap;

//Class for creating a dog object
public class Dog {
    //Declare variables for our dog properties
    private Integer id;
    private String name;
    private String breed;
    private Integer age;
    private String gender;
    private String furColor;
    private String vaccinated;
    private Bitmap image;

    //Constructor

    public Dog(Integer id, String name, String breed, Integer age, String gender, String furColor, String vaccinated, Bitmap image) {
        this.id = id;
        this.name = name;
        this.breed = breed;
        this.age = age;
        this.gender = gender;
        this.furColor = furColor;
        this.vaccinated = vaccinated;
        this.image = image;
    }

    //Getters and Setters

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBreed() {
        return breed;
    }

    public void setBreed(String breed) {
        this.breed = breed;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getFurColor() {
        return furColor;
    }

    public void setFurColor(String furColor) {
        this.furColor = furColor;
    }

    public String getVaccinated() {
        return vaccinated;
    }

    public void setVaccinated(String vaccinated) {
        this.vaccinated = vaccinated;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }
}
