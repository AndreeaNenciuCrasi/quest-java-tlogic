package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;

public class Skeleton3 extends Actor {

    private int health = 25;
    private int strength = 15;

    public Skeleton3(Cell cell) {
        super(cell);
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getSkeletonHealth() {
        return health;
    }

    public int getStrength() {
        return strength;
    }

    @Override
    public String getTileName() {
        return "skeleton3";
    }
}
