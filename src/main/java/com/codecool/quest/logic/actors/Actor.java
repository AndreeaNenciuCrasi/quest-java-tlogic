package com.codecool.quest.logic.actors;

import com.codecool.quest.logic.*;

public abstract class Actor implements Drawable {
    private Cell cell;
    private int playerHealth = 100;
    private int playerStrength = 5;
    private int sword = 0;
    private int key = 0;
    private int diamond = 0;
    private int door = 0;

    public Actor(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
    }

    public void move(int dx, int dy) {
        Cell nextCell = cell.getNeighbor(dx, dy);

        if (nextCell.getType().equals(CellType.WALL) ||
                nextCell.getType().equals(CellType.HOUSE) || nextCell.getType().equals(CellType.TREE1) ||
                nextCell.getActor() instanceof Skeleton || nextCell.getActor() instanceof Skeleton2 ||
                nextCell.getActor() instanceof Skeleton3) {
//            Stops player getting through this objects
        } else {
            cell.setActor(null);
            nextCell.setActor(this);
            cell = nextCell;
        }
//        item position on map
        itemPosition();
        findMonster(nextCell);
    }


    void findMonster(Cell nextCell) {
        if (nextCell.getActor() instanceof Skeleton) {
            playerHealth -= ((Skeleton) nextCell.getActor()).getStrength();
            ((Skeleton) nextCell.getActor()).setHealth(((Skeleton) nextCell.getActor()).getSkeletonHealth() - playerStrength);
            if (((Skeleton) nextCell.getActor()).getSkeletonHealth() < 1) {
                nextCell.setActor(null);
            }
        } else if (nextCell.getActor() instanceof Skeleton2) {
            playerHealth -= ((Skeleton2) nextCell.getActor()).getStrength();
            ((Skeleton2) nextCell.getActor()).setHealth(((Skeleton2) nextCell.getActor()).getSkeletonHealth() - playerStrength);
            if (((Skeleton2) nextCell.getActor()).getSkeletonHealth() < 1) {
                nextCell.setActor(null);
            }

        } else if (nextCell.getActor() instanceof Skeleton3) {
            playerHealth -= ((Skeleton3) nextCell.getActor()).getStrength();
            ((Skeleton3) nextCell.getActor()).setHealth(((Skeleton3) nextCell.getActor()).getSkeletonHealth() - playerStrength);
            if (((Skeleton3) nextCell.getActor()).getSkeletonHealth() < 1) {
                nextCell.setActor(null);
            }

        }

    }

    void hitMonster(Cell nextCell, int skeletonHitStrength, int skeletonHealth) {
        playerHealth -= skeletonHitStrength;
        ((Skeleton) nextCell.getActor()).setHealth(skeletonHealth - playerStrength);
        if (((Skeleton) nextCell.getActor()).getSkeletonHealth() < 1) {
            nextCell.setActor(null);
        }
    }


    public int getSword() {
        return sword;
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

    public int getPlayerHealth() {
        return playerHealth;
    }

    public void setPlayerHealth(int playerHealth) {
        this.playerHealth = playerHealth;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
        this.cell.setActor(this);
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

    public int getPlayerStrength() {
        return playerStrength;
    }

    public void itemPosition() {
        ItemButton button = new ItemButton(this);
        if (cell.getType().equals(CellType.HEART)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.DIAMOND)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.SWORD)) {
            button.getItem(cell);
            playerStrength += 10;
        } else if (cell.getType().equals(CellType.KEY)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.KEY1)) {
            button.getItem(cell);
        } else if (cell.getType().equals(CellType.CLOSED_DOOR) && door == 1) {
            cell.setType(CellType.OPEN_DOOR);
        } else if (cell.getType().equals(CellType.WHITE_CLOSED_DOOR) && key == 2) {
            cell.setType(CellType.WHITE_OPEN_DOOR);
        } else if (diamond == 10) {
            Message message = new Message("Winner");
            message.setMessage("You Win !!");
            message.setVisible(true);
        } else if (playerHealth <= 0) {
            Message message = new Message("Loser");
            message.setMessage("You Lose !!");
            message.setVisible(true);
        }
    }
}
