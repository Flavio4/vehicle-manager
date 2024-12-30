package com.rtz.vehicle_manager.repositories;

import com.rtz.vehicle_manager.entities.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BrandRepository extends JpaRepository<Brand, Long> {
    Brand findByName(String name);
    Boolean existsByName(String name);
}
