package ru.nechunaev.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public class Book {

    private Long id;

    @NotBlank(message = "the name should not be empty")
    private String name;

    @NotBlank(message = "the author should not be empty")
    private String author;

    @Min(value = 0, message = "the minimum value must be at least 0")
    private int year;

    private Long personId;

    public Long getPersonId() {
        return personId;
    }

    public void setPersonId(Long personId) {
        this.personId = personId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
