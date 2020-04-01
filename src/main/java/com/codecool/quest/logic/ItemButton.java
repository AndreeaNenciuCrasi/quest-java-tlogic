package com.codecool.quest.logic;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicBoolean;


public class ItemButton {

    public void getItem(Cell cell) {
        Stage stage = new Stage();
        Button btn = new Button();
        btn.setText("Pick up item");
        stage.centerOnScreen();
        stage.setScene(new Scene(btn, 50, 50));
        stage.show();
        btn.addEventHandler(MouseEvent.MOUSE_CLICKED, (e) -> {
            cell.setType(CellType.FLOOR);
            stage.close();
        });

    }


}
