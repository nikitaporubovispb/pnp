package com.testrusoft.pnp.pnp;

import com.testrusoft.pnp.pnp.model.Car;
import com.testrusoft.pnp.pnp.model.Client;
import com.testrusoft.pnp.pnp.repository.CarsRepository;
import com.testrusoft.pnp.pnp.repository.ClientsRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.mock.http.MockHttpOutputMessage;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;
import static org.springframework.http.converter.json.Jackson2ObjectMapperBuilder.json;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


/**
 * Created by andr on 26.04.2018.
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = PnpApplication.class)
@WebAppConfiguration
public class CarsControllerTest {

    private MediaType contentType = new MediaType(MediaType.APPLICATION_JSON.getType(),
            MediaType.APPLICATION_JSON.getSubtype(),
            Charset.forName("utf8"));

    private HttpMessageConverter mappingJackson2HttpMessageConverter;
    private MockMvc mvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    void setConverters(HttpMessageConverter<?>[] converters) {

        this.mappingJackson2HttpMessageConverter = Arrays.asList(converters).stream()
                .filter(hmc -> hmc instanceof MappingJackson2HttpMessageConverter)
                .findAny()
                .orElse(null);

        assertNotNull("the JSON message converter must not be null",
                this.mappingJackson2HttpMessageConverter);
    }
    @Autowired
    CarsRepository carsRepository;
    @Autowired
    ClientsRepository clientsRepository;
    static int id = 0;
    @Before
    public void setup() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        this.carsRepository.deleteAllInBatch();
    }
    @Test
    public void getAllCars() throws Exception {

        Car car1 = new Car("Kia", 2001);
        car1.setId((long)(++id));
        carsRepository.save(car1);

        Car car2 = new Car("Lexus", 2001);
        car2.setId((long)(++id));
        carsRepository.save(car2);

        String exampleCars = "[" + json(car1) + "," + json(car2) + "]";

        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().json(exampleCars));
    }

    @Test
    public void createCars() throws Exception {

        String exampleClient = json(new newClient("Mark", 18, "Honda", 2002));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        Car car = new Car("Honda", 2002);
        car.setId((long)(++id));

        Assert.assertTrue(car.equals(carsRepository.findById((long)(id)).get()));
    }

    @Test
    public void getCarsById() throws Exception {
        Car car = new Car("UAZ", 2003);
        car.setId((long)(++id));
        carsRepository.save(car);

        String exampleCar = json(car);
        mvc.perform(get("/api/cars/" + id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().json(exampleCar));
    }

    protected String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}