package com.richstone.cargo.model;

import com.richstone.cargo.model.types.OtpStatus;
import com.richstone.cargo.model.types.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "trips")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Trip {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "route_id", referencedColumnName = "id")
    private Route route;
    private LocalDateTime departureTime;
    private LocalDateTime arrivalTime;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Income> incomes;
    @OneToMany(mappedBy = "trip", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Expense> expenses;
    @ManyToOne
    @JoinColumn(name = "assigned_driver_id", referencedColumnName = "id")
    private Driver assignedDriver;
    @Enumerated(EnumType.STRING)
    private TripStatus tripStatus;
    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name = "cargo_id", referencedColumnName = "id")
    private Cargo cargo;
    private boolean isDeleted = false;
    @Column(name = "trip_number")
    private String tripNumber;

    @Override
    public String toString() {
        return "Trip{" +
                "id=" + id +
                ", route=" + (route != null ? route.getId() : null) +
                ", departureTime=" + departureTime +
                ", arrivalTime=" + arrivalTime +
                ", assignedDriver=" + (assignedDriver != null ? assignedDriver.getId() : null) +
                ", tripStatus=" + tripStatus +
                ", isDeleted=" + isDeleted +
                ", tripNumber='" + tripNumber + '\'' +
                '}';
    }
}
