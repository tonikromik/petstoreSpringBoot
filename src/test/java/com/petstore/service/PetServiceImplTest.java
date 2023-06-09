package com.petstore.service;

import com.petstore.dto.PetDTO;
import com.petstore.entity.Pet;
import com.petstore.exception.InvalidStatusException;
import com.petstore.mapper.PetMapper;
import com.petstore.mapper.TagMapper;
import com.petstore.repository.CategoryRepository;
import com.petstore.repository.PetRepository;
import com.petstore.service.impl.PetServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static com.petstore.entity.Pet.Status.valueOf;
import static com.petstore.service.PetServiceTestFactory.*;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {
    private static final String INVALID_STATUS_VALUE = "Invalid status value";
    private static final String CATEGORY_NOT_FOUND = "Category with id '%d' not found.";
    @Mock
    private PetMapper petMapper;
    @Mock
    private TagMapper tagMapper;
    @Mock
    private PetRepository petRepository;
    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private PetServiceImpl petService;

    @Test
    public void findById_ReturnPetDTO() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(pet));
        when(petMapper.toDTO(any(Pet.class))).thenReturn(PET_DTO);

        PetDTO returnedPetDTO = petService.findById(1L);

        verify(petRepository).findById(1L);
        assertNotNull(returnedPetDTO);
        assertEquals(PET_DTO, returnedPetDTO);
    }

    @Test
    public void findById_ThrowEntityNotFoundException() {
        when(petRepository.findById(1L)).thenThrow(
                new EntityNotFoundException("Pet with id '1L' not found."));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> petService.findById(1L));

        verify(petRepository).findById(1L);
        assertEquals("Pet with id '1L' not found.", exception.getMessage());
    }

    @Test
    public void findPetsByStatus_ReturnListPetDTOs() {
        when(petRepository.findAllByStatus(valueOf("PENDING"))).thenReturn(PETS);
        when(petMapper.toListDTOs(PETS)).thenReturn(PET_DTOS);

        List<PetDTO> petsByStatus = petService.findPetsByStatus("PENDING");

        assertNotNull(petsByStatus);
        assertEquals(PET_DTOS, petsByStatus);
        verify(petRepository).findAllByStatus(valueOf("PENDING"));
    }

    @Test
    public void findPetsByStatus_ThrowInvalidStatusException() {
        InvalidStatusException exception = assertThrows(InvalidStatusException.class,
                () -> petService.findPetsByStatus("none"));

        assertEquals(INVALID_STATUS_VALUE, exception.getMessage());
    }

    @Test
    public void addPet_ThenReturnPetDTO() {
        when(petMapper.toEntity(PET_DTO)).thenReturn(pet);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(pet.getCategory()));
        when(petRepository.save(pet)).thenReturn(pet);
        when(petMapper.toDTO(pet)).thenReturn(PET_DTO);

        PetDTO addedPetDTO = petService.addPet(PET_DTO);

        verify(petRepository).save(pet);
        assertNotNull(addedPetDTO);
        assertEquals(PET_DTO, addedPetDTO);
    }

    @Test
    public void addPet_WhenInvalidCategory_ThenThrowEntityNotFoundException() {
        when(petMapper.toEntity(PET_DTO)).thenReturn(pet);
        when(categoryRepository.findById(1L)).thenThrow(new EntityNotFoundException(format(CATEGORY_NOT_FOUND, 1L)));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> petService.addPet(PET_DTO));

        assertEquals(format(CATEGORY_NOT_FOUND, 1L), exception.getMessage());
        verify(tagMapper, times(0)).toSetEntities(anySet());
    }

    @Test
    public void updatePet_ReturnPetDTO() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        when(petMapper.toDTO(pet)).thenReturn(PET_DTO);

        PetDTO updatedPet = petService.updatePet(PET_DTO);
        verify(petMapper).updateProperties(PET_DTO, pet);
        verify(petMapper).toDTO(pet);
        assertEquals(PET_DTO, updatedPet);
    }

    @Test
    public void updatePetInTheStoreById_ReturnPetDTO() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        when(petMapper.toDTO(pet)).thenReturn(PET_DTO_UPDATED);

        String updatedName = "Alisa";
        String updatedStatus = "PENDING";

        PetDTO updated = petService.updatePetInTheStoreById(1L, updatedName, updatedStatus);

        verify(petMapper).toDTO(pet);
        assertEquals(updatedName, updated.getName());
        assertEquals(Pet.Status.PENDING, updated.getStatus());
    }

    @Test
    public void deletePet_ReturnVoid() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(pet));
        doNothing().when(petRepository).delete(pet);

        assertAll(() -> petService.deletePet(1L));
        verify(petRepository).delete(pet);
    }
}