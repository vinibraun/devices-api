package com.devices.devicesapi.mapper;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.entity.Device;

import java.time.LocalDateTime;

public class DeviceMapper { //SINGLE RESPONSIBILITY PRINCIPLE (SRP)

    public static Device toEntity(DeviceDTO dto) {
        return Device.builder()
                .name(dto.getName())
                .brand(dto.getBrand())
                .state(dto.getState())
                .createdAt(LocalDateTime.now())
                .build();
    }

    public static DeviceDTO toDto(Device device) {
        return DeviceDTO.builder()
                .id(device.getId())
                .name(device.getName())
                .brand(device.getBrand())
                .state(device.getState())
                .createdAt(device.getCreatedAt())
                .build();
    }

}
