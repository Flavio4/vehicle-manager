package com.rtz.vehicle_manager.services;

import com.rtz.vehicle_manager.entities.Brand;
import com.rtz.vehicle_manager.errors.DuplicateBrandException;
import com.rtz.vehicle_manager.models.BrandModel;
import com.rtz.vehicle_manager.repositories.BrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService {

    @Autowired
    private BrandRepository brandRepository;

    public Brand saveBrand(BrandModel brandModel) {
        if (brandRepository.existsByName(brandModel.getName())) {
           throw new DuplicateBrandException();
        }
        Brand newBrand = new Brand();
        newBrand.setName(brandModel.getName());
        newBrand.setModels(brandModel.getModels());
        return brandRepository.save(newBrand);
    }

    public Brand updateBrand(Long id, BrandModel brandModel) {
        Brand brand = new Brand();
        brand.setId(id);
        brand.setName(brandModel.getName());
        brand.setModels(brandModel.getModels());
        return brandRepository.save(brand);
    }

    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    public Optional<Brand> getBrandById(Long brandId) {
        return brandRepository.findById(brandId);
    }

    public Brand addModelsToBrand(Brand brand, List<String> models) {
        brand.getModels().addAll(models);
        return brandRepository.save(brand);
    }

    public void deleteBrand(Long brandId) {
        brandRepository.deleteById(brandId);
    }
}
