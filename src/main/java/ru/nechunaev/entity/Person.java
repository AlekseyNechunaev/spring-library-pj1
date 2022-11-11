package ru.nechunaev.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Person {

    private Long id;

    @NotBlank(message = "The name should not be empty")
    private String fullName;
    @Min(value = 1900, message = "The year of birth must not be less than")
    private int yearOfBirth;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public int getYearOfBirth() {
        return yearOfBirth;
    }

    public void setYearOfBirth(int yearOfBirth) {
        this.yearOfBirth = yearOfBirth;
    }
}
