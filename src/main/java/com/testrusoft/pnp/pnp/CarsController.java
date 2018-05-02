package com.testrusoft.pnp.pnp;

/**
 * Created by andr on 26.04.2018.
 */
import com.testrusoft.pnp.pnp.model.Car;
import com.testrusoft.pnp.pnp.model.Client;
import com.testrusoft.pnp.pnp.repository.CarsRepository;
import com.testrusoft.pnp.pnp.repository.ClientsRepository;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class CarsController {


    private final CarsRepository carsRepository;
    private final ClientsRepository clientsRepository;
    @Autowired
    CarsController(CarsRepository carsRepository,
                   ClientsRepository clientsRepository) {
        this.carsRepository = carsRepository;
        this.clientsRepository = clientsRepository;
    }

    // Get All cars
    @ApiOperation(value = "Get All Cars",
            notes = "Получение списка всех машин")
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }

    // Get All clients
    @ApiOperation(value = "Get All Clients",
            notes = "Получение списка всех клиентов")
    @GetMapping("/clients")
    public List<Client> getAllClients() {
        return clientsRepository.findAll();
    }

    // Create a new client
    @ApiOperation(value = "Create a new client",
            notes = "Создание нового клиента")
    @PostMapping("/client")
    public ResponseEntity<?> createClient(@Valid @RequestBody NewClient newClient) {

        Client client = clientsRepository.findByNameAndYear(newClient.name, newClient.year)
                .orElse(new Client(newClient.name, newClient.year));
        Car car;

        Car newCar = new Car(newClient.brandName, newClient.yearOfManufacturing);

        Optional<Car> oldCar = carsRepository.findByClient(client);
        if (oldCar.isPresent() && oldCar.get().equals(newCar)) {
            car = oldCar.get();
        } else {
            car = carsRepository.findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(newClient.brandName, newClient.yearOfManufacturing)
                    .orElse(newCar);
        }

        clientsRepository.save(client);

        car.setClient(client);
        client.setCar(car);

        carsRepository.save(car);

        return ResponseEntity.ok().build();
    }

    // Delete a client
    @DeleteMapping("/client")
    @ApiOperation(value = "Delete a Client",
            notes = "Удаление клиента")
    public ResponseEntity<?> deleteClient(@Valid @RequestBody OldClient oldClient) {

        Car car = carsRepository.findByBrandNameAndClientName(oldClient.getBrandName(), oldClient.getName())
                .orElseThrow(() -> new ResourceNotFoundException("Client", "name", oldClient.getName()));

        clientsRepository.delete(car.getClient());

        return ResponseEntity.ok().build();
    }
}