package com.rtz.vehicle_manager.services;

import com.rtz.vehicle_manager.entities.Brand;
import com.rtz.vehicle_manager.entities.Car;
import com.rtz.vehicle_manager.errors.BrandNotfoundException;
import com.rtz.vehicle_manager.errors.CarNotfoundException;
import com.rtz.vehicle_manager.errors.ModelNotfoundException;
import com.rtz.vehicle_manager.dto.CarDTO;
import com.rtz.vehicle_manager.repositories.CarRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BrandService brandService;

    /**
     * Retrieves all cars from the database
     * @return A list of all cars
     */
    public List<CarDTO> getAllCars() {
        List<Car> carList = carRepository.findAll();
        return carList.stream()
                .map(this::mapCarEntityToDto)
                .toList();
    }

    /**
     * Retrieves a car by its ID
     * @param id The ID of the car to retrieve
     * @return The car with the given ID
     */
    public CarDTO getCarById(Long id) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            return mapCarEntityToDto(carOptional.get());
        } else {
            throw new CarNotfoundException(id);
        }
    }

    /**
     * Saves a new car to the database
     * @param car The car to save
     * @return The saved car
     */
    public CarDTO saveNewCar(CarDTO car) {
        Car newCar = mapCarDtoToEntity(car);
        return mapCarEntityToDto(carRepository.save(newCar));
    }

    /**
     * Updates an existing car in the database
     * @param id The ID of the car to update
     * @param updatedCarDto The car to update
     * @return The updated car
     */
    public CarDTO updateCar(Long id, CarDTO updatedCarDto) {
        if (carRepository.existsById(id)) {
            Car updatedCar = mapCarDtoToEntity(updatedCarDto);
            updatedCar.setId(id);
            return mapCarEntityToDto(carRepository.save(updatedCar));
        } else {
            throw new CarNotfoundException(id);
        }
    }

    /**
     * Deletes a car from the database
     * @param id The ID of the car to delete
     */
    public void deleteCar(Long id) {
        if (carRepository.existsById(id)) {
            carRepository.deleteById(id);
        } else {
            throw new CarNotfoundException(id);
        }
    }

    /**
     * Maps a CarDTO to a Car entity
     * @param carDTO The CarDTO to map
     * @return The Car entity
     */
    private Car mapCarDtoToEntity(CarDTO carDTO) {
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setYear(carDTO.getYear());
        car.setPrice(carDTO.getPrice());
        car.setStatus(carDTO.getStatus());
        car.setColor(carDTO.getColor());
        car.setPlate(carDTO.getPlate());
        Optional<Brand> brandOptional = brandService.getBrandById(carDTO.getBrandId());
        // Validate that the brand exists and that the model belongs to the brand
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            car.setBrand(brand);
            if (brand.getModels().contains(carDTO.getModel())) {
                car.setModel(carDTO.getModel());
            } else {
                throw new ModelNotfoundException("El modelo " + carDTO.getModel() + " no pertenece a la marca " + brand.getName());
            }
        } else {
            throw new BrandNotfoundException("La marca con ID " + carDTO.getBrandId() + " no existe");
        }
        return car;
    }

    /**
     * Maps a Car entity to a CarDTO
     * @param car The Car entity to map
     * @return The CarDTO
     */
    private CarDTO mapCarEntityToDto(Car car) {
        CarDTO carModel = new CarDTO();
        carModel.setId(car.getId());
        carModel.setYear(car.getYear());
        carModel.setPrice(car.getPrice());
        carModel.setStatus(car.getStatus());
        carModel.setColor(car.getColor());
        carModel.setBrandId(car.getBrand().getId());
        carModel.setBrandName(car.getBrand().getName());
        carModel.setModel(car.getModel());
        carModel.setPlate(car.getPlate());
        return carModel;
    }

}
