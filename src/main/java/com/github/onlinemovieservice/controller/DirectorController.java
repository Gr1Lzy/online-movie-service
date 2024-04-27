package com.github.onlinemovieservice.controller;

import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;
import com.github.onlinemovieservice.service.DirectorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/director")
public class DirectorController {
    private final DirectorService directorService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public DirectorDto createDirector(@Validated @RequestBody DirectorSaveDto directorDto) {
        return directorService.save(directorDto);
    }
}
