package com.testrusoft.pnp.pnp;

/**
 * Created by andr on 26.04.2018.
 */
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CarsController {

    @Autowired
    CarsRepository carsRepository;

    // Get All Car
    @ApiOperation(value = "Get All Car",
            notes = "Получение списка всех клиентов")
    @GetMapping("/cars")
    public List<Car> getAllCars() {
        return carsRepository.findAll();
    }
    // Create a new Car
    @ApiOperation(value = "Create a new Car",
            notes = "Создание нового клиента")
    @PostMapping("/cars")
    public Car createCars(@Valid @RequestBody Car car) {
        return carsRepository.save(car);
    }

    // Get a Single Car
    @GetMapping("/cars/{id}")
    @ApiOperation(value = "Get a Single Car",
            notes = "Получение клиента по индексу")
    public Car getCarsById(@PathVariable(value = "id") Long id) {
        return carsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Car", "id", id));
    }

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
        car.setClient(noteDetails.getClient());

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
}