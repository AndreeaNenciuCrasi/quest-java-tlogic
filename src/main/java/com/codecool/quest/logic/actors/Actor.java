package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.Cell;
import com.codecool.quest.logic.CellType;
import com.codecool.quest.logic.Drawable;
import com.codecool.quest.logic.ItemButton;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int health = 10;

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

    public void itemPosition(int dx, int dy) {
        ItemButton button = new ItemButton();

        if (cell.getType().equals(CellType.HEART)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.DIAMOND)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.SWORD)) {
            button.getItem(cell);

        } else if (cell.getType().equals(CellType.KEY)) {
            button.getItem(cell);
        }
    }


}
