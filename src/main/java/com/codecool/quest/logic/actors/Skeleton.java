package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton extends Actor {

    private int health = 10;
    private int strength = 3;

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSkeletonHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    public Skeleton(Cell cell) {
        super(cell);
    }

    @Override
    public String getTileName() {
        return "skeleton";
    }
}
