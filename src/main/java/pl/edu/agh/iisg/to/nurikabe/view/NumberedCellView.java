package pl.edu.agh.iisg.to.nurikabe.view;

import javafx.scene.Node;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;


public class NumberedCellView extends CellView {
    private final StackPane stack;

    NumberedCellView(int x, int y, BoardView boardView, Text text) {
        super(x, y, boardView);
        this.stack = new StackPane();
        stack.getChildren().addAll(imageView, text);
        this.setWhite();
    }

    @Override
    public Node getNode() {
        return this.stack;
    }
}
