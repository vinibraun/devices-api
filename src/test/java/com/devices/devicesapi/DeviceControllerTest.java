package com.devices.devicesapi;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.entity.DeviceState;
import com.devices.devicesapi.repository.DeviceRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class DeviceControllerTest { //Web Layer Integration Test

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private DeviceRepository deviceRepository;

    private Device testDevice;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();

        testDevice = new Device();
        testDevice.setName("Smartphone X");
        testDevice.setBrand("Samsung");
        testDevice.setState(DeviceState.AVAILABLE);
        testDevice.setCreatedAt(LocalDateTime.now());

        testDevice = deviceRepository.save(testDevice);
    }

    @Test
    public void shouldCreateDevice() throws Exception {
        String json = """
                {
                    "name": "Smartphone Y",
                    "brand": "Brand Y",
                    "state": "AVAILABLE"
                }
                """;

        mockMvc.perform(post("/api/devices")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Smartphone Y"))
                .andExpect(jsonPath("$.brand").value("Brand Y"));
    }

    @Test
    void shouldUpdateDeviceSuccessfully() throws Exception {

        // Changes that will be sent in the body of the update
        DeviceDTO updatedDevice = new DeviceDTO();
        updatedDevice.setName("Smartphone B");
        updatedDevice.setBrand("Brand B");
        updatedDevice.setState(DeviceState.INACTIVE); // State change

        String jsonUpdate = objectMapper.writeValueAsString(updatedDevice);

        // Update request with MockMvc
        mockMvc.perform(put("/api/devices/{id}", testDevice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonUpdate))
                .andExpect(status().isOk());

        // Validates whether the device has actually been updated in the database
        Device deviceAfterUpdate = deviceRepository.findById(testDevice.getId()).orElseThrow();
        assertEquals("Smartphone B", deviceAfterUpdate.getName());
        assertEquals("Brand B", deviceAfterUpdate.getBrand());
        assertEquals(DeviceState.INACTIVE, deviceAfterUpdate.getState());
    }

    @Test
    void shouldDeleteDeviceSuccessfully() throws Exception {
        mockMvc.perform(delete("/api/devices/{id}", testDevice.getId()))
                .andExpect(status().isNoContent());

        // Verification of existence
        assertFalse(deviceRepository.findById(testDevice.getId()).isPresent());
    }

    @Test
    public void shouldUpdateNamePatchDevice() throws Exception {
        String jsonPatch = "{\"name\":\"newName\"}";

        mockMvc.perform(patch("/api/devices/{id}", testDevice.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonPatch))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("newName"));
    }

    @Test
    public void shouldGetDeviceById() throws Exception {
        mockMvc.perform(get("/api/devices/{id}", testDevice.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Smartphone X"))
                .andExpect(jsonPath("$.brand").value("Samsung"))
                .andExpect(jsonPath("$.state").value("AVAILABLE"));
    }

    @Test
    public void shouldGetAllDevices() throws Exception {
        Device secondDevice = new Device();
        secondDevice.setName("Device 2");
        secondDevice.setBrand("Lenovo");
        secondDevice.setState(DeviceState.IN_USE);
        secondDevice.setCreatedAt(LocalDateTime.now());
        deviceRepository.save(secondDevice);

        mockMvc.perform(get("/api/devices")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void shouldReturnDevicesByBrand() throws Exception {
        mockMvc.perform(get("/api/devices/brand/{brand}", "Samsung")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].brand").value("Samsung"));
    }

    @Test
    void shouldReturnDevicesByState() throws Exception {
        mockMvc.perform(get("/api/devices/state/{state}", "AVAILABLE")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].state").value("AVAILABLE"));
    }

}
