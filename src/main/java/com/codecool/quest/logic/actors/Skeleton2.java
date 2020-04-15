package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton2 extends Actor {
    public Skeleton2(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton2";
    }
}
