package com.github.onlinemovieservice.dto.movie;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieSaveDto {
    @NotBlank(message = "Title is required")
    private String title;

    @NotNull(message = "Date is required")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @NotEmpty(message = "Genres are required")
    private Set<Long> genresIds;

    @NotNull(message = "Director is required or cannot be null")
    private Long directorId;
}
