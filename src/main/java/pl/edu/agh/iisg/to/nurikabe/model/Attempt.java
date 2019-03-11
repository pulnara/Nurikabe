package pl.edu.agh.iisg.to.nurikabe.model;

import pl.edu.agh.iisg.to.nurikabe.persistence.ScoreboardManager;
import pl.edu.agh.iisg.to.nurikabe.presenter.AlertPresenter;

public class Attempt {
    private Timer timer;
    private String playerName;

    Attempt(String name) {
        this.playerName = name;
        this.timer = new Timer();
    }

    void startAttempt() {
        this.timer.start();
    }

    void finishAttempt() {
        Score score = new Score(playerName, timer.getTime());
        ScoreboardManager.addScore(score);
        AlertPresenter.showSuccessAlert(score);
    }

    public Timer getTimer() {
        return timer;
    }
}
