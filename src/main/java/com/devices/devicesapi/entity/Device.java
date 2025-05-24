package com.devices.devicesapi.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "DEVICES")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Device {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String brand;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeviceState state;

    @Column(name = "CREATION_TIME", nullable = false, updatable = false) //The "updatable" anotation doesnt let JPA updates this column values
    private LocalDateTime createdAt;

    @PrePersist //This method executes itself before the entity is saved in DB
    public void PrePersist() {
        this.createdAt = LocalDateTime.now();
    }

}
