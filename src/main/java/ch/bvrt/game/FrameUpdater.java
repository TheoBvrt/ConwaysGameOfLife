package ch.bvrt.game;

import javafx.animation.AnimationTimer;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

public class FrameUpdater extends AnimationTimer {
        final private int[][] gameTab;
        final private GraphicsContext gc;
        private final int cellSize;
        public FrameUpdater (int[][] gameTab, GraphicsContext gc, int cellSize) {
            this.gameTab = gameTab;
            this.gc = gc;
            this.cellSize = cellSize;
        }

        @Override
        public void handle(long now) {
            frameUpdate(gameTab, gc);
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
}
