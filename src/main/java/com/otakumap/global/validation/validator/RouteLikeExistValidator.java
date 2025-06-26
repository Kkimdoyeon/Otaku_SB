package com.otakumap.global.validation.validator;


import com.otakumap.domain.route_like.service.RouteLikeQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistRouteLike;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RouteLikeExistValidator implements ConstraintValidator<ExistRouteLike, List<Long>> {
    private final RouteLikeQueryService routeLikeQueryService;

    @Override
    public void initialize(ExistRouteLike constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> routeIds, ConstraintValidatorContext context) {
        if (routeIds == null || routeIds.isEmpty()) {
            return false;
        }

        boolean isValid = routeIds.stream()
                .allMatch(routeLikeQueryService::isRouteLikeExist);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ROUTE_LIKE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}