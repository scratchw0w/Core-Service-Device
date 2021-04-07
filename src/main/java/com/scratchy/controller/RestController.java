package com.scratchy.controller;

import com.scratchy.domain.Device;
import com.scratchy.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@org.springframework.web.bind.annotation.RestController
public class RestController {

    @Autowired
    private DeviceService deviceService;

    @GetMapping()
    public ResponseEntity<String> status() {
        return ResponseEntity.ok("All is working!");
    }

    @GetMapping("/devices")
    public ResponseEntity<Iterable<Device>> getDevices() {
        return ResponseEntity.ok(deviceService.getDeviceList());
    }
}
