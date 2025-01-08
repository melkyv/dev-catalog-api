package com.mkv.devcatalog.controller;

import com.mkv.devcatalog.domain.category.Category;
import com.mkv.devcatalog.domain.category.CategoryDTO;
import com.mkv.devcatalog.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/categories")
public class CategoryController {

    @Autowired
    private CategoryService service;

    @GetMapping
    public ResponseEntity<List<CategoryDTO>> findAll() {
        List<CategoryDTO> list = service.findAll();

        return ResponseEntity.ok(list);
    }
}
