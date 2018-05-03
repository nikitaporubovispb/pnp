package com.testrusoft.pnp.pnp.service;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Client to delete class .
 *
 * @author pnp
 * @version 0.1
 */
@ApiModel(value="Клиент на удаление", description="Описание удаляемого клиента с автомобилем")
public class ClientToDelete {

    @ApiModelProperty(value = "Имя клиента")
    private String clientName;

    @ApiModelProperty(value = "Марка автомобиля")
    private String carBrandName;

    public ClientToDelete(String clientName, String carBrandName){
        this.clientName = clientName;
        this.carBrandName = carBrandName;
    }
    public ClientToDelete(){}

    public void setClientName(String clientName) {
        this.clientName = clientName;
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
}
