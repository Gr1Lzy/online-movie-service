package com.github.onlinemovieservice.dto.director;

import lombok.Data;

@Data
public class DirectorSearchParameters implements DirectorParameters {
    private String firstName;
    private String lastName;
    private String nationality;
}
