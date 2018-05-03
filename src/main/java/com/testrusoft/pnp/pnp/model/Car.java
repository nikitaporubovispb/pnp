package com.testrusoft.pnp.pnp.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Car rent class.
 *
 * @author pnp
 * @version 0.1
 */
@Entity
@Table(name = "Cars")
@ApiModel(value="Арендуемый автомобиль", description="Описание автомобиля")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "brand_name")
    @ApiModelProperty(value = "Марка", notes = "Описание марки автомобиля")
    private String brandName;

    @NotNull
    @Column(name = "year_of_manufacturing")
    @ApiModelProperty(value = "Год выпуска", notes = "Описание года выпуска автомобиля")
    private Integer yearOfManufacturing;

    @JoinColumn(name = "client")
    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER)
    @ApiModelProperty(value = "Клиент", notes = "Описание текущего арендатора автомобиля")
    private Client client;


    public Car(){}
    public Car(@NotNull String brandName, @NotNull Integer yearOfManufacturing){
        this.brandName = brandName;
        this.yearOfManufacturing = yearOfManufacturing;
    }

    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return id;
    }
    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
    public String getBrandName() {
        return brandName;
    }
    public void setYearOfManufacturing(int yearOfManufacturing) {
        this.yearOfManufacturing = yearOfManufacturing;
    }
    public Integer getYearOfManufacturing() {
        return yearOfManufacturing;
    }
    public void setClient(Client client) {
        this.client = client;
    }
    public Client getClient() {
        return client;
    }
    public boolean equalsWithClient(Object obj) {
        if (equals(obj)) {
            Car other = (Car) obj;
            if (client == null && other.client == null) {
                return true;
            } else {
                return client.equals((other.client));
            }
        }
        return false;
    }
    @Override
    public int hashCode(){
        return Objects.hash(brandName, yearOfManufacturing, client);
    }
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Car other = (Car) obj;
        if (!this.brandName.equals(other.brandName))
            return false;
        if (!this.yearOfManufacturing.equals(other.yearOfManufacturing))
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "ID:  " + id + " brandName: " + brandName + ", clientYear: " + yearOfManufacturing + " Client: " + client;
    }
}
