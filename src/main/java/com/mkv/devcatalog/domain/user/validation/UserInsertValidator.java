package com.mkv.devcatalog.domain.user.validation;

import java.util.ArrayList;
import java.util.List;

import com.mkv.devcatalog.domain.user.User;
import com.mkv.devcatalog.domain.user.UserInsertDTO;
import com.mkv.devcatalog.domain.user.UserRepository;
import com.mkv.devcatalog.infra.exception.ValidationErrorData;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UserInsertValidator implements ConstraintValidator<UserInsertValid, UserInsertDTO> {

    @Autowired
    private UserRepository repository;

    @Override
    public void initialize(UserInsertValid ann) {
    }

    @Override
    public boolean isValid(UserInsertDTO dto, ConstraintValidatorContext context) {

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
