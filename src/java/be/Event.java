package be;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.w3c.dom.css.RGBColor;

import java.time.LocalDateTime;

public class Event {

    private int id;
    private StringProperty eventName;
    private LocalDateTime startDateTime;
    private LocalDateTime endDateTime;
    private ObjectProperty location;
    private int ticketsSold;
    private int ticketsRemaining;
    private double[] ticketPrice;

    private VBox node;
    private Image banner;
    private Color background;
    private Color imageFill;
    private Label lblName;
    private Label lblDate;

    public Event() {
        eventName = new SimpleStringProperty();
        this.eventName.set("Name");
        this.startDateTime = LocalDateTime.now();

        imageFill = Color.rgb(203, 161, 137);
        background = Color.rgb(197, 112, 64);

        createNode();
    }

    private void createNode(){
        node = new VBox();
        node.setMaxSize(450, 150);
        node.setStyle("-fx-background-color: rgb(197, 112, 64); -fx-background-radius: 5;");
        node.setAlignment(Pos.CENTER);
        ImageView imageView = new ImageView();
        if (banner == null){
            imageView.setImage(generateBlankImage(imageFill));
        }
        if (banner != null){
            imageView.setImage(banner);
        }
        imageView.setFitHeight(94);
        imageView.setFitWidth(331);
        imageView.setTranslateY(0);
        imageView.setStyle("-fx-background-radius: 5;");

        GridPane gridPane = new GridPane();
        lblDate = new Label();
        lblDate.setText(startDateTime.toString());

        lblName = new Label();
        lblName.setText(eventName.get());

        gridPane.add(lblName, 0, 0);
        lblName.setAlignment(Pos.CENTER_LEFT);
        gridPane.add(lblDate, 0,0);
        lblDate.setAlignment(Pos.CENTER_RIGHT);

        node.getChildren().add(imageView);
        node.getChildren().add(gridPane);



    }

    /**
     * Generates an image of a single color, with a 1x1 pixel resolution, which can then be scaled to the size of the ImageView.
     * @return a blank image.
     */
    private Image generateBlankImage(Color color) {
        WritableImage img = new WritableImage(1, 1);
        PixelWriter pw = img.getPixelWriter();
        pw.setColor(0, 0, color);
        return img ;
    }

    public VBox getNode(){
        return node;
    }


}
