package com.github.onlinemovieservice.dto.movie;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.Set;

@Data
public class MovieSaveDto {
    @JsonProperty("title")
    @NotBlank(message = "Title is required or cannot be null")
    private String title;

    @JsonProperty("releaseDate")
    @NotNull(message = "Date is required or cannot be null")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate releaseDate;

    @JsonProperty("genresIds")
    @NotEmpty(message = "Genres are required or cannot be null")
    private Set<Long> genresIds;

    @JsonProperty("directorId")
    @NotNull(message = "Director is required or cannot be null")
    private Long directorId;
}
