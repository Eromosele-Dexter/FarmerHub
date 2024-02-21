package services;

import models.RatingAndReview;
import models.User;
import models.composite_responses.RatingAndReviewResponse;
import repositories.reviewRepository.IReviewRepository;

public class RatingAndReviewService {

    private IReviewRepository reviewRepository;
    private UserService userService;

    public RatingAndReviewService(IReviewRepository reviewRepository, UserService userService) {
        this.reviewRepository = reviewRepository;
        this.userService = userService;
    }


    public void addRatingAndReview(RatingAndReview ratingAndReview) {
        reviewRepository.addRatingAndReview(ratingAndReview);
    }

    public void updateRatingAndReview(RatingAndReview ratingAndReview) {
        reviewRepository.updateRatingAndReview(ratingAndReview);
    }

    public void deleteRatingAndReview(int id) {
        reviewRepository.deleteRatingAndReview(id);
    }

    public RatingAndReviewResponse getRatingAndReview(int id) {
        RatingAndReview ratingAndReview = reviewRepository.getRatingAndReview(id);
        return mapToRatingAndReviewResponse(ratingAndReview);
    }

    public RatingAndReviewResponse[] getAllRatingAndReviewByItemId(int itemId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByItemId(itemId);

        RatingAndReviewResponse[] ratingAndReviewResponses = new RatingAndReviewResponse[ratingAndReviews.length];

        for (int i = 0; i < ratingAndReviews.length; i++) {
            ratingAndReviewResponses[i] = mapToRatingAndReviewResponse(ratingAndReviews[i]);
        }

        return ratingAndReviewResponses;
    }

    public RatingAndReviewResponse[] getAllRatingAndReviewByCustomerId(int customerId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByCustomerId(customerId);

        RatingAndReviewResponse[] ratingAndReviewResponses = new RatingAndReviewResponse[ratingAndReviews.length];

        for (int i = 0; i < ratingAndReviews.length; i++) {
            ratingAndReviewResponses[i] = mapToRatingAndReviewResponse(ratingAndReviews[i]);
        }

        return ratingAndReviewResponses;
    }

    public RatingAndReviewResponse[] getAllRatingAndReviewByCustomerIdAndItemId(int customerId, int itemId) {
        RatingAndReview[] ratingAndReviews = reviewRepository.getAllRatingAndReviewByCustomerIdAndItemId(customerId, itemId);

        RatingAndReviewResponse[] ratingAndReviewResponses = new RatingAndReviewResponse[ratingAndReviews.length];

        for (int i = 0; i < ratingAndReviews.length; i++) {
            ratingAndReviewResponses[i] = mapToRatingAndReviewResponse(ratingAndReviews[i]);
        }

        return ratingAndReviewResponses;
    }

    private RatingAndReviewResponse mapToRatingAndReviewResponse(RatingAndReview ratingAndReview) {
        User user = userService.getUserById(ratingAndReview.getCustomerId());
        return new RatingAndReviewResponse(
                ratingAndReview.getId(),
                ratingAndReview.getOrderItemId(),
                ratingAndReview.getCustomerId(),
                ratingAndReview.getRating(),
                ratingAndReview.getReview(),
                ratingAndReview.getDate(),
                user.getFirstName(),
                user.getLastName()
        );
    }
}
