package com.nhomA.mockproject.service;

import com.nhomA.mockproject.dto.ReviewRequestDTO;
import com.nhomA.mockproject.dto.ReviewResponseDTO;

import java.util.List;

public interface ReviewsService {
    ReviewResponseDTO createReview (ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO updateReview (String username, Long idReview, ReviewRequestDTO reviewRequestDTO);
    ReviewResponseDTO deleteReview (String username,Long idReview, ReviewRequestDTO reviewRequestDTO);
    List<ReviewResponseDTO> getAllReviewsByIdProduct (Long productId);
}
