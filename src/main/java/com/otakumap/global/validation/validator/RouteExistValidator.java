package com.otakumap.global.validation.validator;

import com.otakumap.domain.route.service.RouteQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistRoute;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RouteExistValidator implements ConstraintValidator<ExistRoute, Long> {
    private final RouteQueryService routeQueryService;

    @Override
    public void initialize(ExistRoute constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long routeId, ConstraintValidatorContext context) {
        if (routeId == null) {
            return false;
        }

        boolean isValid = routeQueryService.isRouteExist(routeId);

        if(!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.ROUTE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
