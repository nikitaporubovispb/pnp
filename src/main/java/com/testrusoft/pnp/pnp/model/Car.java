package com.testrusoft.pnp.pnp.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Objects;

/**
 * Created by root on 25.04.2018.
 */
@Entity
@Table(name = "Cars")
/*@NamedQuery(name = "Cars.findByBrandNameAndClientName",
        query = "select c from Cars c where c.brandName = ?1 and c.client.name = ?2")*/
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "brand_name")
    private String brandName;

    @NotNull
    @Column(name = "year_of_manufacturing")
    private Integer yearOfManufacturing;

    @JoinColumn(name = "client")
    @OneToOne(mappedBy = "car", fetch = FetchType.EAGER)
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
        return "ID:  " + id + " Brand name: " + brandName + ", year: " + yearOfManufacturing + " Client: " + client;
    }
}
