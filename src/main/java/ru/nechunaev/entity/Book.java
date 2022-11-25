package ru.nechunaev.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.util.Date;

@Entity
@Table(name = "book")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @NotBlank(message = "the name should not be empty")
    @Column(name = "name")
    private String name;

    @NotBlank(message = "the author should not be empty")
    @Column(name = "author")
    private String author;

    @Min(value = 0, message = "the minimum value must be at least 0")
    @Column(name = "year")
    private int year;

    @ManyToOne
    @JoinColumn(name = "person_id")
    private Person person;

    @Temporal(TemporalType.TIMESTAMP)
    @CreationTimestamp
    @Column(name = "reservation_date")
    private Date reservationDate;

    @Transient
    private boolean isExpired;

    public Long getId() {
        return id;
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

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public boolean getIsExpired() {
        return isExpired;
    }

    public void setIsExpired(boolean isExpired) {
        this.isExpired = isExpired;
    }

    public Date getReservationDate() {
        return reservationDate;
    }
}
