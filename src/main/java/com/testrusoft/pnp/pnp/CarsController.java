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
    public List<Client> createClient(@Valid @RequestBody newClient request) {

        Client client = this.clientsRepository.findByName(request.name).orElse(new Client(request.name, request.year));

        clientsRepository.save(client);

        Car newCar = new Car(request.brandName, request.yearOfManufacturing);

        Optional<Car> oldCar = carsRepository.findByClient(client);
        if (oldCar.isPresent()) {
            Car car = oldCar.get();
            if (!car.equals(newCar)) {
                car.setClient(null);
                carsRepository.save(car);

                newCar.setClient(client);
                carsRepository.save(newCar);
            }
        } else {
            newCar.setClient(client);
            carsRepository.save(newCar);
        }

        return clientsRepository.findAll();
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