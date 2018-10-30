package com.testrusoft.pnp.pnp;

import com.testrusoft.pnp.pnp.model.Car;
import com.testrusoft.pnp.pnp.model.Client;
import com.testrusoft.pnp.pnp.repository.CarsRepository;
import com.testrusoft.pnp.pnp.repository.ClientsRepository;
import com.testrusoft.pnp.pnp.service.ClientToDelete;
import com.testrusoft.pnp.pnp.service.NewClient;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


/**
 * Rest controller class car rent.
 *
 * @autor pnp
 * @version 0.1
 */
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
    @ApiOperation(value = "${CarsController.getAllCars.value}",
            notes = "Получение списка всех автомобилей")
    @GetMapping("/cars")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список всех автомобилей") })
    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }

    // Get All clients
    @ApiOperation(value = "${CarsController.getAllClients.value}",
            notes = "Получение списка всех клиентов")
    @GetMapping("/clients")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Список всех клиентов") })
    public List<Client> getAllClients() {
        return clientsRepository.findAll();
    }

    // Create a new client
    @ApiOperation(value = "${CarsController.createClient.value}",
            notes = "Создание нового клиента")
    @PostMapping("/client")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Клиент добавлен") })
    public ResponseEntity<?> createClient(@ApiParam(value = "Новый клиент с описанием автомобиля", required = true)
            @Valid @RequestBody NewClient newClient) {

        Client client = clientsRepository.findByNameAndYear(newClient.getClientName(), newClient.getClientYear())
                .orElse(new Client(newClient.getClientName(), newClient.getClientYear()));
        Car car;

        Car newCar = new Car(newClient.getCarBrandName(), newClient.getCarYearOfManufacturing());

        Optional<Car> oldCar = carsRepository.findByClient(client);
        if (oldCar.isPresent() && oldCar.get().equals(newCar)) {
            car = oldCar.get();
        } else {
            car = carsRepository
                    .findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(newClient.getCarBrandName(), newClient.getCarYearOfManufacturing())
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
    @ApiOperation(value = "${CarsController.deleteClient.value}",
            notes = "Удаление клиента, автомобиль остается в базе")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Клиент удален"),
            @ApiResponse(code = 404, message = "Клинент не найден") })
    public ResponseEntity<?> deleteClient(@ApiParam(value = "Описание клиента на удаление с арендуемым автомобилем", required = true)
            @Valid @RequestBody ClientToDelete clientToDelete) {

        Car car = carsRepository.findByBrandNameAndClientName(clientToDelete.getCarBrandName(), clientToDelete.getClientName())
                .orElseThrow(() -> new ResourceNotFoundException("Client", "name", clientToDelete.getClientName()));

        clientsRepository.delete(car.getClient());

        return ResponseEntity.ok().build();
    }
}