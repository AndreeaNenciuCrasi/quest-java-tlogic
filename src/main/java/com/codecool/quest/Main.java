package com.codecool.quest;

import com.codecool.quest.logic.*;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ListView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    GameMap map = MapLoader.loadMap("map.txt", null);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    ListView<String> listView;
    int levelCount = 0;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        System.out.println(map);
        listView = new ListView<>();
        listView.getItems().addAll("Health: " + map.getPlayer().getHealth(), " ", "INVENTORY", "Sword: " + map.getPlayer().getSword(),
                "Diamond: " + map.getPlayer().getDiamond(), "Key: " + map.getPlayer().getKey(), "Enemy Health: " + map.getPlayer().getSkeleton());
        ui.add(listView, 0, 0);

        BorderPane borderPane = new BorderPane();
        borderPane.setCenter(canvas);
        borderPane.setRight(ui);

        Scene scene = new Scene(borderPane);
        primaryStage.setScene(scene);
        refresh();
        listView.setOnMouseClicked(e -> borderPane.requestFocus());
        scene.setOnKeyPressed(this::onKeyPressed);

        primaryStage.setTitle("Codecool Quest");
        primaryStage.show();

    }

    private void onKeyPressed(KeyEvent keyEvent) {
        switch (keyEvent.getCode()) {
            case UP:
                map.getPlayer().move(0, -1);
                refresh();
                break;
            case DOWN:
                map.getPlayer().move(0, 1);
                refresh();
                break;
            case LEFT:
                map.getPlayer().move(-1, 0);
                refresh();
                break;
            case RIGHT:
                map.getPlayer().move(1, 0);
                refresh();
                break;
        }

        if (map.getPlayer().getCell().getType() == CellType.OPEN_DOOR) {
            map = MapLoader.loadMap("maze.txt", map.getPlayer());
            Message myWindow1 = new Message("Level Up");
            myWindow1.setVisible(true);
        } else if (map.getPlayer().getCell().getType() == CellType.WHITE_OPEN_DOOR) {
            map = MapLoader.loadMap("level3.txt", map.getPlayer());
            Message myWindow2 = new Message("Level Up");
            myWindow2.setVisible(true);
        }
    }

    private void refresh() {
        context.setFill(Color.BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null) {
                    Tiles.drawTile(context, cell.getActor(), x, y);
                } else {
                    Tiles.drawTile(context, cell, x, y);
                }
            }
        }

        listView.getItems().clear();
        listView.getItems().addAll("Health: " + map.getPlayer().getHealth(), "Enemy Health: " + map.getPlayer().getSkeleton(), " ", "INVENTORY", "Sword: " + map.getPlayer().getSword(),
                "Diamond: " + map.getPlayer().getDiamond(), "Key: " + map.getPlayer().getKey());
    }
}
