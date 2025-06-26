package com.otakumap.global.validation.annotation;

import com.otakumap.global.validation.validator.RouteExistValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = RouteExistValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ExistRoute {
    String message() default "유효하지 않은 루트 ID 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}