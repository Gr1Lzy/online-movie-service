package com.github.onlinemovieservice.dto.genre;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class GenreSaveDto {
    @NotBlank(message = "Genre name is required")
    private String name;
}
