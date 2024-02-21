package repositories.reviewRepository;

public interface IReviewRepository {

    public void addReview(int orderItemId, int rating, String review);

    public void updateReview(int orderItemId, int rating, String review);

    public void deleteReview(int orderItemId);

    public void getReview(int orderItemId);
} 
