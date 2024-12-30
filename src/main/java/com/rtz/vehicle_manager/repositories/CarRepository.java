package com.rtz.vehicle_manager.repositories;

import com.rtz.vehicle_manager.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, Long> {

}
