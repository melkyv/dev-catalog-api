package com.mkv.devcatalog.service;

import com.mkv.devcatalog.domain.category.Category;
import com.mkv.devcatalog.domain.category.CategoryDTO;
import com.mkv.devcatalog.domain.category.CategoryRepository;
import com.mkv.devcatalog.domain.product.Product;
import com.mkv.devcatalog.domain.product.ProductDTO;
import com.mkv.devcatalog.domain.product.ProductRepository;
import com.mkv.devcatalog.infra.exception.IntegrityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    @Autowired
    private CategoryRepository catRepository;

    @Transactional(readOnly = true)
    public Page<ProductDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(ProductDTO::new);
    }

    @Transactional(readOnly = true)
    public ProductDTO findById(Long id) {
        Product product = repository.getReferenceById(id);

        return new ProductDTO(product, product.getCategories());
    }

    @Transactional
    public ProductDTO insert(ProductDTO dto) {
        Product product = new Product();
        copyDtoToEntity(dto, product);
        repository.save(product);

        return new ProductDTO(product);
    }

    @Transactional
    public ProductDTO update(Long id, ProductDTO dto) {
        Product product = repository.getReferenceById(id);
        copyDtoToEntity(dto, product);

        return new ProductDTO(product);
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

    private void copyDtoToEntity(ProductDTO dto, Product entity) {
        entity.setName(dto.getName());
        entity.setDescription(dto.getDescription());
        entity.setPrice(dto.getPrice());
        entity.setImgUrl(dto.getImgUrl());
        entity.setDate(dto.getDate());

        entity.getCategories().clear();
        for (CategoryDTO catDto : dto.getCategories()) {
            Category category = catRepository.getReferenceById(catDto.id());
            entity.getCategories().add(category);
        }
    }
}
