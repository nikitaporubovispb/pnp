package com.testrusoft.pnp.pnp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.Date;
import java.util.Objects;

/**
 * Created by root on 25.04.2018.
 */
@Entity
@Table(name = "Cars")
@EntityListeners(AuditingEntityListener.class)
@JsonIgnoreProperties(value = {"createdAt", "updatedAt"},
        allowGetters = true)
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    private String brandName;
    private Integer yearOfManufacturing;
    private String client;

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
    public void setClient(String client) {
        this.client = client;
    }
    public String getClient() {
        return client;
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
        Cars other = (Cars) obj;
        if (this.brandName != other.brandName)
            return false;
        if (this.yearOfManufacturing != other.yearOfManufacturing)
            return false;
        if (this.client != other.client)
            return false;
        return true;
    }
    @Override
    public String toString() {
        return "Brand name: " + brandName + ", year: " + yearOfManufacturing + ", client : " + client;
    }
}
