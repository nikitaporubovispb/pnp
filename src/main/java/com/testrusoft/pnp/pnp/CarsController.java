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

        Car newCar = new Car(newClient.getCarBrandName(), newClient.getCarYearOfManufacturing());
        Optional<Client> client = clientsRepository
                .findByNameAndYear(newClient.getClientName(), newClient.getClientYear());

        if (client.isPresent()){
            Optional<Car> oldCar = carsRepository.findByClient(client.get());
            if (oldCar.isPresent() && oldCar.get().equals(newCar)) {
                ; // same car
            } else {
                changeCar(client.get(), newCar);
            }
        } else {
            changeCar(new Client(newClient.getClientName(), newClient.getClientYear()), newCar);
        }

        return ResponseEntity.ok().build();
    }
    private void changeCar(Client client, Car newCar){
        Car car = carsRepository
                .findFirst1ByBrandNameAndYearOfManufacturingAndClientIsNull(newCar.getBrandName(), newCar.getYearOfManufacturing())
                .orElse(newCar);

        clientsRepository.save(client);

        car.setClient(client);
        client.setCar(car);

        carsRepository.save(car);
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