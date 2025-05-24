package com.devices.devicesapi.repository;

import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.entity.DeviceState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeviceRepository extends JpaRepository<Device, Long> {
    List<Device> findByBrand(String brand);
    List<Device> findByState(DeviceState state);
}
