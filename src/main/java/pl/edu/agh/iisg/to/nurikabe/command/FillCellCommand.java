package pl.edu.agh.iisg.to.nurikabe.command;

import javafx.scene.image.Image;
import pl.edu.agh.iisg.to.nurikabe.model.Cell;
import pl.edu.agh.iisg.to.nurikabe.model.CellState;
import pl.edu.agh.iisg.to.nurikabe.model.State;
import pl.edu.agh.iisg.to.nurikabe.view.CellView;


public class FillCellCommand implements Command {
    private Cell cell;
    private State newState;
    private CellState oldState;
    private Image oldImage;
    private Image newImage;

    public FillCellCommand(Cell cell, boolean isDebug) {
        this.cell = cell;
        this.oldState = cell.getState();
        int newStateIndex = countNewStateIndex(isDebug);
        this.newState = State.values()[newStateIndex];
        this.oldImage = getCellView().getImage();
    }

    private int countNewStateIndex(boolean isDebug) {
        int oldStateIndex = this.oldState.getState().ordinal();
        if (!isDebug) {
            return  (oldStateIndex + 1) % 2;
        }
        if (oldStateIndex < 4 && oldStateIndex > 1) {
            return  2 + (oldStateIndex + 1) % 2;
        }
        return  2;
    }

    private CellView getCellView() {
        return cell.getBoardPresenter().getCellView(cell.getX(), cell.getY());
    }

    private void fillCell(State state) {
//        System.out.println(state);
        CellState currentState = cell.getState();
        currentState.setState(state);
        CellView cellView = cell.getBoardPresenter().getCellView(cell.getX(), cell.getY());

        if (state == State.EMPTY) {
            cellView.setWhite();
        } else if (state == State.FILLED) {
            cellView.setBlack();
        } else {
            cellView.setDebug(currentState.getDebugNumber());
        }
    }

    @Override
    public void execute() {
        fillCell(newState);
        newImage = getCellView().getImage();
    }

    @Override
    public void undo() {
        cell.setState(oldState.getState());
        getCellView().setImageView(oldImage);
    }

    @Override
    public void redo() {
        cell.setState(newState);
        getCellView().setImageView(newImage);
    }
}
