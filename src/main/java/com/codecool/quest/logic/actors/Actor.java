package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.ItemButton;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;
    private int sword = 0;
    private int key = 0;
    private int diamond = 0;
    private int door = 0;
    private int skeleton = 0;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
        if (nextCell.getType().equals(CellType.WALL) || nextCell.getType().equals(CellType.SKELETON)) {
        } else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
//        item position on map
        itemPosition(dx, dy);
        System.out.println(cell.getX() + cell.getY());
    }

    public void setSword(int sword) {
        this.sword = sword;
    }

    public int getSword() {
        return sword;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
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

    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
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

    public void itemPosition(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);
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


}
