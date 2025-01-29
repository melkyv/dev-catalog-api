package com.mkv.devcatalog.service;

import com.mkv.devcatalog.domain.role.Role;
import com.mkv.devcatalog.domain.role.RoleDTO;
import com.mkv.devcatalog.domain.role.RoleRepository;
import com.mkv.devcatalog.domain.user.User;
import com.mkv.devcatalog.domain.user.UserDTO;
import com.mkv.devcatalog.domain.user.UserInsertDTO;
import com.mkv.devcatalog.domain.user.UserRepository;
import com.mkv.devcatalog.infra.exception.IntegrityError;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserService {

    @Autowired
    private UserRepository repository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private RoleRepository roleRepository;

    @Transactional(readOnly = true)
    public Page<UserDTO> findAll(Pageable pageable) {
        return repository.findAll(pageable).map(UserDTO::new);
    }

    @Transactional(readOnly = true)
    public UserDTO findById(Long id) {
        return new UserDTO(repository.getReferenceById(id));
    }

    @Transactional
    public UserDTO insert(UserInsertDTO dto) {
        User user = new User();
        copyDtoToEntity(dto, user);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        repository.save(user);

        return new UserDTO(user);
    }

    @Transactional
    public UserDTO update(Long id, UserDTO dto) {
        User user = repository.getReferenceById(id);
        copyDtoToEntity(dto, user);

        return new UserDTO(user);
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

    private void copyDtoToEntity(UserDTO dto, User entity) {
        entity.setFirstName(dto.getFirstName());
        entity.setLastName(dto.getLastName());
        entity.setEmail(dto.getEmail());

        entity.getRoles().clear();
        for (RoleDTO roleDto : dto.getRoles()) {
            Role role = roleRepository.getReferenceById(roleDto.id());
            entity.getRoles().add(role);
        }
    }
}
