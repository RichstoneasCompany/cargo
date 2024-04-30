package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "incomes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Income {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trip_id", referencedColumnName = "id")
    private Trip trip;
    private Double amount;
    private String description;
    private LocalDate date;
    @ManyToOne
    @JoinColumn(name = "payslip_id", referencedColumnName = "id")
    private Payslip payslip;

}
