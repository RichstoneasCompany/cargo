package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "trucks")
public class Truck {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String model;
    private String numberPlate;
    private Integer maxPermMass;
    private Integer mass;
    private Double axleLoad;
    private Double height;
    private Double width;
    private Integer length;
    @ManyToOne
    @JoinColumn(name = "driver_id")
    private Driver driver;


    @Override
    public String toString() {
        return "Truck{" +
                "id=" + id +
                ", model='" + model + '\'' +
                ", numberPlate='" + numberPlate + '\'' +
                ", maxPermMass=" + maxPermMass +
                ", mass=" + mass +
                ", axleLoad=" + axleLoad +
                ", height=" + height +
                ", width=" + width +
                ", length=" + length +
                '}';
    }
}
