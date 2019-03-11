package pl.edu.agh.iisg.to.nurikabe.model;

import javafx.application.Application;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to.nurikabe.presenter.MenuPresenter;


public class Nurikabe extends Application {
    public static int numberOfDebugColors = 6;
    private static Attempt attempt;

    public static void startGame(String name) {
        attempt = new Attempt(name);
        attempt.startAttempt();
        BoardInitializer boardInitializer = new BoardInitializer();
        boardInitializer.initializeBoard();
    }

    public static void endGame(boolean saveAttempt, boolean initMenu) {
        if (saveAttempt) {
            attempt.finishAttempt();
            MenuPresenter.initializeMenu();
        }
        else {
            if (initMenu) {
                MenuPresenter.initializeMenu();
            }
            else {
                System.exit(0);
            }
        }
    }

    @Override
    public void start(Stage primaryStage) {
        MenuPresenter.initializeMenu();
    }

    public static Attempt getAttempt() {
        return attempt;
    }


    @Override
    public void stop() throws Exception {
        super.stop();
        endGame(false, false);
    }
}
