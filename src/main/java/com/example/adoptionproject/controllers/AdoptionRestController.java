package com.example.adoptionproject.controllers;

import com.example.adoptionproject.dto.AdoptantDTO;
import com.example.adoptionproject.dto.AdoptionDTO;
import com.example.adoptionproject.dto.AnimalDTO;
import com.example.adoptionproject.entities.Adoptant;
import com.example.adoptionproject.entities.Adoption;
import com.example.adoptionproject.entities.Animal;
import com.example.adoptionproject.services.IAdoptionServices;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("")
public class AdoptionRestController {

    private final IAdoptionServices adoptionServices;

    @PostMapping("/addAdoptant")
    public Adoptant addAdoptant(@RequestBody AdoptantDTO dto) {
        Adoptant adoptant = new Adoptant();
        adoptant.setNom(dto.getNom());
        adoptant.setAdresse(dto.getAdresse());
        adoptant.setTelephone(dto.getTelephone());
        return adoptionServices.addAdoptant(adoptant);
    }

    @PostMapping("/addAnimal")
    public Animal addAnimal(@RequestBody AnimalDTO dto) {
        Animal animal = new Animal();
        animal.setNom(dto.getNom());
        animal.setAge(dto.getAge());
        animal.setSterilise(dto.isSterilise());
        animal.setEspece(dto.getEspece());
        return adoptionServices.addAnimal(animal);
    }

    @PostMapping("/addAdoption/{idAdoptant}/{idAnimal}")
    public Adoption addAdoption(@RequestBody AdoptionDTO dto,
                                @PathVariable int idAdoptant,
                                @PathVariable int idAnimal) {
        Adoption adoption = new Adoption();
        adoption.setDateAdoption(dto.getDateAdoption());
        adoption.setFrais(dto.getFrais());
        return adoptionServices.addAdoption(adoption, idAdoptant, idAnimal);
    }

    @GetMapping("/byAdoptant/{nom}")
    public List<Adoption> getAdoptionsByAdoptant(@PathVariable String nom) {
        return adoptionServices.getAdoptionsByAdoptant(nom);
    }

    @GetMapping("/totalFrais/{idAdoptant}")
    public float calculTotalFrais(@PathVariable int idAdoptant) {
        return adoptionServices.calculFraisTotalAdoptions(idAdoptant);
    }
}
