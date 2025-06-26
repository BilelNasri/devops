package com.example.adoptionproject.controllers;

import com.example.adoptionproject.entities.*;
import com.example.adoptionproject.services.IAdoptionServices;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

import static org.mockito.Mockito.*;
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
        Adoptant a = new Adoptant();
        a.setNom("Sam");

        when(adoptionServices.addAdoptant(any())).thenReturn(a);

        mockMvc.perform(post("/addAdoptant")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(a)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.nom").value("Sam"));
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
