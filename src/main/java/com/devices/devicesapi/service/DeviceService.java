package com.devices.devicesapi.service;

import com.devices.devicesapi.dto.DeviceDTO;
import com.devices.devicesapi.entity.Device;
import com.devices.devicesapi.entity.DeviceState;
import com.devices.devicesapi.mapper.DeviceMapper;
import com.devices.devicesapi.repository.DeviceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    public Device createDevice(DeviceDTO dto){
        return deviceRepository.save(DeviceMapper.toEntity(dto));
    }

    public Device updateDevice(Long id, DeviceDTO dto){
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + id));

        if (device.getState() == DeviceState.IN_USE) {
            if (!device.getName().equals(dto.getName()) || !device.getBrand().equals(dto.getBrand())) {
                throw new IllegalStateException("Cannot update name or brand while device is in use.");
            }
        }
        device.setName(dto.getName());
        device.setBrand(dto.getBrand());
        device.setState(dto.getState());
        return deviceRepository.save(device);
    }

    public DeviceDTO patchDevice(Long id, Map<String, Object> updates) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + id));

        //Update only the fields that came in the map
        updates.forEach((key, value) -> {
            switch (key) {
                case "name":
                    if (device.getState() == DeviceState.IN_USE) {
                        throw new IllegalStateException("Cannot update name while device is in use.");
                    }
                    device.setName((String) value);
                    break;
                case "brand":
                    if (device.getState() == DeviceState.IN_USE) {
                        throw new IllegalStateException("Cannot update brand while device is in use.");
                    }
                    device.setBrand((String) value);
                    break;
                case "state":
                    device.setState(DeviceState.valueOf((String) value));
                    break;
            }
        });
        Device saved = deviceRepository.save(device);
        return DeviceMapper.toDto(saved);
    }

    public void deleteDevice(Long id){
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + id));

        if (device.getState() == DeviceState.IN_USE) {
            throw new IllegalStateException("Cannot delete device while it's in use.");
        }

        deviceRepository.deleteById(id);
    }

    public DeviceDTO getDeviceById(Long id){
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Device not found with ID: " + id));
        return DeviceMapper.toDto(device);
    }

    public List<DeviceDTO> getAllDevices(){
        return deviceRepository.findAll()
                .stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

    public List<DeviceDTO> getDevicesByBrand(String brand){
        return deviceRepository.findByBrand(brand)
                .stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

    public List<DeviceDTO> getDevicesByState(String state){
        return deviceRepository.findByState(DeviceState.valueOf(state.toUpperCase()))
                .stream()
                .map(DeviceMapper::toDto)
                .toList();
    }

}
