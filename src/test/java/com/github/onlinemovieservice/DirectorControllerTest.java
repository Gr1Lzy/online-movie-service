package com.github.onlinemovieservice;

import com.github.onlinemovieservice.controller.DirectorController;
import com.github.onlinemovieservice.dto.director.DirectorDto;
import com.github.onlinemovieservice.dto.director.DirectorSaveDto;
import com.github.onlinemovieservice.service.DirectorService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@WebMvcTest(DirectorController.class)
class DirectorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DirectorService directorService;

    @Test
    void testCreateDirector() throws Exception {
        DirectorSaveDto directorSaveDto = new DirectorSaveDto();
        directorSaveDto.setFirstName("Christopher");
        directorSaveDto.setLastName("Nolan");

        DirectorDto createdDirectorDto = new DirectorDto();
        createdDirectorDto.setId(1L);
        createdDirectorDto.setFirstName("Christopher");
        createdDirectorDto.setLastName("Nolan");

        when(directorService.save(any(DirectorSaveDto.class))).thenReturn(createdDirectorDto);

        mockMvc.perform(MockMvcRequestBuilders.post("/api/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Christopher\", \"lastName\":\"Nolan\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists())
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Christopher"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Nolan"));
    }

    @Test
    void testUpdateDirector() throws Exception {
        Long directorId = 1L;
        DirectorSaveDto directorSaveDto = new DirectorSaveDto();
        directorSaveDto.setFirstName("Quentin");
        directorSaveDto.setLastName("Tarantino");

        DirectorDto updatedDirectorDto = new DirectorDto();
        updatedDirectorDto.setId(directorId);
        updatedDirectorDto.setFirstName("Quentin");
        updatedDirectorDto.setLastName("Tarantino");

        when(directorService.update(eq(directorId), any(DirectorSaveDto.class))).thenReturn(updatedDirectorDto);

        mockMvc.perform(MockMvcRequestBuilders.put("/api/director/{id}", directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"Quentin\", \"lastName\":\"Tarantino\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(directorId))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName").value("Quentin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName").value("Tarantino"));
    }

    @Test
    void testDeleteDirector() throws Exception {
        Long directorId = 1L;

        mockMvc.perform(MockMvcRequestBuilders.delete("/api/director/{id}", directorId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());

        verify(directorService).deleteById(directorId);
    }

    @Test
    void testGetAllDirectors() throws Exception {
        List<DirectorDto> directorDtoList = new ArrayList<>();
        DirectorDto director1 = new DirectorDto();
        director1.setId(1L);
        director1.setFirstName("Quentin");
        director1.setLastName("Tarantino");
        directorDtoList.add(director1);

        DirectorDto director2 = new DirectorDto();
        director2.setId(2L);
        director2.setFirstName("Christopher");
        director2.setLastName("Nolan");
        directorDtoList.add(director2);

        when(directorService.findAll()).thenReturn(directorDtoList);

        mockMvc.perform(MockMvcRequestBuilders.get("/api/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.hasSize(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id").value(1L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName").value("Quentin"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].lastName").value("Tarantino"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id").value(2L))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName").value("Christopher"))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].lastName").value("Nolan"));
    }

    @Test
    void testCreateDirectorWithInvalidInput() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/director")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"firstName\":\"\", \"lastName\":\"Nolan\", \"nationality\":\"American\"}")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("First name is required. "));
    }
}

