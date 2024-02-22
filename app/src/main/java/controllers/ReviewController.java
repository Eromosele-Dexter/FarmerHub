package controllers;

import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Item;
import views.customerViews.ReviewPage;

public class ReviewController {


    public static void viewReviews(Item item, Stage stage, Scene scene) {
        new ReviewPage(stage, item.getId(), scene);
        return;
    }


    
}
