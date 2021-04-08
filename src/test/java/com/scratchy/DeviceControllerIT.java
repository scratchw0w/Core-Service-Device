package com.scratchy;

import com.scratchy.builders.DeviceBuilder;
import com.scratchy.domain.Device;
import com.scratchy.repository.DeviceDao;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT, classes = CoreApplication.class)
@ActiveProfiles("dev")
public class DeviceControllerIT {

    private static final int PORT = 8080;
    private static final String BASE_URL = "http://localhost:";
    private final Device testDevice = new DeviceBuilder()
            .id("Test")
            .model("Test")
            .description("Test")
            .build();

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private DeviceDao deviceDao;

    @BeforeEach
    public void insertNewDevice() {
        this.restTemplate
                .postForEntity(BASE_URL + PORT + "/api/devices",
                        getRequestForDevice(this.testDevice), Device.class);
    }

    @AfterEach
    public void deleteDevice() {
        this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/" + this.testDevice.getId(),
                        HttpMethod.DELETE, getRequestForDevice(this.testDevice), Device.class);
    }

    @Test
    public void shouldReturnOkStatusForGettingAllDevices() {
        ResponseEntity<Iterable<Device>> responseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices", HttpMethod.GET,
                        null, new ParameterizedTypeReference<Iterable<Device>>() {});

        Iterable<Device> expectedDeviceList = deviceDao.findAll();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDeviceList, responseEntity.getBody());
    }

    @Test
    public void shouldReturnOkStatusForDeviceById() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .getForEntity(BASE_URL + PORT + "/api/devices/" +
                        testDevice.getId(), Device.class);

        Device expectedDevice = new DeviceBuilder().id("Test").model("Test")
                .description("Test").build();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(expectedDevice, responseEntity.getBody());
    }
    
    @Test
    public void shouldReturn404StatusForGettingNonExistingDevice() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .getForEntity(BASE_URL + PORT + "/api/devices/Non Existing Device", Device.class);
        
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void shouldCreateNewDeviceAndReturnCreatedStatusCode() {
        Device newDevice = new DeviceBuilder()
                .id("1")
                .model("1")
                .description("1")
                .build();

        ResponseEntity<Device> responseEntity = this.restTemplate
                .postForEntity(BASE_URL + PORT + "/api/devices", getRequestForDevice(newDevice), Device.class);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertEquals(newDevice, responseEntity.getBody());

        this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/" + newDevice.getId(),
                        HttpMethod.DELETE, getRequestForDevice(newDevice), Device.class);
    }

    @Test
    public void shouldReturn409ForAddingExistingDeviceId() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices",
                        HttpMethod.POST, getRequestForDevice(this.testDevice), Device.class);

        assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnOkStatusForUpdatingExistingDevice() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .getForEntity(BASE_URL + PORT + "/api/devices/" + testDevice.getId(), Device.class);

        Device deviceToUpdate = responseEntity.getBody();
        deviceToUpdate.setModel("Updated");
        deviceToUpdate.setDescription("Updated");

        ResponseEntity<Device> updatedResponseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/" + deviceToUpdate.getId(),
                        HttpMethod.PUT, getRequestForDevice(deviceToUpdate), Device.class);

        assertEquals(HttpStatus.OK, updatedResponseEntity.getStatusCode());
        assertEquals(deviceToUpdate, updatedResponseEntity.getBody());
    }

    @Test
    public void shouldReturn404StatusForUpdatingNonExistingDevice() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/Non Existing Device",
                        HttpMethod.PUT, getRequestForDevice(this.testDevice), Device.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    @Test
    public void shouldReturnOkForDeletingExistingDevice() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/" + this.testDevice.getId(),
                        HttpMethod.DELETE, getRequestForDevice(this.testDevice), Device.class);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(this.testDevice, responseEntity.getBody());
    }

    @Test
    public void shouldReturn404ForDeletingNonExistingUser() {
        ResponseEntity<Device> responseEntity = this.restTemplate
                .exchange(BASE_URL + PORT + "/api/devices/Non Existing Device",
                        HttpMethod.DELETE, getRequestForDevice(this.testDevice), Device.class);

        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
    }

    private HttpEntity<Device> getRequestForDevice(Device device) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        return new HttpEntity<>(device, headers);
    }
}
