package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "trip_change_history")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TripChangeHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id", nullable = false)
    private Trip trip;

    @Column(name = "change_description", nullable = false)
    private String changeDescription;

    @Column(name = "change_time", nullable = false)
    private LocalDateTime changeTime;
    @Column(name = "change_title", nullable = false)
    private String changeTitle;

}
