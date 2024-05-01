package com.github.onlinemovieservice.dto.director;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DirectorSaveDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;

    @NotBlank(message = "Nationality is required")
    private String nationality;
}
