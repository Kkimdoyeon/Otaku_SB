package com.otakumap.global.validation.validator;

import com.otakumap.domain.animation.service.AnimationQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistAnimation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AnimationExistValidator implements ConstraintValidator<ExistAnimation, Long> {
    private final AnimationQueryService animationQueryService;

    @Override
    public void initialize(ExistAnimation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long animationId, ConstraintValidatorContext context) {
        boolean isValid = animationQueryService.existsById(animationId);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ANIMATION_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}