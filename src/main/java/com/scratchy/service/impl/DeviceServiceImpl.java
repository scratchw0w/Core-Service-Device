package com.scratchy.service.impl;

import com.scratchy.domain.Device;
import com.scratchy.repository.DeviceRepository;
import com.scratchy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceRepository deviceRepository;

    @Override
    @Transactional
    public Optional<Device> getDevice(String serialNumber) {
        return deviceRepository.findById(serialNumber);
    }

    @Override
    @Transactional
    public Iterable<Device> getDeviceList() {
        return deviceRepository.findAll();
    }

    @Override
    @Transactional
    public Optional<Device> createDevice(Device device) {
        Optional<Device> deviceOptional = deviceRepository.findById(device.getId());
        if (deviceOptional.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public Optional<Device> updateDevice(String serialNumber, Device device) {
        Optional<Device> oldDevice = deviceRepository.findById(serialNumber);
        if (oldDevice.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(deviceRepository.save(device));
    }

    @Override
    @Transactional
    public Optional<Device> deleteDevice(String serialNumber) {
        Optional<Device> device = deviceRepository.findById(serialNumber);
        if (device.isPresent()) {
            deviceRepository.delete(device.get());
            return device;
        }

        return device;
    }
}
