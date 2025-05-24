package com.devices.devicesapi.dto;

import com.devices.devicesapi.entity.DeviceState;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DeviceDTO {
    private Long id;
    private String name;
    private String brand;
    private DeviceState state;
    private LocalDateTime createdAt;

}
