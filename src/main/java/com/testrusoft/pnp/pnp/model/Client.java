package com.testrusoft.pnp.pnp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

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
/*
    @OneToOne(fetch = FetchType.EAGER,
            cascade=CascadeType.ALL,
            mappedBy = "client")
    @PrimaryKeyJoinColumn
    private Car car;
*/
    public Client(){}
    public Client(@NotNull String name, @NotNull Integer year){
        this.name = name;
        this.year = year;
    }

    // TODO getters setters

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
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }

    /*    public void setCar(Car car) {
                                this.car = car;
                            }
                        */
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
/*        if (this.client != other.client)
            return false;
 */      return true;
    }
    public String toString() {
        return "name: " + name + ", year: " + year;
    }
}
