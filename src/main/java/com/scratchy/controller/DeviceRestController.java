package com.scratchy.controller;

import com.scratchy.domain.Device;
import com.scratchy.errors.DeviceNotFoundException;
import com.scratchy.service.DeviceService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
public class DeviceRestController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public ResponseEntity<String> welcome() {
        log.info("On main page of the api");
        return ResponseEntity.ok("Welcome to the Core Service!");
    }

    @Operation(summary = "Check status of the api")
    @ApiResponse(responseCode = "200", description = "Service is currently working")
    @GetMapping("/status")
    public ResponseEntity<String> status() {
        log.info("Checking status of the application");
        return ResponseEntity.ok("All is working!");
    }

    @Operation(summary = "Get list of devices")
    @ApiResponse(responseCode = "200", description = "Found list of devices",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Device.class))})
    @GetMapping("/devices")
    public ResponseEntity<Iterable<Device>> getDevices() {
        log.info("Getting all devices");
        return ResponseEntity.ok(deviceService.getDeviceList());
    }

    @Operation(summary = "Get device by id")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found the device",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Device was not found")})
    @GetMapping(value = "/devices/{id}", produces = "application/json")
    public ResponseEntity<Device> getDeviceById(@PathVariable("id") String id) {
        log.info("Getting device by id: " + id);
        Optional<Device> device = deviceService.getDevice(id);
        return device.map(ResponseEntity::ok)
                .orElseGet(() -> {
                    log.warn("There is no device with id: " + id);
                    throw new DeviceNotFoundException("There is no device with id: " + id);
                });
    }

    @Operation(summary = "Create new device")
    @ApiResponse(responseCode = "201", description = "Device is created!",
            content = {@Content(mediaType = "application/json",
                    schema = @Schema(implementation = Device.class))})
    @PostMapping(path = "/devices", consumes = "application/json")
    public ResponseEntity<Device> createDevice(@Valid @RequestBody Device device) {
        log.info("Creating new Device with id: " + device.getId());
        deviceService.createDevice(device);
        return ResponseEntity.status(HttpStatus.CREATED).body(device);
    }

    @Operation(summary = "Update an existing device")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Device was updated",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Device was not found")})
    @PutMapping(value = "/devices/{id}",
            consumes = "application/json", produces = "application/json")
    public ResponseEntity<Device> updateDevice(@PathVariable("id") String id,
                                               @Valid @RequestBody Device device) {
        log.info("Updating device with id: " + id);
        Optional<Device> updatedDevice = deviceService.updateDevice(id, device);
        return updatedDevice.map(ResponseEntity::ok).orElseGet(() -> {
                    log.warn("There is no device with id: " + id);
                    throw new DeviceNotFoundException("There is no device with id: " +id);
        });
    }

    @Operation(summary = "Delete an existing device")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Device was deleted",
            content = {@Content(mediaType = "application/json", schema = @Schema(implementation = Device.class))}),
            @ApiResponse(responseCode = "404", description = "Device was not found")})
    @DeleteMapping(value = "devices/{id}", consumes = "application/json")
    public ResponseEntity<Device> deleteDevice(@PathVariable("id") String id) {
        log.info("Deleting device with id: " + id);
        Optional<Device> device = deviceService.deleteDevice(id);
        return device.map(ResponseEntity::ok).orElseGet(() -> {
            log.warn("There is no device with id: " + id);
            throw new DeviceNotFoundException("There is no device with id: " + id);
        });
    }
}
