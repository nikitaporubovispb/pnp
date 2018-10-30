package com.testrusoft.pnp.pnp;

import com.testrusoft.pnp.pnp.model.Car;
import com.testrusoft.pnp.pnp.model.Client;
import com.testrusoft.pnp.pnp.repository.CarsRepository;
import com.testrusoft.pnp.pnp.repository.ClientsRepository;
import com.testrusoft.pnp.pnp.service.ClientToDelete;
import com.testrusoft.pnp.pnp.service.NewClient;
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
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.*;


/**
 * Rest controller car rent tests class.
 *
 * @author  pnp
 * @version 0.1
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = PnpApplication.class, properties = "spring.profiles.active=test")
@WebAppConfiguration
@DirtiesContext(classMode= DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class CarsControllerTest {

    private MediaType contentType = new MediaType(
                MediaType.APPLICATION_JSON.getType(),
                MediaType.APPLICATION_JSON.getSubtype(),
                Charset.forName("utf8")
    );

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
    private CarsRepository carsRepository;
    @Autowired
    private ClientsRepository clientsRepository;

    @Before
    public void setup() {
        this.mvc = webAppContextSetup(webApplicationContext).build();
        this.carsRepository.deleteAllInBatch();
    }
    @Test
    public void getAllCars() throws Exception {

        Car car1 = new Car("Kia", 2001);
        carsRepository.save(car1);

        Car car2 = new Car("Lexus", 2001);
        carsRepository.save(car2);

        String exampleCars = json(Arrays.asList(car1, car2));

        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().json(exampleCars));
    }

    @Test
    public void getAllClients() throws Exception {

        Client client1 = new Client("Ivan", 1944);
        clientsRepository.save(client1);

        Client client2 = new Client("Petr", 1911);
        clientsRepository.save(client2);

        String exampleClients =  json(Arrays.asList(client1, client2));

        mvc.perform(get("/api/clients"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(contentType))
                .andExpect(content().json(exampleClients));
    }

    @Test
    public void createCars() throws Exception {

        Car car = new Car("Honda", 2002);
        Client client = new Client("Mark", 18);
        car.setClient(client);

        String exampleClient = json(new NewClient(client.getName(), client.getYear(), car.getBrandName(), car.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        Car baseCar = carsRepository.findByBrandNameAndClientName(car.getBrandName(), client.getName()).get();

        Assert.assertTrue(car.equalsWithClient(baseCar));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 1);
    }
    @Test
    public void deleteClient() throws Exception {
        Car car = new Car("Lada", 2005);
        Client client = new Client("Vasya", 22);
        car.setClient(client);

        String exampleClient = json(new NewClient(client.getName(), client.getYear(), car.getBrandName(), car.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        Optional<Car> baseCar = carsRepository.findByBrandNameAndClientName(car.getBrandName(), client.getName());

        Assert.assertTrue(baseCar.isPresent() && car.equalsWithClient(baseCar.get()));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 1);

        exampleClient = json(new ClientToDelete(client.getName(), car.getBrandName()));
        mvc.perform(delete("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        baseCar = carsRepository.findByBrandNameAndClientName(car.getBrandName(), client.getName());

        car.setClient(null);
        Optional<Car> baseCarWithoutClient = carsRepository.findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(car.getBrandName(), car.getYearOfManufacturing());

        Assert.assertTrue(!baseCar.isPresent());
        Assert.assertTrue(baseCarWithoutClient.get().equalsWithClient(car));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 0);
    }
    @Test
    public void deleteClientException() throws Exception {
        Car car = new Car("Lada", 2005);
        Client client = new Client("Vasya", 22);
        car.setClient(client);

        String exampleClient = json(new ClientToDelete(client.getName(), car.getBrandName()));
        mvc.perform(delete("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isNotFound());
    }
    @Test
    public void createCarsDublicate() throws Exception {
        Car car = new Car("Lada", 2005);
        Client client = new Client("Mark", 18);
        car.setClient(client);

        String exampleClient = json(new NewClient(client.getName(), client.getYear(), car.getBrandName(), car.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());


        Car baseCar = carsRepository.findByBrandNameAndClientName(car.getBrandName(), client.getName()).get();

        Assert.assertTrue((car.equalsWithClient(baseCar)));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 1);
    }
    @Test
    public void changeClientCar() throws Exception {

        // add first car
        Client client = new Client("Mark", 18);
        Car car1 = new Car("Honda", 2002);
        car1.setClient(client);

        String exampleClient = json(new NewClient(client.getName(), client.getYear(), car1.getBrandName(), car1.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        Car baseCar1 = carsRepository.findByBrandNameAndClientName(car1.getBrandName(), client.getName()).get();
        Assert.assertTrue((car1.equalsWithClient(baseCar1)) && (carsRepository.count() == 1));

        // change car
        Car car2 = new Car("Toyota", 2002);
        car2.setClient(client);

        car1.setClient(null);

        exampleClient = json(new NewClient(client.getName(), client.getYear(), car2.getBrandName(), car2.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        baseCar1 = carsRepository.findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(car1.getBrandName(), car1.getYearOfManufacturing()).get();
        Car baseCar2 = carsRepository.findByBrandNameAndClientName(car2.getBrandName(), client.getName()).get();

        Assert.assertTrue(car1.equalsWithClient(baseCar1));
        Assert.assertTrue(car2.equalsWithClient(baseCar2));
        Assert.assertTrue(carsRepository.count() == 2);
        Assert.assertTrue(clientsRepository.count() == 1);
    }
    public void changeCarClient() throws Exception {

        // add first car
        Client client1 = new Client("Mark", 18);
        Car car = new Car("Honda", 2002);
        car.setClient(client1);

        String exampleClient = json(new NewClient(client1.getName(), client1.getYear(), car.getBrandName(), car.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        Car baseCar = carsRepository.findByBrandNameAndClientName(car.getBrandName(), client1.getName()).get();
        Assert.assertTrue(car.equalsWithClient(baseCar));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 1);

        // delete client 1
        exampleClient = json(new ClientToDelete(client1.getName(), car.getBrandName()));
        mvc.perform(delete("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        // change car
        Client client2 = new Client("Jonny", 19);
        car.setClient(client2);

        exampleClient = json(new NewClient(client2.getName(), client2.getYear(), car.getBrandName(), car.getYearOfManufacturing()));
        mvc.perform(post("/api/client")
                .contentType(contentType)
                .content(exampleClient))
                .andExpect(status().isOk());

        baseCar = carsRepository.findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(car.getBrandName(), car.getYearOfManufacturing()).get();

        Assert.assertTrue(car.equalsWithClient(baseCar));
        Assert.assertTrue(carsRepository.count() == 1);
        Assert.assertTrue(clientsRepository.count() == 1);
    }

    private String json(Object o) throws IOException {
        MockHttpOutputMessage mockHttpOutputMessage = new MockHttpOutputMessage();
        this.mappingJackson2HttpMessageConverter.write(
                o, MediaType.APPLICATION_JSON, mockHttpOutputMessage);
        return mockHttpOutputMessage.getBodyAsString();
    }
}