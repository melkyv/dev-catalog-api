package com.mkv.devcatalog.controller;

import com.mkv.devcatalog.domain.user.UserDTO;
import com.mkv.devcatalog.domain.user.UserInsertDTO;
import com.mkv.devcatalog.domain.user.UserUpdateDTO;
import com.mkv.devcatalog.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService service;

    @GetMapping
    public ResponseEntity<Page<UserDTO>> findAll(@PageableDefault(size = 15, sort = "id", direction = Sort.Direction.DESC) Pageable pagination) {
        Page<UserDTO> list = service.findAll(pagination);

        return ResponseEntity.ok(list);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> findById(@PathVariable Long id) {
        UserDTO dto = service.findById(id);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<UserDTO> insert(@Valid @RequestBody UserInsertDTO dto, UriComponentsBuilder uriBuilder) {
        UserDTO newDto = service.insert(dto);
        URI uri = uriBuilder.path("/users/{id}").buildAndExpand(newDto.getId()).toUri();

        return ResponseEntity.created(uri).body(newDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> update(@PathVariable Long id, @Valid @RequestBody UserUpdateDTO dto) {
        UserDTO newDto = service.update(id, dto);

        return ResponseEntity.ok(newDto);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
