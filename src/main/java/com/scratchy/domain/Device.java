package com.scratchy.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Document
@NoArgsConstructor
public class Device {

    @Id
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-\\s]*$", message = "Serial number is incorrect")
    private String id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Model is incorrect")
    private String model;

    private String description;

    public Device(String serialNumber, String model, String description) {
        this.id = serialNumber;
        this.model = model;
        this.description = description;
    }
}
