package com.testrusoft.pnp.pnp;

/**
 * Created by andr on 26.04.2018.
 */
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

    // Get All Cars
    @GetMapping("/cars")
    public List<Cars> getAllCars() {
        return carsRepository.findAll();
    }
    // Create a new Cars
    @PostMapping("/cars")
    public Cars createCars(@Valid @RequestBody Cars car) {
        return carsRepository.save(car);
    }

    // Get a Single Cars
    @GetMapping("/cars/{id}")
    public Cars getCarsById(@PathVariable(value = "id") Long id) {
        return carsRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cars", "id", id));
    }

    // Update a Cars
    @PutMapping("/cars/{id}")
    public Cars updateCars(@PathVariable(value = "id") Long noteId,
                           @Valid @RequestBody Cars noteDetails) {

        Cars car = carsRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cars", "id", noteId));

        car.setBrandName(noteDetails.getBrandName());
        car.setYearOfManufacturing(noteDetails.getYearOfManufacturing());
        car.setClient(noteDetails.getClient());

        Cars updatedCars = carsRepository.save(car);
        return updatedCars;
    }
    // Delete a Cars
    @DeleteMapping("/cars/{id}")
    public ResponseEntity<?> deleteCars(@PathVariable(value = "id") Long noteId) {
        Cars note = carsRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Cars", "id", noteId));

        carsRepository.delete(note);

        return ResponseEntity.ok().build();
    }
}