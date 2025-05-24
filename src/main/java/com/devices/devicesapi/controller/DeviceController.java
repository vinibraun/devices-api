package com.devices.devicesapi.controller;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.mapper.DeviceMapper;
import com.devices.devicesapi.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "Manage devices in the system")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Create a new device", description = "Adds a new device to the database")
    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createDevice(deviceDTO);
        DeviceDTO responseDTO = DeviceMapper.toDto(device);
        return ResponseEntity.status(201).body((responseDTO));
    }

    @Operation(summary = "Update a device", description = "Update an existing device")
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.updateDevice(id, deviceDTO);
        DeviceDTO responseDTO = DeviceMapper.toDto(device);
        return ResponseEntity.ok().body((responseDTO));
    }

    @Operation(summary = "Delete a device", description = "Remove a device by its ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeviceDTO> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get device by ID", description = "Retrieve a device by its ID")
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @Operation(summary = "Get all devices", description = "Retrieve all devices")
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @Operation(summary = "Get device by Brand", description = "Retrieve a device by its Brand")
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }

    @Operation(summary = "Get device by State", description = "Retrieve a device by its State")
    @GetMapping("/state/{state}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByState(@PathVariable String state) {
        return ResponseEntity.ok(deviceService.getDevicesByState(state));
    }

}
