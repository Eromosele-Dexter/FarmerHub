package views.customerViews;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import models.composite_responses.RatingAndReviewResponse;

public class ReviewPage {

    public ReviewPage(Stage stage, int itemId) {
        VBox mainLayout = new VBox(10);
        mainLayout.setPadding(new Insets(15));
        mainLayout.setAlignment(Pos.TOP_CENTER);

        // Mock call to get reviews for the item
        List<RatingAndReviewResponse> reviews = getReviewsForItem(itemId);

        for (RatingAndReviewResponse review : reviews) {
            VBox reviewBox = new VBox(5);
            reviewBox.setPadding(new Insets(10));
            reviewBox.setStyle("-fx-border-color: #cccccc; -fx-border-radius: 5; -fx-background-color: #f8f8f8;");
            
            Label reviewerName = new Label(review.getUserFirstName() + " " + review.getUserLastName());
            Label reviewDate = new Label(review.getDate());
            Label rating = new Label("Rating: " + review.getRating());
                        // Using a Label for review text with wrapping enabled
            Label reviewText = new Label(review.getReview());
            reviewText.setWrapText(true); // Enable text wrapping
            



            reviewBox.getChildren().addAll(reviewerName, reviewDate, rating, reviewText);
            mainLayout.getChildren().add(reviewBox);
        }

        // Section to add a new review
        TextField ratingInput = new TextField();
        ratingInput.setPromptText("Rating (1-5)");

        ratingInput.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) { 
                ratingInput.setText(newValue.replaceAll("[^\\d]", ""));
            } else if (!newValue.isEmpty()) {
                int rating = Integer.parseInt(newValue);
                if (rating < 1) ratingInput.setText("1");
                if (rating > 5) ratingInput.setText("5");
            }
        });

        TextArea reviewInput = new TextArea();
        reviewInput.setPromptText("Your review");

        Button submitReviewButton = new Button("Submit Review");

        submitReviewButton.setOnAction(e -> {
            // Logic to submit review
            System.out.println("Review submitted!");
            // Implement the submission logic here, and refresh the reviews list accordingly
        });

        mainLayout.getChildren().addAll(new Label("Add Your Review:"), ratingInput, reviewInput, submitReviewButton);

        Scene scene = new Scene(mainLayout, 400, 600);
        Platform.runLater(() -> stage.setScene(scene));
    }

        private List<RatingAndReviewResponse> getReviewsForItem(int itemId) {
            // This method should fetch reviews from your data source based on itemId.
            // For demonstration, returning mock data.
            List<RatingAndReviewResponse> mockReviews = new ArrayList<>();

            mockReviews.add(new RatingAndReviewResponse(1, itemId, 1, 4, "Great product, loved it!", "2023-01-01", "John", "Doe"));

            mockReviews.add(new RatingAndReviewResponse(2, itemId, 2, 5, "Absolutely amazing! Remove all comments in project ejhhje  dhjdj ch ch djc hcb  cjcdbjud ", "2023-01-02", "Jane", "Doe"));

            return mockReviews;
    }
    
}
