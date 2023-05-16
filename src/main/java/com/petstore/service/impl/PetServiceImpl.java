package com.petstore.service.impl;

import com.petstore.dto.PetDTO;
import com.petstore.entity.Pet;
import com.petstore.entity.Tag;
import com.petstore.mapper.PetMapper;
import com.petstore.mapper.TagMapper;
import com.petstore.repository.CategoryRepository;
import com.petstore.repository.PetRepository;
import com.petstore.repository.TagRepository;
import com.petstore.service.PetService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.Set;

/**
 * This implementation of {@link PetService} provides methods for managing pets.
 */
@Service
@RequiredArgsConstructor
public class PetServiceImpl implements PetService {

    private final PetRepository petRepository;
    private final CategoryRepository categoryRepository;
    private final TagRepository tagRepository;
    private final PetMapper petMapper;
    private final TagMapper tagMapper;
    private final Path root = Paths.get("uploads");
    private static final String PET_NOT_FOUND = "Pet with id '%d' not found.";
    private static final String CATEGORY_NOT_FOUND = "Category with id '%d' not found.";
    private static final String TAG_NOT_FOUND = "Tag with id '%d' not found.";
    private static final String INVALID_VALUE = "Invalid status value";
    private static final String INVALID_INIT = "Could not initialize folder for upload.";
    private static final String FILE_EXIST = "A file with name '%s' already exists.";


    /**
     * {@inheritDoc}
     */
    @Transactional(readOnly = true)
    @Override
    public PetDTO findById(Long id) {
        return petMapper.toDTO(petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PET_NOT_FOUND, id))));
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
            throw new IllegalArgumentException(INVALID_VALUE);
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
                .orElseThrow(() -> new EntityNotFoundException(String.format(CATEGORY_NOT_FOUND, pet.getCategory().getId())));
        pet.setCategory(category);
        Set<Tag> tags = tagMapper.toSetEntities(petDTO.getTags());
        for (var t : tags) {
            var tag = tagRepository.findById(t.getId())
                    .orElseThrow(() -> new EntityNotFoundException(String.format(TAG_NOT_FOUND, t.getId())));
            tag.assignPet(pet);
        }

        return petMapper.toDTO(petRepository.save(pet));
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updatePet(PetDTO petDTO) {
        var pet = petRepository.findById(petDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(PET_NOT_FOUND, petDTO.getId())));
        var tags = tagMapper.toSetEntities(petDTO.getTags());
        for (var tag : tags) {
            tag.assignPet(pet);
        }
        petMapper.updateProperties(petDTO, pet);
        petRepository.save(pet);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void updatePetInTheStoreById(Long id, String name, String status) {
        Pet petForUpdate = petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PET_NOT_FOUND, id)));

        if (name != null) {
            petForUpdate.setName(name);
        }
        if (status != null) {
            petForUpdate.setStatus(Pet.Status.valueOf(status.toUpperCase()));
        }
        petRepository.save(petForUpdate);
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void deletePet(Long id) {
        petRepository.delete(petRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(PET_NOT_FOUND, id))));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void init() {
        try {
            Files.createDirectories(root);
        } catch (IOException e) {
            throw new RuntimeException(INVALID_INIT);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public void uploadImage(Long petId, MultipartFile image) {
        String url = image.getOriginalFilename();
        try {
            var pet = petRepository.findById(petId)
                    .orElseThrow(() -> new EntityNotFoundException(String.format(PET_NOT_FOUND, petId)));
            Files.copy(image.getInputStream(), this.root.resolve(Objects.requireNonNull(url)));
            pet.getPhotoUrls().add(url);
            petRepository.save(pet);
        } catch (Exception e) {
            if (e instanceof FileAlreadyExistsException) {
                throw new RuntimeException(String.format(FILE_EXIST, url));
            }
            throw new RuntimeException(e.getMessage());
        }
    }
}
