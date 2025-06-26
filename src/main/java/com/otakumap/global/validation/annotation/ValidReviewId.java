package com.otakumap.global.validation.annotation;

import com.otakumap.global.validation.validator.ReviewIdValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = ReviewIdValidator.class)
@Target( { ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidReviewId {

    String message() default "이벤트 후기와 장소 후기에 모두 존재하지 않는 후기 id 입니다.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};

}
