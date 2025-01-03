package com.rtz.vehicle_manager.controllers;

import com.rtz.vehicle_manager.entities.Car;
import com.rtz.vehicle_manager.dto.CarDTO;
import com.rtz.vehicle_manager.services.CarService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("v1/api/cars")
@Validated
public class CarController {

    @Autowired
    private CarService carService;

    /*GET*/
    @GetMapping
    public List<CarDTO> getAllCars() {
        return carService.getAllCars();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CarDTO> getCarById(@PathVariable Long id) {
        CarDTO car = carService.getCarById(id);
        return ResponseEntity.ok(car);
    }

    /*POST*/
    @PostMapping
    public CarDTO createCar(@Valid @RequestBody CarDTO newCar) {
        return carService.saveNewCar(newCar);
    }

    /*PUT*/
    @PutMapping("/{id}")
    public ResponseEntity<CarDTO> updateCar(@PathVariable Long id,@Valid @RequestBody CarDTO updatedCar) {
      return ResponseEntity.ok(carService.updateCar(id, updatedCar));
    }

    /*DELETE*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCar(@PathVariable Long id) {
        carService.deleteCar(id);
        return ResponseEntity.noContent().build();
    }
}