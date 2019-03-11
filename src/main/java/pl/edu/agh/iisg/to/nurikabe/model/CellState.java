package pl.edu.agh.iisg.to.nurikabe.model;

public class CellState {
    private State state;
    private Integer debugNumber;

    CellState(State state) {
        this.state = state;
        this.debugNumber = -1;
    }

    public void setState(State state) {
        this.state = state;
        if (this.state == State.EMPTYSUSPECT || this.state == State.FILLEDSUSPECT) {
            this.debugNumber = (this.debugNumber + 1) % Nurikabe.numberOfDebugColors;
        }
    }

    public State getState() {
        return state;
    }

    public Integer getDebugNumber() {
        return debugNumber;
    }

}
