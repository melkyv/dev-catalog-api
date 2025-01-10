package com.mkv.devcatalog.service;

import com.mkv.devcatalog.domain.category.Category;
import com.mkv.devcatalog.domain.category.CategoryDTO;
import com.mkv.devcatalog.domain.category.CategoryRepository;
import com.mkv.devcatalog.infra.exception.IntegrityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Transactional(readOnly = true)
    public Page<CategoryDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(CategoryDTO::new);
    }

    @Transactional(readOnly = true)
    public CategoryDTO findById(Long id) {
        return new CategoryDTO(repository.getReferenceById(id));
    }

    @Transactional
    public CategoryDTO insert(CategoryDTO dto) {
        Category category = new Category();
        copyDtoToEntity(dto, category);
        repository.save(category);

        return new CategoryDTO(category);
    }

    @Transactional
    public CategoryDTO update(Long id, CategoryDTO dto) {
        Category category = repository.getReferenceById(id);
        copyDtoToEntity(dto, category);

        return new CategoryDTO(category);
    }

    @Transactional
    public void delete(Long id) {
        try {
            repository.deleteById(id);
        }
        catch (DataIntegrityViolationException e) {
            throw new IntegrityError("Integrity violation");
        }
    }

    private void copyDtoToEntity(CategoryDTO dto, Category entity) {
        if (entity.getName() != null) {
            entity.setUpdatedAt(Instant.now());
        }
        entity.setName(dto.name());
    }
}
