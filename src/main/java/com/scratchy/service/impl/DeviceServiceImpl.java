package com.scratchy.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.scratchy.domain.Device;
import com.scratchy.repository.DeviceRepository;
import com.scratchy.service.DeviceService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@Log4j2
@EnableKafka
public class DeviceServiceImpl implements DeviceService {

    private static final String TOPIC_NAME = "device-topic";

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

    @KafkaListener(topics = TOPIC_NAME)
    void deviceListSaver(String data) {
        log.info("Getting data from topic: " + TOPIC_NAME);
        List<Device> deviceList;
        ObjectMapper mapper = new ObjectMapper();
        log.info("Deserializing data");
        try {
            deviceList = List.of(mapper.readValue(data, Device[].class));
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }

        log.info("Saving data to the database");
        deviceList.forEach(this::createDevice);
    }
}
