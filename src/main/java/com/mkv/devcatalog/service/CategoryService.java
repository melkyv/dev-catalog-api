package com.mkv.devcatalog.service;

import com.mkv.devcatalog.domains.category.Category;
import com.mkv.devcatalog.domains.category.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public List<Category> findAll() {
        return repository.findAll();
    }
}
