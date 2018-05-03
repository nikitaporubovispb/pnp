package com.testrusoft.pnp.pnp.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * New client class.
 *
 * @author pnp
 * @version 0.1
 */
@ApiModel(value="Новый клиент", description="Описание нового клиента с автомобилем")
public class NewClient {
    @ApiModelProperty(value = "Имя клиента")
    private String clientName;

    @ApiModelProperty(value = "Год рождения клиента")
    private Integer clientYear;

    @ApiModelProperty(value = "Марка автомобиля")
    private String carBrandName;

    @ApiModelProperty(value = "Год выпуска")
    private Integer carYearOfManufacturing;

    public NewClient(String clientName, Integer clientYear, String CarBrandName, Integer CarYearOfManufacturing){
        this.clientName = clientName;
        this.clientYear = clientYear;
        this.carBrandName = CarBrandName;
        this.carYearOfManufacturing = CarYearOfManufacturing;
    }
    public NewClient(){}

    public void setClientName(String clientName) {
        this.clientName = clientName;
    }

    public Integer getClientYear() {
        return clientYear;
    }

    public void setClientYear(Integer clientYear) {
        this.clientYear = clientYear;
    }

    public String getClientName() {
        return clientName;
    }

    public void setCarBrandName(String carBrandName) {
        this.carBrandName = carBrandName;
    }

    public String getCarBrandName() {
        return carBrandName;
    }

    public void setCarYearOfManufacturing(Integer carYearOfManufacturing) {
        this.carYearOfManufacturing = carYearOfManufacturing;
    }

    public Integer getCarYearOfManufacturing() {
        return carYearOfManufacturing;
    }

    @Override
    public String toString(){
        return "Name: " + clientName + " Year: " + clientYear + " carBrandName: " + carBrandName + " carYearOfManufacturing: " + carYearOfManufacturing;
    }
}
