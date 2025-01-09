package com.rtz.vehicle_manager.dto;

import com.rtz.vehicle_manager.enums.CarStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.List;

public class ImageDTO {

    private Long id;

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

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
