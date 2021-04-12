package com.scratchy.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@Document
@NoArgsConstructor
@AllArgsConstructor
public class Device {

    @Id
    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9-\\s]*$", message = "Serial Number is incorrect")
    private String id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9]*$", message = "Model is incorrect")
    private String model;

    private String description;
}
