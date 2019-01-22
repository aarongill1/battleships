package org.openjfx;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

public class Game extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("GridPane Experiment");

        Tile tile1 = new Tile();
        tile1.setOccupied(true);

        Button button1 = new Button();
        Button button2 = new Button();
        Button button3 = new Button();
        Button button4 = new Button();

        button1.setPrefSize(1000, 1000);
        button2.setPrefSize(1000, 1000);
        button3.setPrefSize(1000, 1000);
        button4.setPrefSize(1000, 1000);

        GridPane gridPane = new GridPane();

        gridPane.add(button1, 0, 0, 1, 1);
        gridPane.add(button2, 0, 1, 1, 1);
        gridPane.add(button3, 1, 0, 1, 1);
        gridPane.add(button4, 1, 1, 1, 1);
        gridPane.setGridLinesVisible(true);

        RowConstraints row1 = new RowConstraints();
        row1.setPercentHeight(50);
        RowConstraints row2 = new RowConstraints();
        row2.setPercentHeight(50);
        gridPane.getRowConstraints().addAll(row1, row2);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(50);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(50);
        gridPane.getColumnConstraints().addAll(column1, column2);

        button1.setOnAction(actionEvent ->  {
            tile1.fire();
            gridPane.add(new Label("Miss"), 0, 0, 1, 1);

        });
        button2.setOnAction(actionEvent ->  {
            gridPane.add(new Label("Miss"), 0, 1, 1, 1);
        });
        button3.setOnAction(actionEvent ->  {
            gridPane.add(new Label("Miss"), 1, 0, 1, 1);
        });
        button4.setOnAction(actionEvent ->  {

            gridPane.add(new Label("HIT!!!"), 1, 1, 1, 1);
        });

        Scene scene = new Scene(gridPane, 500, 500);
        primaryStage.setScene(scene);
        scene.getStylesheets().add
                (Game.class.getResource("test.css").toExternalForm());
        primaryStage.show();
    }

    public static void main(String[] args) {
        Application.launch(args);
    }
}