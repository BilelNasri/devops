package com.example.adoptionproject.controllers;

import com.example.adoptionproject.dto.AdoptantDTO;
import com.example.adoptionproject.dto.AdoptionDTO;
import com.example.adoptionproject.dto.AnimalDTO;
import com.example.adoptionproject.entities.Adoptant;
import com.example.adoptionproject.entities.Adoption;
import com.example.adoptionproject.entities.Animal;
import com.example.adoptionproject.entities.Espece;
import com.example.adoptionproject.services.IAdoptionServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdoptionRestController.class)
class AdoptionRestControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAdoptionServices adoptionServices;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testAddAdoptant() throws Exception {
        AdoptantDTO dto = new AdoptantDTO("Sam", "Tunis", 12345678);
        Adoptant saved = new Adoptant();
        saved.setNom("Sam");

        when(adoptionServices.addAdoptant(any())).thenReturn(saved);

        mockMvc.perform(post("/addAdoptant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Sam"));
    }

    @Test
    void testAddAnimal() throws Exception {
        AnimalDTO dto = new AnimalDTO("Max", 2, true, Espece.CHAT);
        Animal saved = new Animal();
        saved.setNom("Max");

        when(adoptionServices.addAnimal(any())).thenReturn(saved);

        mockMvc.perform(post("/addAnimal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Max"));
    }

    @Test
    void testAddAdoption() throws Exception {
        AdoptionDTO dto = new AdoptionDTO(new Date(), 150f);
        Adoption saved = new Adoption();
        saved.setFrais(150f);

        when(adoptionServices.addAdoption(any(), eq(1), eq(2))).thenReturn(saved);

        mockMvc.perform(post("/addAdoption/1/2")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.frais").value(150));
    }

    @Test
    void testGetAdoptionsByAdoptant() throws Exception {
        List<Adoption> list = Arrays.asList(new Adoption(), new Adoption());
        when(adoptionServices.getAdoptionsByAdoptant("Sam")).thenReturn(list);

        mockMvc.perform(get("/byAdoptant/Sam"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    void testCalculFraisTotal() throws Exception {
        when(adoptionServices.calculFraisTotalAdoptions(1)).thenReturn(99.99f);

        mockMvc.perform(get("/totalFrais/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("99.99"));
    }
}
