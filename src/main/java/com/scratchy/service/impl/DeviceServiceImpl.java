package com.scratchy.service.impl;

import com.scratchy.domain.Device;
import com.scratchy.repository.DeviceDao;
import com.scratchy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DeviceServiceImpl implements DeviceService {

    @Autowired
    private DeviceDao deviceDao;

    @Override
    public Optional<Device> getDevice(String serialNumber) {
        return deviceDao.findById(serialNumber);
    }

    @Override
    public Iterable<Device> getDeviceList() {
        return deviceDao.findAll();
    }

    @Override
    public void createDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    public void updateDevice(Device device) {
        deviceDao.save(device);
    }

    @Override
    public Optional<Device> deleteDevice(String serialNumber) {
        Optional<Device> device = deviceDao.findById(serialNumber);
        device.ifPresent(deviceToDelete -> deviceDao.delete(deviceToDelete));
        return device;
    }
}
