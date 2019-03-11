package pl.edu.agh.iisg.to.nurikabe.model;

import javafx.animation.AnimationTimer;
import pl.edu.agh.iisg.to.nurikabe.presenter.BoardPresenter;

public class Timer extends AnimationTimer {
    private int minutes;
    private int seconds;
    private long lastTime;

    Timer() {
        this.seconds = 0;
        this.minutes = 0;
        lastTime = 0;
    }

    String getTime() {
        return String.format("%02d:%02d", this.minutes, this.seconds);
    }

    @Override
    public void handle(long now) {
        if (lastTime != 0) {
            if (now > lastTime + 1_000_000_000) {
                seconds++;
                if (seconds > 59) {
                    minutes += seconds / 60;
                    seconds %= 60;
                }
                if (minutes > 59) {
                    Nurikabe.endGame(false, true);
                }
                BoardPresenter.setTimeCounter(minutes, seconds);
                lastTime = now;
            }
        } else {
            lastTime = now;
        }
    }

    @Override
    public void stop() {
        super.stop();
    }
}
