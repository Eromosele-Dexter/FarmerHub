package models;

public class RatingAndReview {

    private int orderId;

    private int rating ; // 1-5

    private String review;

    public RatingAndReview(int orderId, int rating, String review) {
        this.orderId = orderId;
        this.rating = rating;
        this.review = review;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    
}