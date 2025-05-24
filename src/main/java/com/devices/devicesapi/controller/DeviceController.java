package com.devices.devicesapi.controller;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping
    public ResponseEntity<Void> createDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        deviceService.createDevice(deviceDTO);
        return ResponseEntity.status(201).build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceDTO deviceDTO) {
        deviceService.updateDevice(id, deviceDTO);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }

    @GetMapping("/brand/{state}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByState(@PathVariable String state) {
        return ResponseEntity.ok(deviceService.getDevicesByState(state));
    }

}
