package com.petstore.service;

import static com.petstore.entity.Pet.Status.valueOf;
import static com.petstore.service.impl.PetServiceImpl.CATEGORY_NOT_FOUND;
import static com.petstore.service.impl.PetServiceImpl.INVALID_STATUS_VALUE;
import static com.petstore.testdatafactory.PetTestFactory.TEST_PETS;
import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTO;
import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTOS;
import static com.petstore.testdatafactory.PetTestFactory.TEST_PET_DTO_UPDATED;
import static com.petstore.testdatafactory.PetTestFactory.test_pet;
import static java.lang.String.format;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.petstore.dto.PetDto;
import com.petstore.entity.Pet;
import com.petstore.exception.InvalidStatusException;
import com.petstore.mapper.PetMapper;
import com.petstore.mapper.TagMapper;
import com.petstore.repository.CategoryRepository;
import com.petstore.repository.PetRepository;
import com.petstore.service.impl.PetServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class PetServiceImplTest {
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
    public void findById_ReturnPetDto() {
        when(petRepository.findById(1L)).thenReturn(Optional.of(test_pet));
        when(petMapper.toDto(any(Pet.class))).thenReturn(TEST_PET_DTO);

        PetDto returnedPetDto = petService.findById(1L);

        verify(petRepository).findById(1L);
        assertNotNull(returnedPetDto);
        assertEquals(TEST_PET_DTO, returnedPetDto);
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
    public void findPetsByStatus_ReturnListPetDtos() {
        when(petRepository.findAllByStatus(valueOf("PENDING"))).thenReturn(TEST_PETS);
        when(petMapper.toListDtos(TEST_PETS)).thenReturn(TEST_PET_DTOS);

        List<PetDto> petsByStatus = petService.findPetsByStatus("PENDING");

        assertNotNull(petsByStatus);
        assertEquals(TEST_PET_DTOS, petsByStatus);
        verify(petRepository).findAllByStatus(valueOf("PENDING"));
    }

    @Test
    public void findPetsByStatus_ThrowInvalidStatusException() {
        InvalidStatusException exception = assertThrows(InvalidStatusException.class,
                () -> petService.findPetsByStatus("none"));

        assertEquals(INVALID_STATUS_VALUE, exception.getMessage());
    }

    @Test
    public void addPet_ThenReturnPetDto() {
        when(petMapper.toEntity(TEST_PET_DTO)).thenReturn(test_pet);
        when(categoryRepository.findById(anyLong())).thenReturn(Optional.of(test_pet.getCategory()));
        when(petRepository.save(test_pet)).thenReturn(test_pet);
        when(petMapper.toDto(test_pet)).thenReturn(TEST_PET_DTO);

        PetDto addedPetDto = petService.addPet(TEST_PET_DTO);

        verify(petRepository).save(test_pet);
        assertNotNull(addedPetDto);
        assertEquals(TEST_PET_DTO, addedPetDto);
    }

    @Test
    public void addPet_WhenInvalidCategory_ThenThrowEntityNotFoundException() {
        when(petMapper.toEntity(TEST_PET_DTO)).thenReturn(test_pet);
        when(categoryRepository.findById(1L)).thenThrow(new EntityNotFoundException(format(CATEGORY_NOT_FOUND, 1L)));

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class,
                () -> petService.addPet(TEST_PET_DTO));

        assertEquals(format(CATEGORY_NOT_FOUND, 1L), exception.getMessage());
        verify(tagMapper, times(0)).toSetEntities(anySet());
    }

    @Test
    public void updatePet_ReturnPetDto() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(test_pet));
        when(petMapper.toDto(test_pet)).thenReturn(TEST_PET_DTO);

        PetDto updatedPet = petService.updatePet(TEST_PET_DTO);
        verify(petMapper).updateProperties(TEST_PET_DTO, test_pet);
        verify(petMapper).toDto(test_pet);
        assertEquals(TEST_PET_DTO, updatedPet);
    }

    @Test
    public void updatePetInTheStoreById_ReturnPetDto() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(test_pet));
        when(petMapper.toDto(test_pet)).thenReturn(TEST_PET_DTO_UPDATED);

        String updatedName = "Alisa";
        String updatedStatus = "PENDING";

        PetDto updated = petService.updatePetInTheStoreById(1L, updatedName, updatedStatus);

        verify(petMapper).toDto(test_pet);
        assertEquals(updatedName, updated.getName());
        assertEquals(Pet.Status.PENDING, updated.getStatus());
    }

    @Test
    public void deletePet_ReturnVoid() {
        when(petRepository.findById(anyLong())).thenReturn(Optional.of(test_pet));
        doNothing().when(petRepository).delete(test_pet);

        assertAll(() -> petService.deletePet(1L));
        verify(petRepository).delete(test_pet);
    }
}