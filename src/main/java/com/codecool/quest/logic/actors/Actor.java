package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.ItemButton;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 15;
    private int sword = 0;
    private int key = 0;
    private int diamond = 0;
    private int door = 0;
    private int skeleton = 10;
    private int skeleton2 = 0;
    private int skeleton3 = 0;


    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        System.out.println("CellX: " + cell.getX());
        System.out.println("CellY: " + cell.getY());
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.WALL) || nextCell.getType().equals(CellType.SKELETON) ||
                nextCell.getType().equals(CellType.SKELETON2) || nextCell.getType().equals(CellType.SKELETON3)) {
        } else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;

        }
        System.out.println(cell.getSkeleton2X());
//        item position on map
        itemPosition(dx, dy);


        findMonster(nextCell);
    }



    void findMonster(Cell nextCell) {
        if ((nextCell.getType().equals(CellType.SKELETON) || nextCell.getType().equals(CellType.SKELETON2) ||
                nextCell.getType().equals(CellType.SKELETON3)) &&
                sword == 0) {
            hitMonster(nextCell, 5);
        } else if ((nextCell.getType().equals(CellType.SKELETON) || nextCell.getType().equals(CellType.SKELETON2) ||
                nextCell.getType().equals(CellType.SKELETON3)) && sword == 1) {
            hitMonster(nextCell, 8);
        }

    }

    void hitMonster(Cell nextCell, int hitStrength) {
        health -= 2;
        skeleton -= hitStrength;
        System.out.println(nextCell.getType());
        if (skeleton > 0) {
        } else {
            nextCell.setType(CellType.FLOOR);
            skeleton = 10;
            System.out.println(nextCell.getType());
        }
    }



    void itemPosition(int dx, int dy) {
        ItemButton button = new ItemButton(this);

        if (cell.getType().equals(CellType.HEART)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.DIAMOND)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.SWORD)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.KEY)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.CLOSED_DOOR) && door == 1) {
            cell.setType(CellType.OPEN_DOOR);
        }
    }

    public int getSword() {
        return sword;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void setSword(int sword) {
        this.sword = sword;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getDiamond() {
        return diamond;
    }

    public void setDiamond(int diamond) {
        this.diamond = diamond;
    }

    public int getSkeleton() {
        return skeleton;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public Cell getCell() {
        return cell;
    }

    public int getX() {
        return cell.getX();
    }

    public int getY() {
        return cell.getY();
    }

    public int getDoor() {
        return door;
    }

    public void setDoor(int door) {
        this.door = door;
    }

}
