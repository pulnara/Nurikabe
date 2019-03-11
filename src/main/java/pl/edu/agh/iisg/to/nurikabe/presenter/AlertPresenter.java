package pl.edu.agh.iisg.to.nurikabe.presenter;

import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import pl.edu.agh.iisg.to.nurikabe.model.Score;
import pl.edu.agh.iisg.to.nurikabe.persistence.ScoreboardManager;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class AlertPresenter {
    private static void showAlert(String title, String headerText, String contentText, String imageFileName) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        try {
            alert.setGraphic(new ImageView(new Image(new FileInputStream(imageFileName))));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        alert.showAndWait();
    }

    public static void showSuccessAlert(Score score) {
        String title = "Congratulations!";
        String headerText = "Your solution is correct!";
        int place = ScoreboardManager.findShorter(score.getTime());
        String contentText = String.format("You solved the puzzle in %s, which places you %d in the highscore!",
                score.getTime(), place);
        String imageFileName = "hooray.jpg";
        showAlert(title, headerText, contentText, imageFileName);
    }

    static void showFailureAlert() {
        String title = "Sorry...";
        String headerText = "Your solution is incorrect.";
        String contentText = "Fix highlighted cells and try again.";
        String imageFileName = "sad.jpg";
        showAlert(title, headerText, contentText, imageFileName);
    }
}
