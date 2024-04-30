package com.richstone.cargo.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Table(name = "topics")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Topic {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @OneToMany(mappedBy = "topicId", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Question> questions;
}
