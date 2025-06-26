package com.example.adoptionproject.services;

import com.example.adoptionproject.entities.*;
import com.example.adoptionproject.repositories.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AdoptionServicesImplTest {

    @InjectMocks
    private AdoptionServicesImpl adoptionService;

    @Mock
    private AdoptantRepository adoptantRepository;

    @Mock
    private AnimalRepository animalRepository;

    @Mock
    private AdoptionRepository adoptionRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testAddAdoptant() {
        Adoptant a = new Adoptant();
        a.setNom("Alice");

        when(adoptantRepository.save(a)).thenReturn(a);

        Adoptant result = adoptionService.addAdoptant(a);

        assertEquals("Alice", result.getNom());
        verify(adoptantRepository).save(a);
    }

    @Test
    void testAddAnimal() {
        Animal animal = new Animal();
        animal.setNom("Rex");

        when(animalRepository.save(animal)).thenReturn(animal);

        Animal result = adoptionService.addAnimal(animal);

        assertEquals("Rex", result.getNom());
        verify(animalRepository).save(animal);
    }

    @Test
    void testAddAdoptionSuccess() {
        Adoption adoption = new Adoption();
        Adoptant adoptant = new Adoptant();
        Animal animal = new Animal();

        when(adoptantRepository.findById(1)).thenReturn(Optional.of(adoptant));
        when(animalRepository.findById(2)).thenReturn(Optional.of(animal));
        when(adoptionRepository.save(any(Adoption.class))).thenReturn(adoption);

        Adoption result = adoptionService.addAdoption(adoption, 1, 2);

        assertNotNull(result);
        verify(adoptionRepository).save(adoption);
    }

    @Test
    void testGetAdoptionsByAdoptant() {
        List<Adoption> list = Arrays.asList(new Adoption(), new Adoption());
        when(adoptionRepository.findByAdoptant_Nom("Alice")).thenReturn(list);

        List<Adoption> result = adoptionService.getAdoptionsByAdoptant("Alice");

        assertEquals(2, result.size());
        verify(adoptionRepository).findByAdoptant_Nom("Alice");
    }

    @Test
    void testCalculFraisTotalAdoptions() {
        Adoption a1 = new Adoption(); a1.setFrais(10f);
        Adoption a2 = new Adoption(); a2.setFrais(15f);

        when(adoptionRepository.findByAdoptant_IdAdoptant(1)).thenReturn(Arrays.asList(a1, a2));

        float total = adoptionService.calculFraisTotalAdoptions(1);

        assertEquals(25f, total);
    }
}
