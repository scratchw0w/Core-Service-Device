package com.scratchy.controller;

import com.scratchy.domain.Device;
import com.scratchy.service.DeviceService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Slf4j
@org.springframework.web.bind.annotation.RestController
@RequestMapping("/api")
public class RestController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public ResponseEntity<String> status() {
        log.info("Checking status of the application");
        return ResponseEntity.ok("All is working!");
    }

    @GetMapping("/devices")
    public ResponseEntity<Iterable<Device>> getDevices() {
        log.info("Getting all devices");
        return ResponseEntity.ok(deviceService.getDeviceList());
    }

    @GetMapping(value = "/devices/{id}", produces = "application/json")
    @ApiResponse(responseCode = "201", description = "Order is created!",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Device.class))})
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") String id) {
        log.info("Getting device by id: " + id);
        Optional<Device> device = deviceService.getDevice(id);
        return device.map(ResponseEntity::ok)
                .orElseGet(() ->
                        ResponseEntity.status(HttpStatus.NOT_FOUND).body(device.get()));
    }

    @PostMapping(path = "/devices", consumes = "application/json")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        log.info("Creating new Device with id: " + device.getId());
        deviceService.createDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }
}
