package pl.edu.agh.iisg.to.nurikabe.model;

import pl.edu.agh.iisg.to.nurikabe.presenter.BoardPresenter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;


public class Board {
    private final Logger logger = Logger.getLogger(getClass().getName());
    private static ArrayList<Cell> invalidCells = new ArrayList<>();
    private Cell cells[][];
    private int size;
    private BoardPresenter boardPresenter;

    Board(int size) {
        this.size = size;
        this.cells = new Cell[size][size];
        this.boardPresenter = new BoardPresenter(this);
    }

    public Cell[][] getCells() {
        return this.cells;
    }

    BoardPresenter getBoardPresenter() {
        return boardPresenter;
    }

    public int getSize() {
        return size;
    }

    private boolean validateCell(Cell cell) {
        boolean isValid = cell.validate();
        if (!isValid) {
            invalidCells.add(cell);
        }
        return isValid;
    }

    public static ArrayList<Cell> getInvalidCells() {
        return invalidCells;
    }

    public boolean validate() {
        invalidCells.clear();
        boolean valid = true;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (!validateCell(cells[i][j]))
                    valid = false;
            }
        }
        if (invalidCells.size() > 0) {
            logger.log(Level.INFO, "Incorrectly filled cells:");
            for (Cell cell : invalidCells) {
                logger.log(Level.INFO, String.format("(%d, %d)", cell.getX(), cell.getY()));
            }
        }
        return valid;
    }

    void setCell(int i, int j, Cell cell) {
        this.cells[i][j] = cell;
    }
}
