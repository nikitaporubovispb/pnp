package com.testrusoft.pnp.pnp;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

/**
 * Created by andr on 26.04.2018.
 */
@RunWith(SpringJUnit4ClassRunner.class)
public class CarsControllerTest {

    private MockMvc mvc;
    @Before
    public void setup() {
        this.mvc = standaloneSetup(new CarsController()).build();
    }
    @Test
    public void getAllCars() throws Exception {
    }

    @Test
    public void createCars() throws Exception {
        String exampleCar = "{\"brandName\": \"Hi\", \"yearOfManufacturing\": \"2003\", \"client\": \"Mark!\"}";

        mvc.perform(post("/api/cars")
                .contentType(MediaType.APPLICATION_JSON)
                .content(exampleCar))
                .andExpect(status().isCreated());
    }

    @Test
    public void getCarsById() throws Exception {
    }

    @Test
    public void updateCars() throws Exception {
    }

    @Test
    public void deleteCars() throws Exception {
    }

}