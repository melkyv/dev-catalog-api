package com.mkv.devcatalog.domain.user.validation;

import com.mkv.devcatalog.domain.user.User;
import com.mkv.devcatalog.domain.user.UserRepository;
import com.mkv.devcatalog.domain.user.UserUpdateDTO;
import com.mkv.devcatalog.infra.exception.ValidationErrorData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

public class UserUpdateValidator implements ConstraintValidator<UserUpdateValid, UserUpdateDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserUpdateValid ann) {
    }

    @Override
    public boolean isValid(UserUpdateDTO dto, ConstraintValidatorContext context) {

        List<ValidationErrorData> list = new ArrayList<>();

        User user = repository.findByEmail(dto.getEmail());
        if (user != null) {
            list.add(new ValidationErrorData("email", "Email já existe"));
        }

        for (ValidationErrorData e : list) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(e.message()).addPropertyNode(e.field())
                .addConstraintViolation();
        }
        return list.isEmpty();
    }
}
