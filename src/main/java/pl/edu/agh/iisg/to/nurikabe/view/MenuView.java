package pl.edu.agh.iisg.to.nurikabe.view;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import pl.edu.agh.iisg.to.nurikabe.model.Nurikabe;
import pl.edu.agh.iisg.to.nurikabe.persistence.ScoreboardManager;
import pl.edu.agh.iisg.to.nurikabe.presenter.MenuPresenter;
import static pl.edu.agh.iisg.to.nurikabe.view.BoardView.addShadowEffect;


public class MenuView {
    private Button startButton;
    private TextField nameField;
    private Button exitButton;

    public MenuView() {
        this.startButton = new Button("Play");
        this.exitButton = new Button("Exit");
        this.nameField = new TextField();
        nameField.setPromptText("Enter your name");
    }

    private TableColumn initializeColumn(String name, String label, int size) {
        TableColumn<String, String> column = new TableColumn<>(label);
        column.setCellValueFactory(new PropertyValueFactory<>(name));
        column.setMinWidth(size);
        if (!name.equals("name"))
            column.setStyle("-fx-alignment: CENTER;");
        return column;
    }

    private void initializeAllColumns(TableView scoreboard) {
        TableColumn numberColumn = initializeColumn("id", "No", 10);
        TableColumn nameColumn = initializeColumn("name", "Name", 30);
        TableColumn timeColumn = initializeColumn("time", "Time", 20);
        scoreboard.getColumns().addAll(numberColumn, nameColumn, timeColumn);
    }

    private VBox initializeScoreboard() {
        TableView scoreboard = new TableView();
        scoreboard.setFixedCellSize(25);
        scoreboard.setPrefHeight(10 * 25 + 28);
        scoreboard.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        initializeAllColumns(scoreboard);
        scoreboard.setItems(ScoreboardManager.getTopScores(10));
        Text title = new Text("Highscores");
        title.setFont(new Font(20));

        VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10, 0, 30, 30));
        vbox.getChildren().addAll(title, scoreboard);
        return vbox;
    }

    private void initializeButtons(Stage primaryStage, BorderPane borderPane) {
        startButton.setOnAction(e -> { if (!nameField.getText().equals("")) {
            MenuPresenter.startGame(nameField.getText());
            primaryStage.close(); }
        else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No player name provided");
            alert.setHeaderText("Please, input your name before starting the game.");
            alert.showAndWait();
        }} );

        exitButton.setOnAction(e -> {
            Nurikabe.endGame(false, false);
        });

        addShadowEffect(startButton);
        addShadowEffect(exitButton);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(310, 20, 0, 0));
        vbox.getChildren().add(exitButton);

        borderPane.setRight(vbox);
    }

    public void init() {
        Stage primaryStage = new Stage();
        primaryStage.setTitle("Nurikabe");

        GridPane centerPane = new GridPane();
        centerPane.add(nameField, 0, 0);
        centerPane.add(startButton, 0, 1);
        GridPane.setHalignment(startButton, HPos.CENTER);
        GridPane.setValignment(startButton, VPos.CENTER);
        centerPane.setVgap(10);
        centerPane.setAlignment(Pos.CENTER);

        BorderPane borderPane = new BorderPane();
        BorderPane.setAlignment(centerPane, Pos.CENTER);
        borderPane.setCenter(centerPane);
        borderPane.setLeft(initializeScoreboard());
        initializeButtons(primaryStage, borderPane);

        primaryStage.setScene(new Scene(new StackPane(borderPane), 600, 400));
        primaryStage.show();
    }
}
