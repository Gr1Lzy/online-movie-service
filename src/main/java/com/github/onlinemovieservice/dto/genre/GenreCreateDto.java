package com.github.onlinemovieservice.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreCreateDto {
    @NotBlank(message = "Genre name is required")
    private String name;
}
