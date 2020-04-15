package com.codecool.quest;
import com.codecool.quest.logic.*;
import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.GameMap;
import com.codecool.quest.logic.MapLoader;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
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
import javafx.stage.Screen;
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
    BorderPane borderPane;
    Stage stage;
    boolean resize=false;
    private String playerName;
    private int counter;
    private Cell cell;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        playerName = characterName();
        cheatCodes();
        GridPane ui = new GridPane();
        stage=primaryStage;
        ui.setPrefWidth(200);
        ui.setPadding(new Insets(10));
        stage.setResizable(false);

        System.out.println(map);
        listView = new ListView<>();
        listView.getItems().addAll("Character Name: " + playerName + "Health: " + map.getPlayer().getHealth(), " ", "INVENTORY", "Sword: " + map.getPlayer().getSword(),
                "Diamond: " + map.getPlayer().getDiamond(), "Key: " + map.getPlayer().getKey(), "Enemy Health: " + map.getPlayer().getSkeleton());
        ui.add(listView, 0, 0);

        borderPane = new BorderPane();
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
                map.getPlayer().move(1,0);
                refresh();
                break;
        }
        counter++;

        generateMonsters();


        if (map.getPlayer().getCell().getType() == CellType.OPEN_DOOR) {
            map = MapLoader.loadMap("maze.txt", map.getPlayer());
//            Message myWindow1 = new Message("Level Up");
//            myWindow1.setVisible(true);
            resize=true;
            refresh();
        } else if (map.getPlayer().getCell().getType() == CellType.WHITE_OPEN_DOOR) {
            map = MapLoader.loadMap("level3.txt", map.getPlayer());
//            Message myWindow2 = new Message("Level Up");
//            myWindow2.setVisible(true);
            resize=true;
            refresh();
        }
    }

    private void refresh() {
        Rectangle2D bounds= Screen.getPrimary().getVisualBounds();
        int avWidth=(int)(bounds.getWidth()/1.5);
        int avHeight=(int)(bounds.getHeight());
        int canvasWidth=0;
        int canvasHeight=0;

        if (avWidth>=map.getWidth()*Tiles.TILE_WIDTH&&avHeight>=map.getHeight()*Tiles.TILE_WIDTH) {
            if (resize) {
                canvas = new Canvas(
                        map.getWidth() * Tiles.TILE_WIDTH,
                        map.getHeight() * Tiles.TILE_WIDTH);
                borderPane.setCenter(canvas);
                context = canvas.getGraphicsContext2D();
                context.setFill(Color.BLACK);
                context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
                resize=false;
            }
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
        }
        else
        {

            System.out.println("We are here");
            canvasWidth = avWidth - avWidth % (2 * Tiles.TILE_WIDTH);
            canvasWidth = (canvasWidth + Tiles.TILE_WIDTH <= avWidth) ? canvasWidth + Tiles.TILE_WIDTH : canvasWidth - Tiles.TILE_WIDTH;
            canvasHeight = avHeight - avHeight % (2 * Tiles.TILE_WIDTH);
            canvasHeight = (canvasHeight + Tiles.TILE_WIDTH <= avHeight) ? canvasHeight + Tiles.TILE_WIDTH : canvasHeight - Tiles.TILE_WIDTH;

            //System.out.println(canvasWidth + " " + canvasHeight);
            //System.out.println(avWidth + " " + avHeight);


            int widthCells = canvasWidth / Tiles.TILE_WIDTH;
            int beforeWidth = widthCells / 2;
            int afterWidth = beforeWidth;
            int playerX = map.getPlayer().getX();

            //System.out.println(widthCells + " " + beforeWidth + " " + afterWidth);

            if (beforeWidth > playerX) {
                beforeWidth = playerX;
                afterWidth += afterWidth - beforeWidth;
            } else if (afterWidth > map.getWidth() - 1 - playerX) {

                afterWidth = map.getWidth() - 1 - playerX;
                beforeWidth += beforeWidth - afterWidth;

            }
            //System.out.println(widthCells + " " + beforeWidth + " " + afterWidth);
            //int heightCells = canvasHeight / Tiles.TILE_WIDTH;
            int beforeHeight = canvasHeight / Tiles.TILE_WIDTH / 2;
            int afterHeight = beforeHeight;
            int playerY = map.getPlayer().getY();
            if (beforeHeight > playerY) {
                beforeHeight = playerY;
                afterHeight += afterHeight - beforeHeight;
            } else if (afterHeight > map.getHeight() - 1 - playerY) {
                afterHeight = map.getHeight() - 1 - playerY;
                beforeHeight += beforeHeight - afterHeight;

            }
            if (resize) {
                canvas = new Canvas(canvasWidth, canvasHeight);
                context = canvas.getGraphicsContext2D();
                borderPane.setCenter(canvas);
                stage.sizeToScene();
                stage.centerOnScreen();
                resize=false;
            }
            //System.out.println(playerX+" "+playerY);
            for (int x = playerX-beforeWidth,i=0; x <= playerX+afterWidth; x++,i++) {
                for (int y = playerY-beforeHeight,j=0; y <= playerY+afterHeight; y++,j++) {
                    //System.out.println("Drawing: "+x+" "+y);
                    Cell cell = map.getCell(x, y);
                    if (cell.getActor() != null) {
                        Tiles.drawTile(context, cell.getActor(), i, j);
                    } else {
                        Tiles.drawTile(context, cell, i, j);
                    }
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
