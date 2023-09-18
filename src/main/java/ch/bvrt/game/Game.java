package ch.bvrt.game;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class Game {
    final int tabSize;
    final int cellSize;

    public Game(int windowSize) {
        tabSize = (windowSize / 20) - 1;
        cellSize = 20;
    }

    public void Run(GraphicsContext gc) {
        int[][] gameTab = TabFill();
        int[][] tabCopy = new int[gameTab.length][gameTab.length];

        gameTab[20][16] = 1;
        gameTab[21][15] = 1;
        gameTab[22][15] = 1;
        gameTab[22][16] = 1;
        gameTab[22][17] = 1;

        gameTab[25][16] = 1;
        gameTab[25][17] = 1;
        gameTab[26][15] = 1;
        gameTab[26][17] = 1;
        gameTab[27][17] = 1;

        frameUpdate(gameTab, gc);
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        for (int i = 0; i < 200; i++) {
            try {
                Thread.sleep(300);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            CopyTab(gameTab, tabCopy);
            GameRules(gameTab, tabCopy);
            CopyTab(tabCopy, gameTab);
            frameUpdate(gameTab, gc);
        }
    }

    private static void CopyTab(int[][] source, int[][] destination) {
        for (int i = 0; i < source.length; i++) {
            System.arraycopy(source[i], 0, destination[i], 0, source.length);
        }
    }

    private int[][] TabFill() {
        int[][] tabToReturn = new int[tabSize + 1][tabSize + 1];
        for (int x = 0; x < this.tabSize + 1; x++) {
            for (int y = 0; y < this.tabSize + 1; y++) {
                tabToReturn[x][y] = 0;
            }
        }
        return tabToReturn;
    }

    private void frameUpdate(int[][] gameTab, GraphicsContext gc) {
        int frameY = 0;
        int frameX = 0;

        gc.setFill(Color.RED);
        for (int[] ints : gameTab) {
            for (int gameTabY = 0; gameTabY < gameTab.length; gameTabY++) {
                if (ints[gameTabY] == 0) {
                    DrawCell(frameY, frameX, gc, Color.WHITE);
                } else {
                    DrawCell(frameY, frameX, gc, Color.BLACK);
                }
                frameX += cellSize;
            }
            frameY += cellSize;
            frameX = 0;
        }
    }

    private void DrawCell(int frameY, int frameX, GraphicsContext gc, Color color) {
        for (int y = 0; y < cellSize; y++) {
            for (int x = 0; x < cellSize; x++) {
                if (x == (cellSize - 1) || y == (cellSize - 1) || x == 0 || y == 0) {
                    gc.setFill(Color.BLACK);
                } else {
                    gc.setFill(color);
                }
                gc.fillRect(x + frameX, y + frameY, 1, 1);
            }
        }
    }

    private void GameRules(int[][] gameTab, int[][] tabCopy) {
        for (int y = 0; y < gameTab.length; y++) {
            for (int x = 0; x < gameTab.length; x++) {
                final boolean b = y != 0 && x != 0 && y != gameTab.length - 1 && x != gameTab.length - 1;
                if (gameTab[y][x] == 1 && b) {
                    int neighbor = getNeighbor(gameTab, y, x);
                    System.out.println(neighbor);
                    if (neighbor < 2 || neighbor > 3) {
                        tabCopy[y][x] = 0;
                    }
                } else if (b) {
                    int neighbor = getNeighbor(gameTab, y, x);
                    System.out.println(neighbor);
                    if (neighbor == 3) {
                        tabCopy[y][x] = 1;
                    }
                }
            }
        }
    }

    private static int getNeighbor(int[][] gameTab, int y, int x) {
        int[][] neighborsCoordonates = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        int neighbor = 0;

        for (int[] offset : neighborsCoordonates) {
            int neighborY = y + offset[0];
            int neighborX = x + offset[1];

            if (gameTab[neighborY][neighborX] == 1) {
                neighbor++;
            }
        }
        return neighbor;
    }
}
