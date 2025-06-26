package com.otakumap.global.validation.validator;

import com.otakumap.domain.place.service.PlaceQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistPlace;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PlaceExistValidator implements ConstraintValidator<ExistPlace, Long> {
    private final PlaceQueryService placeQueryService;

    @Override
    public void initialize(ExistPlace constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long placeId, ConstraintValidatorContext context) {
        if (placeId == null) {
            return false;
        }

        boolean isValid = placeQueryService.isPlaceExist(placeId);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PLACE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
