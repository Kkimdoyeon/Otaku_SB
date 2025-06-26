package com.otakumap.global.validation.validator;

import com.otakumap.domain.event_like.service.EventLikeQueryService;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.validation.annotation.ExistEventLike;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class EventLikeExistValidator implements ConstraintValidator<ExistEventLike, List<Long>> {
    private final EventLikeQueryService eventLikeQueryService;

    @Override
    public void initialize(ExistEventLike constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(List<Long> eventIds, ConstraintValidatorContext context) {
        if (eventIds == null || eventIds.isEmpty()) {
            return false;
        }

        boolean isValid = eventIds.stream()
                .allMatch(eventId -> eventLikeQueryService.isEventLikeExist(eventId));

        if (!isValid) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(ErrorStatus.EVENT_LIKE_NOT_FOUND.toString()).addConstraintViolation();
        }
        return isValid;
    }
}