package com.rtz.vehicle_manager.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class BrandDTO {

    @NotNull(message = "El nombre de la marca es obligatorio")
    @NotEmpty(message = "El nombre de la marca no puede estar vacío")
    private String name;
    private List<String> models;

    public BrandDTO() {
    }

    public BrandDTO(Long id, String name, List<String> models) {
        this.name = name;
        this.models = models;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getModels() {
        return models;
    }

    public void setModels(List<String> models) {
        this.models = models;
    }

}
