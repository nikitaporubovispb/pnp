package com.testrusoft.pnp.pnp;

/**
 * Created by andr on 01.05.2018.
 */
public class OldClient {
    private String name;
    private String brandName;

    public OldClient(String name, String brandName){
        this.name = name;
        this.brandName = brandName;
    }
    public OldClient(){}

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getBrandName() {
        return brandName;
    }
}
