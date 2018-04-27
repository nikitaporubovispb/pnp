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


    // Get All Car
    @ApiOperation(value = "Get All Car",
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

        Client client = new Client(request.name, request.year);
        Car car = new Car(request.brandName, request.yearOfManufacturing);
        /*
        car.setClient(client);
        client.setCar(car);
        */
        carsRepository.save(car);
        clientsRepository.save(client);
/*
        System.out.println(" *** ");
        for (Client book : clientsRepository.findAll()) {
            System.out.println(book.toString());
        }
*/
        return clientsRepository.findAll();
    }

    // Get a Single Car
    @GetMapping("/cars/{id}")
    @ApiOperation(value = "Get a Single Car",
            notes = "Получение клиента по индексу")
    public Car getCarsById(@PathVariable(value = "id") Long id) {
        return carsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
    }
    /*
    // Update a Car
    @PutMapping("/cars/{id}")
    @ApiOperation(value = "Update a Car",
            notes = "Обновление клиента")
    public Car updateCars(@PathVariable(value = "id") Long noteId,
                          @Valid @RequestBody Car noteDetails) {

        Car car = carsRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", noteId));

        car.setBrandName(noteDetails.getBrandName());
        car.setYearOfManufacturing(noteDetails.getYearOfManufacturing());
//        car.setClient(noteDetails.getClient());

        Car updatedCar = carsRepository.save(car);
        return updatedCar;
    }
    // Delete a Car
    @DeleteMapping("/cars/{id}")
    @ApiOperation(value = "Delete a Car",
            notes = "Удаление клиента")
    public ResponseEntity<?> deleteCars(@PathVariable(value = "id") Long noteId) {
        Car note = carsRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", noteId));

        carsRepository.delete(note);

        return ResponseEntity.ok().build();
    }
    */
}