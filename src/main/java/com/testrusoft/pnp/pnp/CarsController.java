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
import javax.validation.constraints.Null;
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
    public Car createClient(@Valid @RequestBody newClient request) {
        System.out.println("**********************");

        Client client;
        Car car;

        Optional<Client> oldClient = clientsRepository.findByNameAndYear(request.name, request.year);
        if (oldClient.isPresent()){
            System.out.println("oldClient.isPresent()");
            client = oldClient.get();
            car = new Car(request.brandName, request.yearOfManufacturing);

            Optional<Car> oldCar = carsRepository.findByClient(client);
            if (oldCar.get().equals(car)) {
                System.out.println("oldCar.get().equals(car)");
                car = oldCar.get();
            } else {
                System.out.println("NOT   oldCar.get().equals(car)");
                System.out.println("oldCar.get() -- " + oldCar.get());
                System.out.println("car -- " + car);

                oldCar.get().setClient(null);
                carsRepository.saveAndFlush(oldCar.get());

                System.out.println(clientsRepository.findAll());
                System.out.println(carsRepository.findAll());
            }
        } else {
            System.out.println("NOT     oldClient.isPresent()");
            client = new Client(request.name, request.year);

            Optional<Car> oldCar = carsRepository.findByBrandNameAndYearOfManufacturingAndClientIsNull(request.brandName, request.yearOfManufacturing);
            if (oldCar.isPresent()){
                System.out.println("oldCar.isPresent()");
                car = oldCar.get();
            } else {
                System.out.println("NOT    oldCar.isPresent()");
                car = new Car(request.brandName, request.yearOfManufacturing);
            }

        }

        car.setClient(client);
        clientsRepository.save(client);

        carsRepository.save(car);
        System.out.println("end");
        System.out.println(clientsRepository.findAll());
        System.out.println(carsRepository.findAll());
        System.out.println(client);
        return carsRepository.findByClient(client).get();
    }

    // Delete a client
    @DeleteMapping("/client/{id}")
    @ApiOperation(value = "Delete a Car",
            notes = "Удаление клиента")
    public ResponseEntity<?> deleteCars(@PathVariable(value = "id") Long id) {
        Client client = clientsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Client", "id", id));

        clientsRepository.delete(client);

        return ResponseEntity.ok().build();
    }
}