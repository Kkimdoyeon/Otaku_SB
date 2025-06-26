package com.otakumap.domain.image.service;

import com.otakumap.domain.event_review.entity.EventReview;
import com.otakumap.domain.event_review.repository.EventReviewRepository;
import com.otakumap.domain.image.converter.ImageConverter;
import com.otakumap.domain.image.entity.Image;
import com.otakumap.domain.image.repository.ImageRepository;
import com.otakumap.domain.place_review.entity.PlaceReview;
import com.otakumap.domain.place_review.repository.PlaceReviewRepository;
import com.otakumap.domain.reviews.enums.ReviewType;
import com.otakumap.global.apiPayload.code.status.ErrorStatus;
import com.otakumap.global.apiPayload.exception.handler.ImageHandler;
import com.otakumap.global.apiPayload.exception.handler.ReviewHandler;
import com.otakumap.global.util.AmazonS3Util;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ImageCommandServiceImpl implements ImageCommandService {
    private final ImageRepository imageRepository;
    private final AmazonS3Util amazonS3Util;
    private final EventReviewRepository eventReviewRepository;
    private final PlaceReviewRepository placeReviewRepository;

    @Override
    @Transactional
    public Image uploadProfileImage(MultipartFile file, Long userId) {
        String keyName = amazonS3Util.generateProfileKeyName();
        String fileUrl = amazonS3Util.uploadFile(keyName, file);

        return imageRepository.save(ImageConverter.toImage((keyName.split("/")[1]), keyName, fileUrl));
    }

    @Override
    @Transactional
    public List<Image> uploadReviewImages(List<MultipartFile> files, Long reviewId, ReviewType reviewType) {
        return files.stream()
                .map(file -> {
                    String keyName = amazonS3Util.generateReviewKeyName();
                    String fileUrl = amazonS3Util.uploadFile(keyName, file);
                    Image image = ImageConverter.toImage((keyName.split("/")[1]), keyName, fileUrl);

                    if (reviewType == ReviewType.EVENT) {
                        EventReview eventReview = eventReviewRepository.findById(reviewId)
                                .orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND));
                        image.setEventReview(eventReview);
                    } else if (reviewType == ReviewType.PLACE) {
                        PlaceReview placeReview = placeReviewRepository.findById(reviewId)
                                .orElseThrow(() -> new ReviewHandler(ErrorStatus.REVIEW_NOT_FOUND));
                        image.setPlaceReview(placeReview);
                    }

                    return imageRepository.save(image);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public Image uploadImage(MultipartFile file, String folder) {
        String keyName = switch (folder) {
            case "profile" -> amazonS3Util.generateProfileKeyName();
            case "review" -> amazonS3Util.generateReviewKeyName();
            case "event" -> amazonS3Util.generateEventKeyName();
            default -> throw new ImageHandler(ErrorStatus.INVALID_FOLDER);
        };

        String fileUrl = amazonS3Util.uploadFile(keyName, file);
        return imageRepository.save(ImageConverter.toImage((keyName.split("/")[1]), keyName, fileUrl));
    }
}
