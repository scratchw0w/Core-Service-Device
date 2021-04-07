package com.scratchy.service;

import com.scratchy.domain.Device;

import java.util.Optional;

public interface DeviceService {

    Optional<Device> getDevice(String serialNumber);

    Iterable<Device> getDeviceList();

    void createDevice(Device device);

    void updateDevice(Device device);

    Optional<Device> deleteDevice(String serialNumber);
}
