package com.testrusoft.pnp.pnp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by andr on 26.04.2018.
 */
@Entity
@Table(name = "Clients")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    private String name;

    @NotNull
    @Column(name = "year")
    private Integer year;

    @OneToOne
    @JoinColumn(name = "car")
    private Car car;

    public Client(){}
    public Client(@NotNull String name, @NotNull Integer year){
        this.name = name;
        this.year = year;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public void setYear(Integer year) {
        this.year = year;
    }
    public Integer getYear() {
        return year;
    }

    public void setCar(Car car) {
        this.car = car;
    }
    public int hashCode(){
        return Objects.hash(name, year, car);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Client other = (Client) obj;
        if (!this.name.equals(other.name))
            return false;
        if (!this.year.equals(other.year))
            return false;
      return true;
    }
    public String toString() {
        return "id - " + id + " name: " + name + ", year: " + year;
    }
}
