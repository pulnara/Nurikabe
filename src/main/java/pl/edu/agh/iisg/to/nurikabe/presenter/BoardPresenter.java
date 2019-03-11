package pl.edu.agh.iisg.to.nurikabe.presenter;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to.nurikabe.command.CommandRegistry;
import pl.edu.agh.iisg.to.nurikabe.command.FillCellCommand;
import pl.edu.agh.iisg.to.nurikabe.model.Board;
import pl.edu.agh.iisg.to.nurikabe.model.Cell;
import pl.edu.agh.iisg.to.nurikabe.model.Nurikabe;
import pl.edu.agh.iisg.to.nurikabe.model.Timer;
import pl.edu.agh.iisg.to.nurikabe.view.BoardView;
import pl.edu.agh.iisg.to.nurikabe.view.CellView;
import java.util.ArrayList;

public class BoardPresenter {
    private Stage stage = new Stage();    private Board board;
    private BoardView boardView;
    private CellView[][] cellViews;
    private CommandRegistry commandRegistry = new CommandRegistry();

    public BoardPresenter(Board board) {
        this.board = board;
        this.boardView = new BoardView(this);
        int size = board.getSize();
        cellViews = new CellView[size][size];
    }

    private CellView createCellView(Cell cell, int j, int i) {
        if (cell.getIsIsland()) {
            if (cell.getNumber() != null) {
                Text text = new Text(cell.getNumber().toString());
                return boardView.createNewCell(j, i, text);
            }
            return  boardView.createNewCell(j, i, null);
        }
        return boardView.createNewCell(j, i, null);
    }

    public CellView getCellView(int i, int j) {
        return cellViews[i][j];
    }

    public void setUpBoard() {
        int size = board.getSize();
        GridPane gridPane = boardView.getGridPane();

        gridPane.setMaxWidth(size);
        gridPane.setMaxHeight(size);

        Cell[][] cells = board.getCells();
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                Cell currentCell = cells[i][j];
                cellViews[i][j] = createCellView(currentCell, j, i);
            }
        }
    }

    public void manageLeftClick(int x, int y) {
        Cell activeCell = this.board.getCells()[x][y];
        FillCellCommand fillCellCommand = new FillCellCommand(activeCell, false);
        commandRegistry.executeCommand(fillCellCommand);
    }

    public void manageRightClick(int x, int y) {
        Cell activeCell = this.board.getCells()[x][y];
        FillCellCommand fillCellCommand = new FillCellCommand(activeCell, true);
        commandRegistry.executeCommand(fillCellCommand);
    }

    private void highlightInvalidCells(ArrayList<Cell> invalidCells) {
        for (Cell invalidCell : invalidCells) {
            int x = invalidCell.getX();
            int y = invalidCell.getY();
            cellViews[x][y].highlightInvalid();
        }
    }

    public void checkBoardValidity() {
        boolean result = this.board.validate();
        if (result) {
            Timer timer = Nurikabe.getAttempt().getTimer();
            timer.stop();
            Nurikabe.endGame(true, false);
            closeStage();
        } else {
            this.highlightInvalidCells(Board.getInvalidCells());
            AlertPresenter.showFailureAlert();
        }
    }

    public void closeStage() {
        if (stage.isShowing()) {
            stage.close();
        }
    }

    public static void setTimeCounter(int minutes, int seconds) {
        BoardView.setTimer(minutes, seconds);
    }

    public CommandRegistry getCommandRegistry() {
        return commandRegistry;
    }

    public void initView() {
        Scene scene = new Scene(boardView.init(), 600, 400);
        stage.setTitle("Nurikabe");
        stage.setScene(scene);
        stage.show();
    }
}
