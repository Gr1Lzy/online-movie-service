package com.github.onlinemovieservice;

import com.github.onlinemovieservice.controller.GenreController;
import com.github.onlinemovieservice.dto.genre.GenreDto;
import com.github.onlinemovieservice.dto.genre.GenreSaveDto;
import com.github.onlinemovieservice.exception.EntityNotFoundException;
import com.github.onlinemovieservice.service.GenreService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@WebMvcTest(GenreController.class)
class GenreControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GenreService genreService;

    @Test
    void testCreateGenre() throws Exception {
        GenreSaveDto genreSaveDto = new GenreSaveDto();
        genreSaveDto.setName("Action");

        GenreDto createdGenreDto = new GenreDto();
        createdGenreDto.setId(1L);
        createdGenreDto.setName("Action");

        when(genreService.save(any(GenreSaveDto.class))).thenReturn(createdGenreDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Action\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Action"));
    }

    @Test
    void testDeleteGenre() throws Exception {
        Long genreId = 1L;

        doNothing().when(genreService).deleteById(genreId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/{id}", genreId)
                        . contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }

    @Test
    void testDeleteGenreWithNonExistentId() throws Exception {
        Long nonExistentGenreId = 100L;

        doThrow(new EntityNotFoundException("Genre not found")).when(genreService).deleteById(nonExistentGenreId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/genre/{id}", nonExistentGenreId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    void testCreateEmptyGenre() throws Exception {

        when(genreService.save(any(GenreSaveDto.class))).thenThrow(new IllegalArgumentException("Genre name cannot be empty"));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/genre")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest());
    }
}
