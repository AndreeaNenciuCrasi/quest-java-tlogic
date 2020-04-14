package com.codecool.quest.logic;

public enum CellType {
    EMPTY("empty"),
    FLOOR("floor"),
    WALL("wall"),
    SWORD("sword"),
    KEY("key"),
    DIAMOND("diamond"),
    HEART("heart"),
    OPEN_DOOR("openDoor"),
    CLOSED_DOOR("closedDoor"),
    CROSS_DOOR("crossDoor"),
    SKELETON("skeleton"),
    TREE1("tree1"),
    WATER1("water1"),
    HOUSE("house"),
    SKELETON2("skeleton2"),
    SKELETON3("skeleton3");

    private final String tileName;

    CellType(String tileName) {
        this.tileName = tileName;
    }

    public String getTileName() {
        return tileName;
    }
}
