package com.otakumap.global.validation.validator;

import com.otakumap.domain.place_like.service.PlaceLikeQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistPlaceLike;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class PlaceLikeExistValidator implements ConstraintValidator<ExistPlaceLike, Long> {
    private final PlaceLikeQueryService placeLikeQueryService;

    @Override
    public void initialize(ExistPlaceLike constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long placeLikeId, ConstraintValidatorContext context) {
        if (placeLikeId == null ) {
            return false;
        }

        boolean isValid = placeLikeQueryService.isPlaceLikeExist(placeLikeId);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.PLACE_LIKE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
