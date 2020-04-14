package com.codecool.quest.logic;

import com.codecool.quest.logic.actors.Player;

import java.io.InputStream;
import java.util.Scanner;

public class MapLoader {
    public static GameMap loadMap(String mapFile, Player player) {
        InputStream is = MapLoader.class.getResourceAsStream("/" + mapFile);
        Scanner scanner = new Scanner(is);
        int width = scanner.nextInt();
        int height = scanner.nextInt();

        scanner.nextLine(); // empty line

        GameMap map = new GameMap(width, height, CellType.EMPTY);
        for (int y = 0; y < height; y++) {
            String line = scanner.nextLine();
            for (int x = 0; x < width; x++) {
                if (x < line.length()) {
                    Cell cell = map.getCell(x, y);
                    switch (line.charAt(x)) {
                        case ' ':
                            cell.setType(CellType.EMPTY);
                            break;
                        case '#':
                            cell.setType(CellType.WALL);
                            break;
                        case '.':
                            cell.setType(CellType.FLOOR);
                            break;
                        case 's':
                            cell.setType(CellType.SKELETON);
//                            new Skeleton(cell);
                            break;
                        case 'w':
                            cell.setType(CellType.SWORD);
                            break;
                        case 'k':
                            cell.setType(CellType.KEY);
                            break;
                        case 'd':
                            cell.setType(CellType.DIAMOND);
                            break;
                        case 'h':
                            cell.setType(CellType.HEART);
                            break;
                        case 'o':
                            cell.setType(CellType.OPEN_DOOR);
                            break;
                        case 'c':
                            cell.setType(CellType.CLOSED_DOOR);
                            break;
                        case 'r':
                            cell.setType(CellType.CROSS_DOOR);
                            break;
                        case 't':
                            cell.setType(CellType.TREE1);
                            break;
                        case 'i':
                            cell.setType(CellType.HOUSE);
                            break;
                        case 'a':
                            cell.setType(CellType.WATER1);
                            break;
                        case '@':
                            cell.setType(CellType.FLOOR);
                            if (player==null){
                                map.setPlayer(new Player(cell));
                            }else {
                                map.setPlayer(player);
                                player.setCell(cell);
                            }
                            break;
                        default:
                            throw new RuntimeException("Unrecognized character: '" + line.charAt(x) + "'");
                    }
                }
            }
        }
        System.out.println("map loaded,");
        return map;
    }

}
