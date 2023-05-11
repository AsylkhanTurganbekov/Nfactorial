import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;

public class Bullet2 {
    private double x, y;
    private Image bulletImage;

    public Bullet2(double x, double y) {
        this.x = x;
        this.y = y;
        bulletImage = new ImageIcon("enemy_bullet.png").getImage();
    }

    public void move(String face) {
        if (face.equals("right"))
            x += 5;
        else if (face.equals("left"))
            x -= 5;
        else if (face.equals("up"))
            y -= 5;
        else
            y += 5;
    }

    public void draw(Graphics g) {
        g.drawImage(bulletImage, (int) x, (int) y, null);
    }

    public int getX() {
        return (int) x;
    }

    public int getY() {
        return (int) y;
    }
}
