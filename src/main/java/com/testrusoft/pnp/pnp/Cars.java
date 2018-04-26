package com.testrusoft.pnp.pnp;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

/**
 * Created by root on 25.04.2018.
 */
@Entity
public class Cars {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String brandName;
    private int yearOfManufacturing;
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
    public int getYearOfManufacturing() {
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
}
