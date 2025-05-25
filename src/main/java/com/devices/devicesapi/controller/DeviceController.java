package com.devices.devicesapi.controller;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.dto.ErrorResponse;
import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.mapper.DeviceMapper;
import com.devices.devicesapi.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/devices")
@Tag(name = "Devices", description = "Manage devices in the system")
public class DeviceController {

    private final DeviceService deviceService;

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @Operation(summary = "Create a new device", description = "Adds a new device to the database")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Device created successfully"),
            @ApiResponse(responseCode = "400", description = "Business rule violation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<DeviceDTO> createDevice(@Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.createDevice(deviceDTO);
        DeviceDTO responseDTO = DeviceMapper.toDto(device);
        return ResponseEntity.status(201).body((responseDTO));
    }

    @Operation(summary = "Update a device", description = "Update an existing device")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device updated successfully"),
            @ApiResponse(responseCode = "400", description = "Business rule violation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PutMapping("/{id}")
    public ResponseEntity<DeviceDTO> updateDevice(@PathVariable Long id, @Valid @RequestBody DeviceDTO deviceDTO) {
        Device device = deviceService.updateDevice(id, deviceDTO);
        DeviceDTO responseDTO = DeviceMapper.toDto(device);
        return ResponseEntity.ok().body((responseDTO));
    }

    @Operation(summary = "Patch a device", description = "Patch a device by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Device patched successfully"),
            @ApiResponse(responseCode = "400", description = "Business rule violation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PatchMapping("/{id}")
    public ResponseEntity<DeviceDTO> patchDevice(@PathVariable Long id, @RequestBody Map<String, Object> updates) {
        DeviceDTO updatedDevice = deviceService.patchDevice(id, updates);
        return ResponseEntity.ok(updatedDevice);
    }

    @Operation(summary = "Delete a device", description = "Remove a device by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Device deleted successfully"),
            @ApiResponse(responseCode = "400", description = "Business rule violation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<DeviceDTO> deleteDevice(@PathVariable Long id) {
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Get device by ID", description = "Retrieve a device by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{id}")
    public ResponseEntity<DeviceDTO> getDeviceById(@PathVariable Long id) {
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @Operation(summary = "Get all devices", description = "Retrieve all devices")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success")
    })
    @GetMapping
    public ResponseEntity<List<DeviceDTO>> getAllDevices() {
        return ResponseEntity.ok(deviceService.getAllDevices());
    }

    @Operation(summary = "Get devices by Brand", description = "Retrieve devices by their Brand")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/brand/{brand}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByBrand(@PathVariable String brand) {
        return ResponseEntity.ok(deviceService.getDevicesByBrand(brand));
    }

    @Operation(summary = "Get devices by State", description = "Retrieve devices by their State")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Success"),
            @ApiResponse(responseCode = "404", description = "Device not found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/state/{state}")
    public ResponseEntity<List<DeviceDTO>> getAllDeviceByState(@PathVariable String state) {
        return ResponseEntity.ok(deviceService.getDevicesByState(state));
    }

}
