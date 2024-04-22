package com.github.onlinemovieservice.dto.director;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DirectorCreateDto {
    @NotBlank(message = "First name is required")
    private String firstName;

    @NotBlank(message = "Last name is required")
    private String lastName;
}
