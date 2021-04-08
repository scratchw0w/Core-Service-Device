package com.scratchy.builders;

import com.scratchy.domain.Device;

public class DeviceBuilder {

    private Device device = new Device();

    public DeviceBuilder id(String id) {
        device.setId(id);
        return this;
    }

    public DeviceBuilder model(String model) {
        device.setModel(model);
        return this;
    }

    public DeviceBuilder description(String description) {
        device.setDescription(description);
        return this;
    }

    public Device build() {
        return device;
    }
}
