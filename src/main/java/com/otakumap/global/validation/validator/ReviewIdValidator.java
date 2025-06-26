package com.otakumap.global.validation.validator;

import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ValidReviewId;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReviewIdValidator implements ConstraintValidator<ValidReviewId, Long> {

    private final EventReviewRepository eventReviewRepository;
    private final PlaceReviewRepository placeReviewRepository;

    @Override
    public void initialize(ValidReviewId constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {

        if (eventReviewRepository.existsById(value)) {
            return true;
        }

        boolean isValid = placeReviewRepository.existsById(value);

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.INVALID_REVIEW_ID.toString()).addConstraintViolation();
        }
        return isValid;
    }
}
