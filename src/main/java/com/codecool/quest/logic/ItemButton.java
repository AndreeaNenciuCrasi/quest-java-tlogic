package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Actor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;


public class ItemButton {

    private Actor actor;

    public ItemButton(Actor actor) {
        this.actor = actor;
    }

    public void getItem(Cell cell) {
        Stage stage = new Stage();
        Button btn = new Button();
        btn.setText("Pick up item");
        stage.centerOnScreen();
        stage.setScene(new Scene(btn, 125, 70));
        stage.show();
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            if (cell.getType() == CellType.DIAMOND) {
                actor.setDiamond(actor.getDiamond() + 1);
            }
            if (cell.getType() == CellType.HEART) {
                actor.setHealth(actor.getHealth() + 5);
            }
            if (cell.getType() == CellType.SWORD) {
                actor.setSword(actor.getSword() + 1);
            }
            if (cell.getType() == CellType.KEY) {
                actor.setKey(actor.getKey() + 1);
                actor.setDoor(actor.getDoor() + 1);
            }
            if (cell.getType() == CellType.KEY1) {
                actor.setKey(actor.getKey() + 1);
                actor.setDoor(actor.getDoor() + 1);
            }
            cell.setType(CellType.FLOOR);
            stage.close();
        });

    }


}
