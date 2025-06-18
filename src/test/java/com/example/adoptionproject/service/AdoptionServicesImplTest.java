package com.example.adoptionproject.service;

import com.example.adoptionproject.entities.*;
import com.example.adoptionproject.repositories.*;
import com.example.adoptionproject.services.AdoptionServicesImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AdoptionServicesImplTest {

    @Mock
    private AdoptionRepository adoptionRepository;

    @Mock
    private AdoptantRepository adoptantRepository;

    @Mock
    private AnimalRepository animalRepository;

    @InjectMocks
    private AdoptionServicesImpl adoptionService;

    @Test
    public void testCalculFraisTotalAdoptions() {
        Adoption a1 = new Adoption(); a1.setFrais(100.0f);
        Adoption a2 = new Adoption(); a2.setFrais(150.0f);
        when(adoptionRepository.findByAdoptant_IdAdoptant(1)).thenReturn(Arrays.asList(a1, a2));

        float total = adoptionService.calculFraisTotalAdoptions(1);

        assertEquals(250.0f, total);
    }

    @Test
    public void testAddAdoptant() {
        Adoptant adoptant = new Adoptant(); adoptant.setNom("Ali");
        when(adoptantRepository.save(adoptant)).thenReturn(adoptant);

        Adoptant result = adoptionService.addAdoptant(adoptant);

        assertNotNull(result);
        assertEquals("Ali", result.getNom());
    }

    @Test
    public void testAddAdoption() {
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
}
