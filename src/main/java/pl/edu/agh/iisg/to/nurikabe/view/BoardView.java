package pl.edu.agh.iisg.to.nurikabe.view;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import pl.edu.agh.iisg.to.nurikabe.model.Nurikabe;
import pl.edu.agh.iisg.to.nurikabe.presenter.BoardPresenter;


public class BoardView {
    private Button checkButton;
    private GridPane gridPane;
    private BorderPane borderPane;
    private BoardPresenter boardPresenter;
    private Button undoButton;
    private Button redoButton;
    private Button backButton;
    private static Label timeCounter;


    public BoardView(BoardPresenter boardPresenter) {
        this.boardPresenter = boardPresenter;
        this.gridPane = new GridPane();
        this.borderPane = new BorderPane();
        this.checkButton = new Button("Check");
        this.undoButton = new Button("Undo");
        this.redoButton = new Button("Redo");
        this.backButton = new Button("Give up");
    }


    public GridPane getGridPane() {
        return gridPane;
    }

    public CellView createNewCell(int i, int j, Text text) {
        CellView cell;

        if (text != null) {
            cell = new NumberedCellView(j, i, this, text);
        } else {
            cell = new CellView(j, i, this);
            cell.makeReactive();
        }
        gridPane.add(cell.getNode(), i, j);
        return cell;
    }

    static void addShadowEffect(Button button) {
        button.addEventHandler(MouseEvent.MOUSE_ENTERED,
                e -> button.setEffect(new DropShadow()));
        button.addEventHandler(MouseEvent.MOUSE_EXITED,
                e -> button.setEffect(null));
    }

    private void initCheckButton() {
        addShadowEffect(checkButton);
        checkButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> boardPresenter.checkBoardValidity());
        BorderPane.setAlignment(checkButton, Pos.CENTER);
    }

    private void initUndoButton() {
        addShadowEffect(undoButton);
        undoButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> boardPresenter.getCommandRegistry().undo());
    }

    private void initRedoButton() {
        addShadowEffect(redoButton);
        redoButton.addEventHandler(MouseEvent.MOUSE_CLICKED,
                e -> boardPresenter.getCommandRegistry().redo());
    }

    private void initBackButton() {
        addShadowEffect(backButton);
        backButton.setOnAction(e -> {
            Nurikabe.endGame(false, true);
            boardPresenter.closeStage();
        });
    }

    private GridPane initDebugNumberSetting() {
        GridPane debugNumberPane = new GridPane();
        ComboBox debugNumberComboBox = new ComboBox();

        debugNumberComboBox.setStyle("-fx-pref-width: 150;");
        debugNumberComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue ov, String t, String t1) {
                Nurikabe.numberOfDebugColors = Integer.parseInt(t1) * 2;
            }
        });
        debugNumberComboBox.setValue("3");
        debugNumberComboBox.getItems().addAll("1", "2", "3");
        debugNumberPane.add(new Text("Debug color range: "), 0, 0);
        debugNumberPane.add(debugNumberComboBox, 0, 1);
        return debugNumberPane;
    }

    private Label initTimer() {
        timeCounter = new Label("00:00");
        timeCounter.setFont(new Font(50));
        return timeCounter;
    }

    public static void setTimer(int minutes, int seconds) {
        timeCounter.setText(String.format("%02d:%02d", minutes, seconds));
    }

    private void initRightMenu() {
        BorderPane rightPane = new BorderPane();
        initCheckButton();
        initBackButton();
        GridPane undoRedoPane = new GridPane();
        undoRedoPane.add(undoButton, 0, 0);
        undoRedoPane.add(redoButton, 1, 0);
        undoRedoPane.setHgap(5);

        GridPane checkBackPane = new GridPane();
        checkBackPane.add(checkButton, 1, 0);
        checkBackPane.add(backButton, 0, 0);
        checkBackPane.setHgap(50);
        borderPane.setBottom(checkBackPane);
        BorderPane.setMargin(checkBackPane, new Insets(0,0,30,230));

        rightPane.setCenter(undoRedoPane);
        rightPane.setTop(initTimer());
        rightPane.setBottom(initDebugNumberSetting());
        borderPane.setRight(rightPane);

        BorderPane.setMargin(rightPane, new Insets(100,60,80,0));
        BorderPane.setAlignment(rightPane, Pos.CENTER_RIGHT);
        initUndoButton();
        initRedoButton();
    }


    public BorderPane init() {
        initRightMenu();
        borderPane.setCenter(gridPane);

        gridPane.setHgap(1);
        gridPane.setVgap(1);
        gridPane.setStyle("-fx-border: 2px; -fx-border-style: solid; -fx-border-color: grey; -fx-background-color: grey;");

        boardPresenter.setUpBoard();
        return borderPane;
    }

    BoardPresenter getBoardPresenter() {
        return boardPresenter;
    }
}
