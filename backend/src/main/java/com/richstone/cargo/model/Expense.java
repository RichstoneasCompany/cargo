package com.richstone.cargo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.richstone.cargo.model.types.ExpenseType;
import com.richstone.cargo.model.types.TripStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "expenses")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
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
    @Enumerated(EnumType.STRING)
    private ExpenseType expenseType;

}
