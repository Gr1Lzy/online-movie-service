package com.github.onlinemovieservice.dto.director;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class DirectorSearchParametersWithPageable implements DirectorParameters {
    private String firstName;
    private String lastName;
    private String nationality;
    @NotBlank(message = "page required")
    private int page;
    @NotBlank(message = "size required")
    private int size;
}
