package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "cargos")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Cargo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private Double weight;
    private String name;
    @Column(name = "numberOfPallets")
    private Integer numberOfPallets;
    private Double temperature;
    @OneToOne
    @JoinColumn(name = "trip_id")
    private Trip trip;
}
