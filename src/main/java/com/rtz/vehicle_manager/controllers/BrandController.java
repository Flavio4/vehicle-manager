package com.rtz.vehicle_manager.controllers;


import com.rtz.vehicle_manager.entities.Brand;
import com.rtz.vehicle_manager.models.BrandModel;
import com.rtz.vehicle_manager.services.BrandService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("v1/api/brands")
@Validated
public class BrandController {

    @Autowired
    private BrandService brandService;

    /*GET*/
    @GetMapping
    public List<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Brand> getBrandById(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/{id}/models")
    public ResponseEntity<List<String>> getBrandModels(@PathVariable Long id) {
        return brandService.getBrandById(id)
                .map(brand -> ResponseEntity.ok(brand.getModels()))
                .orElse(ResponseEntity.notFound().build());
    }

    /*POST*/
    @PostMapping
    public Brand createBrand(@Valid @RequestBody BrandModel createBrandModel) {
        return brandService.saveBrand(createBrandModel);
    }

    @PostMapping("/{id}/models")
    public ResponseEntity<Brand> addModelsToBrand(@PathVariable Long id, @RequestBody List<String> models) {
        Optional<Brand> brandOptional = brandService.getBrandById(id);
        if (brandOptional.isPresent()) {
            Brand brand = brandOptional.get();
            return ResponseEntity.ok(brandService.addModelsToBrand(brand, models));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*PUT*/
    @PutMapping("/{id}")
    public ResponseEntity<Brand> updateBrand(@PathVariable Long id,@Valid @RequestBody BrandModel updatedBrand) {
        Optional<Brand> brandOptional = brandService.getBrandById(id);
        if (brandOptional.isPresent()) {
            return ResponseEntity.ok(brandService.updateBrand(id, updatedBrand));
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    /*DELETE*/
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBrand(@PathVariable Long id) {
        if (brandService.getBrandById(id).isPresent()) {
            brandService.deleteBrand(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
