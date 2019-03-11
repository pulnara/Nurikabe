package pl.edu.agh.iisg.to.nurikabe.model;

import pl.edu.agh.iisg.to.nurikabe.presenter.BoardPresenter;

public class Cell {
    private boolean isIsland;
    private final int x;
    private final int y;
    private Integer number;
    private CellState state;
    private BoardPresenter boardPresenter;


    public Cell(boolean isIsland, int x, int y, Integer number, BoardPresenter boardPresenter) {
        this.isIsland = isIsland;
        this.x = x;
        this.y = y;
        this.number = number;
        this.state = number != null ? new CellState(State.EMPTY) : new CellState(State.NULL);
        this.boardPresenter = boardPresenter;
    }

    public boolean getIsIsland() {
        return isIsland;
    }

    public Integer getNumber() {
        return number;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public CellState getState() {
        return state;
    }

    public void setState(State state) {
        this.state.setState(state);
    }

    boolean validate() {
        return isIsland && state.getState() == State.EMPTY || !isIsland && state.getState() == State.FILLED;
    }

    public BoardPresenter getBoardPresenter() {
        return boardPresenter;
    }
}
