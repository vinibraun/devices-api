package com.devices.devicesapi;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.entity.DeviceState;
import com.devices.devicesapi.mapper.DeviceMapper;
import com.devices.devicesapi.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
public class DeviceControllerTest { //Web Layer Integration Test

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    public void successDeviceCreation() throws Exception {
        String json = """
                {
                    "name": "Smartphone X",
                    "brand": "Brand Y",
                    "state": "AVAILABLE"
                }
                """;

        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Smartphone X"))
                .andExpect(jsonPath("$.brand").value("Brand Y"));
    }

    @Test
    void shouldUpdateDeviceSuccessfully() throws Exception {
        // First: create a new device in the database to be able to update
        DeviceDTO newDevice = new DeviceDTO();
        newDevice.setName("Smartphone A");
        newDevice.setBrand("Brand A");
        newDevice.setState(DeviceState.AVAILABLE);

        Device savedDevice = deviceRepository.save(DeviceMapper.toEntity(newDevice));

        // Changes that will be sent in the body of the update
        DeviceDTO updatedDevice = new DeviceDTO();
        updatedDevice.setName("Smartphone B");
        updatedDevice.setBrand("Brand B");
        updatedDevice.setState(DeviceState.INACTIVE); // State change

        String jsonUpdate = objectMapper.writeValueAsString(updatedDevice);

        // Update request with MockMvc
        mockMvc.perform(put("/api/devices/{id}", savedDevice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk());

        // Validates whether the device has actually been updated in the database
        Device deviceAfterUpdate = deviceRepository.findById(savedDevice.getId()).orElseThrow();
        assertEquals("Smartphone B", deviceAfterUpdate.getName());
        assertEquals("Brand B", deviceAfterUpdate.getBrand());
        assertEquals(DeviceState.INACTIVE, deviceAfterUpdate.getState());
    }

    @Test
    void shouldDeleteDeviceSuccessfully() throws Exception {
        DeviceDTO newDevice = new DeviceDTO();
        newDevice.setName("Device To Delete");
        newDevice.setBrand("DeleteBrand");
        newDevice.setState(DeviceState.AVAILABLE);

        Device savedDevice = deviceRepository.save(DeviceMapper.toEntity(newDevice));

        mockMvc.perform(delete("/api/devices/{id}", savedDevice.getId()))
                .andExpect(status().isNoContent());

        // Verification of existence
        assertFalse(deviceRepository.findById(savedDevice.getId()).isPresent());
    }


}
