package com.testrusoft.pnp.pnp;

import java.io.Serializable;

/**
 * Created by andr on 26.04.2018.
 */
public class NewClient {
    public String name;
    public Integer year;
    public String brandName;
    public Integer yearOfManufacturing;

    public NewClient(String name, Integer year, String brandName, Integer yearOfManufacturing){
        this.name = name;
        this.year = year;
        this.brandName = brandName;
        this.yearOfManufacturing = yearOfManufacturing;
    }
    public NewClient(){}
}
