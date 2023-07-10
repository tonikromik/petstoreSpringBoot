package com.petstore.service.impl;

import com.petstore.dto.PetDTO;
import com.petstore.entity.Pet;
import com.petstore.exception.InvalidStatusException;
import com.petstore.mapper.PetMapper;
import com.petstore.repository.CategoryRepository;
import com.petstore.repository.PetRepository;
import com.petstore.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

import static java.lang.String.format;

/**
 * This implementation of {@link PetService} provides methods for managing pets.
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final PetMapper petMapper;
    public static final String INVALID_STATUS_VALUE = "Invalid status value";
    public static final String PET_NOT_FOUND = "Pet with id '%d' not found.";
    public static final String CATEGORY_NOT_FOUND = "Category with id '%d' not found.";
    public static final String FILE_EXIST = "A file with name '%s' already exists.";
    private static final String PET_ADDED = "Pet with id '%d' added.";
    private static final String PET_UPDATED = "Pet with id '%d' updated.";
    private static final String PET_DELETED = "Pet with id '%d' deleted.";
    private static final String IMAGE_UPLOADED = "Image for pet with id '%d' uploaded.";

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public PetDTO findById(Long id) {
        return petMapper.toDTO(petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(PET_NOT_FOUND, id))));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public List<PetDTO> findPetsByStatus(String status) {
        try {
            return petMapper.toListDTOs(petRepository.findAllByStatus(Pet.Status.valueOf(status.toUpperCase())));
        } catch (IllegalArgumentException e) {
            log.error(INVALID_STATUS_VALUE);
            throw new InvalidStatusException(INVALID_STATUS_VALUE);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public PetDTO addPet(PetDTO petDTO) {
        var pet = petMapper.toEntity(petDTO);
        var category = categoryRepository.findById(pet.getCategory().getId())
                .orElseThrow(() -> new EntityNotFoundException(format(CATEGORY_NOT_FOUND, pet.getCategory().getId())));
        pet.setCategory(category);
        Pet saved = petRepository.save(pet);
        log.info(format(PET_ADDED, saved.getId()));
        return petMapper.toDTO(saved);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public PetDTO updatePet(PetDTO petDTO) {
        var pet = petRepository.findById(petDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(format(PET_NOT_FOUND, petDTO.getId())));
        petMapper.updateProperties(petDTO, pet);
        log.info(format(PET_UPDATED, pet.getId()));
        return petMapper.toDTO(pet);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public PetDTO updatePetInTheStoreById(Long id, String name, String status) {
        Pet petForUpdate = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(PET_NOT_FOUND, id)));
        if (name != null) {
            petForUpdate.setName(name);
        }
        if (status != null) {
            petForUpdate.setStatus(Pet.Status.valueOf(status.toUpperCase()));
        }
        log.info(format(PET_UPDATED, petForUpdate.getId()));
        return petMapper.toDTO(petForUpdate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deletePet(Long id) {
        petRepository.delete(petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(format(PET_NOT_FOUND, id))));
        log.info(format(PET_DELETED, id));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void uploadImage(Long petId, MultipartFile image) {
        String url = image.getOriginalFilename();
        Path path = Paths.get("uploads");
        try {
            var pet = petRepository.findById(petId)
                    .orElseThrow(() -> new EntityNotFoundException(format(PET_NOT_FOUND, petId)));
            Files.copy(image.getInputStream(), path.resolve(Objects.requireNonNull(url)));
            pet.getPhotoUrls().add(url);
            log.info(format(IMAGE_UPLOADED, petId));
        } catch (FileAlreadyExistsException e) {
            log.error(format(FILE_EXIST, url));
            throw new RuntimeException(format(FILE_EXIST, url));
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new RuntimeException(e.getMessage());
        }
    }
}