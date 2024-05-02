package com.github.onlinemovieservice;

import com.github.onlinemovieservice.controller.MovieController;
import com.github.onlinemovieservice.dto.movie.MovieDto;
import com.github.onlinemovieservice.dto.movie.MovieSaveDto;
import com.github.onlinemovieservice.model.Director;
import com.github.onlinemovieservice.service.MovieService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.Set;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MovieController.class)
class MovieControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MovieService movieService;

    @Test
    void testCreateMovieWithValidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Inception\", \"releaseDate\":\"2010-07-16\", \"genresIds\":[1, 2], \"directorId\":1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    void testCreateMovieWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/movie")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"\", \"releaseDate\":\"2010-07-16\", \"genresIds\":[1, 2], \"directorId\":1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("Title is required or cannot be null. "));
    }

    @Test
    void testUploadMovie() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "test data".getBytes());

        mockMvc.perform(MockMvcRequestBuilders.multipart("/api/movie/upload")
                        .file(file))
                .andExpect(status().isOk());
    }

    @Test
    void testGetMovie() throws Exception {
        Long movieId = 1L;

        Director director = new Director();
        director.setFirstName("Christopher");
        director.setLastName("Nolan");

        MovieDto movieDto = new MovieDto();
        movieDto.setId(movieId);
        movieDto.setTitle("Inception");
        movieDto.setDirector(director);
        movieDto.setReleaseDate(LocalDate.of(2010, 7, 16));

        when(movieService.findById(movieId)).thenReturn(movieDto);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/movie/{id}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(movieId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Inception"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director.firstName").value("Christopher"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.director.lastName").value("Nolan"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.releaseDate").value("2010-07-16"));
    }

    @Test
    void testDeleteMovie() throws Exception {
        Long movieId = 1L;

        doNothing().when(movieService).deleteById(movieId);

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/movie/{id}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdateMovie() throws Exception {
        Long movieId = 1L;

        Director director = new Director();
        director.setFirstName("Christopher");
        director.setLastName("Nolan");

        MovieSaveDto movieSaveDto = new MovieSaveDto();
        movieSaveDto.setTitle("Inception");
        movieSaveDto.setDirectorId(1L);
        movieSaveDto.setReleaseDate(LocalDate.of(2011, 7, 16));
        movieSaveDto.setGenresIds(Set.of(1L, 2L));

        mockMvc.perform(MockMvcRequestBuilders.post("/api/movie/{id}", movieId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"Inception\", \"releaseDate\":\"2011-07-16\", \"genresIds\":[1, 2], \"directorId\":1}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());
    }
}


