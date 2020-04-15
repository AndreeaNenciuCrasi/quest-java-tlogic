package com.codecool.quest;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Random;


public class Main extends Application {
    GameMap map = MapLoader.loadMap("map.txt", null);
    Canvas canvas = new Canvas(
            map.getWidth() * Tiles.TILE_WIDTH,
            map.getHeight() * Tiles.TILE_WIDTH);
    GraphicsContext context = canvas.getGraphicsContext2D();
    ListView<String> listView;
    int levelCount = 0;
    private String playerName;
    private int counter;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        playerName = characterName();
        cheatCodes();
        System.out.println(playerName);
        GridPane ui = new GridPane();
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));

        System.out.println(map);
        listView = new ListView<>();
        listView.getItems().addAll("Character Name: " + playerName + "Health: " + map.getPlayer().getHealth(), " ", "INVENTORY", "Sword: " + map.getPlayer().getSword(),
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

    private String characterName() {
        Stage input = new Stage();
        input.initModality(Modality.APPLICATION_MODAL);
        input.setTitle("Character Name");
        input.setMinWidth(450);
        Label label = new Label();
        label.setText("Enter your character name: ");

        TextField name = new TextField("");

        name.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                playerName = name.getText().trim();
                input.close();
            }
        });

        input.setOnCloseRequest(e -> {
            input.close();
            System.exit(0);
        });

        VBox layout = new VBox(35);
        layout.getChildren().addAll(label, name);
        layout.setAlignment(Pos.CENTER);
        Scene scene = new Scene(layout);
        input.setScene(scene);
        input.showAndWait();
        return playerName;

    }

    private void cheatCodes() {
        if (playerName.equals("Andreea")) {
            map.getPlayer().setHealth(100);
        }
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
        counter++;

        generateMonsters();


        if (map.getPlayer().getCell().getType() == CellType.OPEN_DOOR) {
            levelCount++;
            if (levelCount == 1) {
                map = MapLoader.loadMap("maze.txt", map.getPlayer());
            } else if (levelCount == 2) {
                map = MapLoader.loadMap("level3.txt", map.getPlayer());
            }
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
        listView.getItems().addAll("Character Name: " + playerName, "Health: " + map.getPlayer().getHealth(), " ", "INVENTORY", "Sword: " + map.getPlayer().getSword(),
                "Diamond: " + map.getPlayer().getDiamond(), "Key: " + map.getPlayer().getKey(), "Enemy Health: " + map.getPlayer().getSkeleton());
    }

    public void generateMonsters() {
        for (int x = 0; x < map.getWidth(); x++) {
            for (int y = 0; y < map.getHeight(); y++) {
                Cell cell = map.getCell(x, y);
                if (cell.getActor() != null && counter % 9 == 0) {
                    generateSkeleton2(cell);
                }
            }
        }

    }


    public void generateSkeleton2(Cell cell) {
        Random rand = new Random();
        int[] randomNr = {0, 0, 0};
        int x = rand.nextInt(randomNr.length);
        int y = rand.nextInt(randomNr.length);
        Cell nextCell = cell.getNeighbor(x, y);
        if (nextCell.getType().equals(CellType.FLOOR)) {
            nextCell.setType(CellType.SKELETON2);
        }
    }


}
