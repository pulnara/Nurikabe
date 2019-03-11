package pl.edu.agh.iisg.to.nurikabe.view;

import javafx.scene.Node;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class CellView {
    ImageView imageView;
    private int x, y;
    private BoardView boardView;
    private Color grey = Color.grayRgb(200, 0.3);
    private Color white = Color.WHITE;
    private Color black = Color.BLACK;
    private final int tileSize = 30;

    CellView(int x, int y, BoardView boardView) {
        this.x = x;
        this.y = y;
        this.boardView = boardView;
        this.imageView = new ImageView((createSquare()));
        imageView.setFitWidth(tileSize);
        imageView.setFitHeight(tileSize);
        this.setGrey();
    }

    private WritableImage createSquare() {
        return new WritableImage(1, 1);
    }

    private void setCellColor(Color color) {
        if (this.imageView.getEffect() != null)
            this.imageView.setEffect(null);
        if (!this.imageView.getImage().getClass().isInstance(WritableImage.class))
            this.imageView.setImage(createSquare());
        ((WritableImage) this.imageView.getImage()).getPixelWriter().setColor(0, 0, color);
    }

    private void setCellImage(int debugPictureNum) {
        Image image = null;
        if (this.imageView.getEffect() != null)
            this.imageView.setEffect(null);
        try {
            image = new Image(new FileInputStream(String.format("debug%d.png", debugPictureNum)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        this.imageView.setImage(image);
    }

    public void setWhite() {
        this.setCellColor(white);
    }

    public void setBlack() {
        this.setCellColor(black);
    }

    private void setGrey() {
        this.setCellColor(grey);
    }

    public void setDebug(int debugPictureNum) {
        this.setCellImage(debugPictureNum);
    }

    private void highlightCell(Color color) {
        this.imageView.setEffect(new DropShadow(13, color));
    }

    public void highlightInvalid() {
        this.highlightCell(Color.RED);
    }

    void makeReactive() {
        addReactionToClick(this.imageView);
    }

    private void addReactionToClick(Node node) {
        node.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getButton() == MouseButton.SECONDARY) {
                this.boardView.getBoardPresenter().manageRightClick(this.x, this.y);
            } else if (event.getButton() == MouseButton.PRIMARY) {
                this.boardView.getBoardPresenter().manageLeftClick(this.x, this.y);
            }
            event.consume();
        });
    }

    public Node getNode() {
        return this.imageView;
    }

    public Image getImage() {
        return imageView.getImage();
    }

    public void setImageView(Image image) {
        this.imageView.setImage(image);
    }
}
