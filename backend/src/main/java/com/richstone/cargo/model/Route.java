package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "routes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "start_location_id", referencedColumnName = "id")
    private Location startLocationId;
    @ManyToOne
    @JoinColumn(name = "end_location_id", referencedColumnName = "id")
    private Location endLocationId;
    @Column(name = "name")
    private String name;

}
