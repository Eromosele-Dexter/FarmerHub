package models;

public class RatingAndReview {

    private int orderItemId;

    private int customerId;

    private int rating ; // 1-5

    private String review;

    private String date;

    public RatingAndReview(int orderItemId, int customerId, int rating, String review, String date) {
        this.orderItemId = orderItemId;
        this.customerId = customerId;
        this.rating = rating;
        this.review = review;
        this.date = date;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
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

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
    
}
