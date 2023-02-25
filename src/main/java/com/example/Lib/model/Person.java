package com.example.Lib.model;



import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.List;


@Entity
@Table(name = "Person")
public class Person {

    @NotEmpty(message = "ФИО не должно быть пустым")
    @Pattern(regexp = ".+ .+ .+", message = "ФИО должно быть вида: Иванов Иван Иванович")
    @Column(name = "full_name")
    private String fullName;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Min(value = 1900, message = "Год рождения должен быть корректным")
    @Max(value = 2023, message = "Год рождения должен быть корректным")
    @Column(name = "year_born")
    private int yearBorn;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private List<Book> books;

    public Person() {

    }

    public Person(int id, String fullName, int yearBorn) {
        this.fullName = fullName;
        this.yearBorn = yearBorn;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String name) {
        this.fullName = name;
    }

    public int getYearBorn() {
        return yearBorn;
    }

    public void setYearBorn(int age) {
        this.yearBorn = age;
    }

    public List<Book> getBooks() {
        return books;
    }

    public void setBooks(List<Book> books) {
        this.books = books;
    }
}