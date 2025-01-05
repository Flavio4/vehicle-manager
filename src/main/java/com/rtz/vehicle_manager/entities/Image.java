package com.rtz.vehicle_manager.entities;


import jakarta.persistence.*;

@Entity
@Table(name = "car_images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn(name = "car_id", nullable = false)
    private Car car;

    public Image() {}

    public Image(Long id, String name, String url, Car car) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.car = car;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }
}
