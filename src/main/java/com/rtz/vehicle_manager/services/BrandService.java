package com.rtz.vehicle_manager.services;

import com.rtz.vehicle_manager.entities.Brand;
import com.rtz.vehicle_manager.errors.DuplicateBrandException;
import com.rtz.vehicle_manager.dto.BrandDTO;
import com.rtz.vehicle_manager.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    /**
     * Saves a new brand to the database
     * @param brandModel The brand model to save
     * @return The saved brand
     */
    public Brand saveBrand(BrandDTO brandModel) {
        if (brandRepository.existsByName(brandModel.getName().toUpperCase())) {
           throw new DuplicateBrandException();
        }
        Brand newBrand = new Brand();
        newBrand.setName(brandModel.getName().toUpperCase());
        if (brandModel.getModels() != null) {
            List<String> models = brandModel.getModels().stream()
                    .map(this::capitalizeWords)
                    .toList();
            newBrand.setModels(models);
        }
        return brandRepository.save(newBrand);
    }

    /**
     * Updates an existing brand in the database
     * @param id The id of the brand to update
     * @param brandModel The brand model to update
     * @return The updated brand
     */
    public Brand updateBrand(Long id, BrandDTO brandModel) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(brandModel.getName().toUpperCase());
        List<String> models = brandModel.getModels().stream()
                .map(this::capitalizeWords)
                .toList();
        brand.setModels(models);
        return brandRepository.save(brand);
    }

    /**
     * Retrieves all brands from the database
     * @return A list of all brands
     */
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    /**
     * Retrieves a brand by its id
     * @param brandId The id of the brand to retrieve
     * @return The brand with the given id
     */
    public Optional<Brand> getBrandById(Long brandId) {
        return brandRepository.findById(brandId);
    }

    /**
     * Adds new models to an existing brand
     * @param brand The brand to add the new models to
     * @param models The new models to add
     * @return The updated brand
     */
    public Brand addModelsToBrand(Brand brand, List<String> models) {
        List<String> newModels = filterNewModels(brand, models);
        // Add the new models to the current models list
        List<String> currentModels = brand.getModels();
        currentModels.addAll(newModels);
        // Update the brand with the new models
        brand.setModels(currentModels);
        return brandRepository.save(brand);
    }

    /**
     * Deletes a brand from the database
     * @param brandId The id of the brand to delete
     */
    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }

    /**
     * Capitalizes the first letter of each word in the input string
     * @param input The input string
     * @return The input string with the first letter of each word capitalized
     */
    private String capitalizeWords(String input) {
        if (input == null || input.isEmpty()) {
            return input;
        }

        return Arrays.stream(input.split(" "))
                .map(word -> word.substring(0, 1).toUpperCase() + word.substring(1).toLowerCase())
                .reduce((first, second) -> first + " " + second)
                .orElse("");
    }

    /**
     * Filters out the new models that are not already in the brand's models list
     * @param brand The brand to add the new models to
     * @param modelsToAdd The new models to add
     * @return The new models that are not already in the brand's models list in a capitalized format
     */
    private List<String> filterNewModels(Brand brand, List<String> modelsToAdd) {;
        List<String> newModels = new ArrayList<>();
        List<String> currentModelsLowerCase =  brand.getModels().stream()
                .map(String::toLowerCase)
                .toList();
        for (String model : modelsToAdd) {
            if (!currentModelsLowerCase.contains(model.toLowerCase())) {
                String formattedNewModel = capitalizeWords(model);
                newModels.add(formattedNewModel);
            }
        }
        return newModels;
    }

}
