package com.scratchy.service;

import com.scratchy.domain.Device;

import java.util.Optional;

public interface DeviceService {

    Optional<Device> getDevice(String serialNumber);

    Iterable<Device> getDeviceList();

    Optional<Device> createDevice(Device device);

    Optional<Device> updateDevice(String serialNumber, Device device);

    Optional<Device> deleteDevice(String serialNumber);
}
