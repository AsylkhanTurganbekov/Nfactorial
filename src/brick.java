import java.awt.Component;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.util.Random;

import javax.swing.ImageIcon;

public class brick {
    private int[][][] maps = {
            {
                    {50, 50, 50, 50, 100, 100, 100, 100, 150, 200, 200, 200, 250, 300, 300, 300, 300, 350, 350, 350, 350, 400, 400, 400, 400, 450, 450, 450, 450, 450, 500, 500, 500, 500, 500, 500, 550, 550, 550, 550},
                    {50, 150, 250, 300, 350, 550, 50, 250, 350, 550, 50, 150, 250, 350, 450, 550, 50, 350, 450, 550, 150, 150, 450, 550, 250, 50, 100, 150, 550, 250, 350, 450, 550, 50, 250, 350, 550, 50, 150, 250, 300, 350, 550},
                    {0, 0, 50, 100, 150, 200, 200, 250, 300, 350, 400, 400, 450},
                    {150, 350, 150, 500, 450, 300, 600, 400, 350, 200, 0, 200, 500}
            },
            {
                    {50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 550, 550, 550, 550, 500, 450, 400, 350, 300, 250, 200, 150, 100, 50, 50, 50, 50, 50, 100, 150, 200, 250, 300, 350, 400, 450, 500, 550, 550, 550, 550, 550},
                    {50, 50, 50, 50, 50, 100, 100, 100, 100, 150, 150, 150, 150, 200, 200, 200, 200, 250, 250, 250, 250, 300, 300, 300, 300, 350, 350, 350, 350, 400, 400, 400, 400, 450, 450, 450, 450, 450, 500, 500, 500, 500, 500, 550},
                    {0, 50, 100, 150, 200, 250, 300, 350, 400, 450},
                    {150, 350, 150, 500, 450, 300, 600, 400, 350, 200}
            },
            {
                    {50, 50, 100, 100, 150, 150, 200, 200, 250, 250, 300, 300, 350, 350, 400, 400, 450, 450, 500, 500, 550, 550},
                    {50, 350, 50, 550, 150, 450, 150, 550, 250, 350, 250, 550, 350, 450, 350, 550, 450, 550, 450, 550, 450, 550},
                    {0, 100, 200, 300, 400, 500,600},
                    {200, 200, 200, 200, 200, 200,200}
            },
            {
                    {50, 150, 250, 350, 450, 550, 50, 150, 250, 350, 450, 550, 50, 150, 250, 350, 450, 550, 50, 150, 250, 350, 450, 550, 50, 150, 250, 350, 450, 550},
                    {50, 50, 50, 50, 50, 50, 150, 150, 150, 150, 150, 150, 250, 250, 250, 250, 250, 250, 350, 350, 350, 350, 350, 350, 450, 450, 450, 450, 450, 450},
                    {0, 100, 200, 300, 400, 500,600},
                    {200, 200, 200, 200, 200, 200,200}
            },

    };
    private int[][] currentMap;
    private ImageIcon breakBrickImage;
    private ImageIcon solidBrickImage;

    public brick(int index) {
        breakBrickImage = new ImageIcon("dirt.jpg");
        solidBrickImage = new ImageIcon("sand.jpg");
        selectMap(index);
    }

    public void draw(Component c, Graphics g) {
        for (int i = 0; i < currentMap[0].length; i++) {
            int x = currentMap[0][i];
            int y = currentMap[1][i];
            breakBrickImage.paintIcon(c, g, x, y);
        }
    }

    public void drawSolids(Component c, Graphics g) {
        for (int i = 0; i < currentMap[2].length; i++) {
            int x = currentMap[2][i];
            int y = currentMap[3][i];
            solidBrickImage.paintIcon(c, g, x, y);
        }
    }


    public boolean checkCollision(int x, int y) {
        for (int i = 0; i < currentMap[0].length; i++) {
            int brickX = currentMap[0][i];
            int brickY = currentMap[1][i];
            if (new Rectangle(x, y, 10, 10).intersects(new Rectangle(brickX, brickY, 50, 50))) {
                currentMap[0][i] = -50;
                currentMap[1][i] = -50;
                return true;
            }
        }
        return false;
    }

    public boolean checkWall(int x, int y) {
        for (int i = 0; i < currentMap[0].length; i++) {
            int brickX = currentMap[0][i];
            int brickY = currentMap[1][i];
            if (new Rectangle(x, y, 50, 50).intersects(new Rectangle(brickX, brickY, 50, 50))) {
                return true;
            }
        }
        for (int i = 0; i < currentMap[2].length; i++) {
            int brickXX = currentMap[2][i];
            int brickYY = currentMap[3][i];
            if (new Rectangle(x, y, 50, 50).intersects(new Rectangle(brickXX, brickYY, 50, 50))) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSolidCollision(int x, int y) {
        boolean collided = false;
        for (int i = 0; i < currentMap[2].length; i++) {
            int brickX = currentMap[2][i];
            int brickY = currentMap[3][i];
            if (new Rectangle(x, y, 10, 10).intersects(new Rectangle(brickX, brickY, 50, 50))) {
                collided = true;
                break;
            }
        }
        return collided;
    }

    private void selectMap(int index) {
        currentMap = maps[index];
    }
}

