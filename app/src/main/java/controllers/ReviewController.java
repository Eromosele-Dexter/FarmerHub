package controllers;

import javafx.stage.Stage;
import models.Item;
import views.customerViews.ReviewPage;

public class ReviewController {


    public static void viewReviews(Item item, Stage stage) {
        new ReviewPage(stage, item.getId());
        return;
    }
    
}
