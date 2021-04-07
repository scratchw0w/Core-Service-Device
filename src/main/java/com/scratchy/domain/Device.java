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
    private String id;

    @Pattern(regexp = "")
    private String model;

    @Pattern(regexp = "")
    private String description;

    public Device(String model, String description) {
        this.model = model;
        this.description = description;
    }
}
