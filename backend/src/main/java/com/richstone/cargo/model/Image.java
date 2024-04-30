package com.richstone.cargo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Lob
    @Column(name = "imageBytes")
    private byte[] imageBytes;
    @JsonIgnore
    @Column(name = "userId")
    private Long userId;
    @Column(nullable = false, name = "name")
    private String name;

}
