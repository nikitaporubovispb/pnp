package com.testrusoft.pnp.pnp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Client class.
 *
 * @author pnp
 * @version 0.1
 */
@Entity
@Table(name = "Clients")
@ApiModel(value="Клиент арендатор", description="Описание клиента арендатора")
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "name")
    @ApiModelProperty(value = "Имя", notes = "Описание имени клиента")
    private String name;

    @NotNull
    @Column(name = "clientYear")
    @ApiModelProperty(value = "Год рождения", notes = "Описание года рождения клиента")
    private Integer year;

    @OneToOne
    @JoinColumn(name = "car")
    @ApiModelProperty(value = "Автомобиль", notes = "Описание арендованного автомобиля клиента")
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
        return Objects.hash(id, name, year);
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
        if (!(this.id == other.id))
            return false;
      return true;
    }
    public String toString() {
        return "id - " + id + " clientName: " + name + ", clientYear: " + year;
    }
}
