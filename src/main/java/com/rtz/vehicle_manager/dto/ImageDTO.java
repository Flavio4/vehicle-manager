package com.rtz.vehicle_manager.dto;

import com.rtz.vehicle_manager.enums.CarStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ImageDTO {

    private Long id;

    @NotNull(message = "La url de la imagen es obligatoria")
    @NotEmpty(message = "La url de la imagen no puede estar vacía")
    private String url;

    public ImageDTO() {
    }

    public ImageDTO(Long id, String url) {
        this.id = id;
        this.url = url;
    }

    public ImageDTO(String url) {
        this.url = url;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }
}
