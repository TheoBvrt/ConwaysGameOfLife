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

        gameTab[9][12] = 1;
        gameTab[10][10] = 1;
        gameTab[10][12] = 1;
        gameTab[11][11] = 1;
        gameTab[11][12] = 1;

        gameTab[12][21] = 1;
        gameTab[13][20] = 1;
        gameTab[13][19] = 1;
        gameTab[14][20] = 1;
        gameTab[14][21] = 1;

        FrameUpdater frameUpdater = new FrameUpdater(gameTab, gc, cellSize);
        frameUpdater.start();
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            System.out.println(e);
        }
        for (int i = 0; i < 200; i++) {
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                System.out.println(e);
            }
            CopyTab(gameTab, tabCopy);
            GameRules(gameTab, tabCopy);
            CopyTab(tabCopy, gameTab);
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

    private void GameRules(int[][] gameTab, int[][] tabCopy) {
        for (int y = 0; y < gameTab.length; y++) {
            for (int x = 0; x < gameTab.length; x++) {
                final boolean b = y != 0 && x != 0 && y != gameTab.length - 1 && x != gameTab.length - 1;
                if (gameTab[y][x] == 1 && b) {
                    int neighbor = getNeighbor(gameTab, y, x);
                    if (neighbor < 2 || neighbor > 3) {
                        tabCopy[y][x] = 0;
                    }
                } else if (b) {
                    int neighbor = getNeighbor(gameTab, y, x);
                    if (neighbor == 3) {
                        tabCopy[y][x] = 1;
                    }
                }
            }
        }
    }

    private static int getNeighbor(int[][] gameTab, int y, int x) {
        int[][] neighbors = {
                {-1, -1}, {-1, 0}, {-1, 1},
                {0, -1}, {0, 1},
                {1, -1}, {1, 0}, {1, 1}
        };
        int neighbor = 0;

        for (int[] offset : neighbors) {
            int neighborY = y + offset[0];
            int neighborX = x + offset[1];

            if (gameTab[neighborY][neighborX] == 1) {
                neighbor++;
            }
        }
        return neighbor;
    }
}
