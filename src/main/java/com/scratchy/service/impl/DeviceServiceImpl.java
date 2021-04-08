package com.scratchy.service.impl;

import com.scratchy.domain.Device;
import com.scratchy.repository.DeviceDao;
import com.scratchy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    @Transactional
    public Optional<Device> getDevice(String serialNumber) {
        return deviceDao.findById(serialNumber);
    }

    @Override
    @Transactional
    public Iterable<Device> getDeviceList() {
        return deviceDao.findAll();
    }

    @Override
    @Transactional
    public Optional<Device> createDevice(Device device) {
        Optional<Device> deviceOptional = deviceDao.findById(device.getId());
        if (deviceOptional.isPresent()) {
            return Optional.empty();
        }

        return Optional.of(deviceDao.save(device));
    }

    @Override
    @Transactional
    public Optional<Device> updateDevice(String serialNumber, Device device) {
        Optional<Device> oldDevice = deviceDao.findById(serialNumber);
        if (oldDevice.isEmpty()) {
            return Optional.empty();
        }

        return Optional.of(deviceDao.save(device));
    }

    @Override
    @Transactional
    public Optional<Device> deleteDevice(String serialNumber) {
        Optional<Device> device = deviceDao.findById(serialNumber);
        if (device.isPresent()) {
            deviceDao.delete(device.get());
            return device;
        }

        return device;
    }
}
