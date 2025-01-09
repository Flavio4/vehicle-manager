package com.rtz.vehicle_manager.controllers;

import com.rtz.vehicle_manager.entities.Car;
import com.rtz.vehicle_manager.dto.CarDTO;
import com.rtz.vehicle_manager.services.CarService;
import com.rtz.vehicle_manager.services.ImageService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    @PostMapping(consumes = {"multipart/form-data"})
    public CarDTO createCar(@Valid @RequestPart("carData") CarDTO newCar, @RequestPart(value = "images", required = false) List<MultipartFile> images) {
        return carService.saveNewCar(newCar, images);
    }

    @PostMapping(value = "/{carId}/images")
    public ResponseEntity<CarDTO> addImagesToCar(@PathVariable Long carId, @RequestParam(value = "images", required = false) List<MultipartFile> images) {
        return ResponseEntity.ok(carService.addImagesToCar(carId, images));
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

    @DeleteMapping("/images/{imageId}")
    public ResponseEntity<CarDTO> deleteImage(@PathVariable Long imageId) {
        carService.deleteCarImage(imageId);
        return ResponseEntity.noContent().build();
    }
}