package com.rtz.vehicle_manager.services;

import com.rtz.vehicle_manager.dto.ImageDTO;
import com.rtz.vehicle_manager.entities.Brand;
import com.rtz.vehicle_manager.entities.Car;
import com.rtz.vehicle_manager.entities.Image;
import com.rtz.vehicle_manager.errors.BrandNotfoundException;
import com.rtz.vehicle_manager.errors.CarNotfoundException;
import com.rtz.vehicle_manager.errors.ModelNotfoundException;
import com.rtz.vehicle_manager.dto.CarDTO;
import com.rtz.vehicle_manager.repositories.CarRepository;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CarService {

    @Autowired
    private CarRepository carRepository;
    @Autowired
    private BrandService brandService;
    @Autowired
    private ImageService imageService;


    /**
     * Saves a new car to the database
     * @param car The car to save
     * @param images The images of the car
     * @return The saved car
     */
    @Transactional
    public CarDTO saveNewCar(CarDTO car, List<MultipartFile> images) {
        List<Image> carImages = new ArrayList<>();
        if (images != null && !images.isEmpty()) {
            carImages = imageService.processAndUploadCarImages(images);
        }
        Car newCar = mapCarDtoToEntity(car, carImages);
        return mapCarEntityToDto(carRepository.save(newCar));
    }

    /**
     * Updates an existing car in the database
     * @param id The ID of the car to update
     * @param updatedCarDto The car to update
     * @return The updated car
     */
    public CarDTO updateCar(Long id, CarDTO updatedCarDto) {
        Optional<Car> actualCar = carRepository.findById(id);
        if (actualCar.isPresent()) {
            List<Image> actualCarImages = actualCar.get().getImages();
            Car updatedCar = mapCarDtoToEntity(updatedCarDto, actualCarImages);
            updatedCar.setId(id);
            return mapCarEntityToDto(carRepository.save(updatedCar));
        } else {
            throw new CarNotfoundException(id);
        }
    }

    /**
     * Adds images to an existing car
     * @param id The ID of the car to add images to
     * @param images The images to add
     * @return The car with the added images
     */
    public CarDTO addImagesToCar(Long id, List<MultipartFile> images) {
        Optional<Car> carOptional = carRepository.findById(id);
        if (carOptional.isPresent()) {
            List<Image> carImages = new ArrayList<>();
            if (images != null && !images.isEmpty()) {
                carImages = imageService.processAndUploadCarImages(images);
            } else {
                throw new IllegalArgumentException("No se han enviado imágenes");
            }
            Car car = carOptional.get();
            carImages.forEach(image -> image.setCar(car));
            car.getImages().addAll(carImages);
            return mapCarEntityToDto(carRepository.save(car));
        } else {
            throw new CarNotfoundException(id);
        }
    }

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

    public void deleteCarImage(Long imageId) {
        imageService.deleteImage(imageId);
    }

    /**
     * Maps a CarDTO to a Car entity
     * @param carDTO The CarDTO to map
     * @return The Car entity
     */
    private Car mapCarDtoToEntity(CarDTO carDTO, List<Image> carImages) {
        Car car = new Car();
        car.setId(carDTO.getId());
        car.setYear(carDTO.getYear());
        car.setPrice(carDTO.getPrice());
        car.setStatus(carDTO.getStatus());
        car.setColor(carDTO.getColor());
        car.setPlate(carDTO.getPlate());
        car.setDescription(carDTO.getDescription());
        // Set the car to each image
        carImages.forEach(image -> image.setCar(car));
        // Set the images to the car
        car.setImages(carImages);
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
        carModel.setDescription(car.getDescription());
        List<Image> carImages = car.getImages();
        if (carImages != null) {
            //Transform the list of images to a list of ImageDTO
            List<ImageDTO> imagesDTO = carImages.stream()
                            .map(image -> new ImageDTO(image.getId(), image.getUrl()))
                    .toList();
            carModel.setImages(imagesDTO);
        }
        return carModel;
    }

}
