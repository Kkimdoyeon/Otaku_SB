package com.otakumap.global.validation.validator;

import com.otakumap.global.validation.annotation.CheckPage;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CheckPageValidator implements ConstraintValidator<CheckPage, Integer> {

    @Override
    public boolean isValid(Integer value, ConstraintValidatorContext context) {
        if (value == null || value < 1) {
            return false;
        }
        return true;
    }

    @Override
    public void initialize(CheckPage constraintAnnotation) {
    }
}