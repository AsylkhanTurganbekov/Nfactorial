import java.util.*;
import java.awt.event.*;

import javax.sound.sampled.*;
import javax.swing.*;

import java.awt.*;

import javax.swing.Timer;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Game  extends JPanel implements ActionListener
{
    public brick br;
    public ImageIcon player1;
    public int player1X;
    public int player1Y;
    public boolean player1right = false;
    public boolean player1left = false;
    public boolean player1down = false;
    public boolean player1up = true;
    public int player1lives = 3;

    public boolean player1Shoot = false;
    public String bulletShootDir1 = "";

    public ImageIcon player2;
    public int player2X;
    public int player2Y;
    public boolean player2right = false;
    public boolean player2left = false;
    public boolean player2down = false;
    public boolean player2up = true;
    public int player2lives = 3;

    public boolean player2Shoot = false;
    public String bulletShootDir2 = "";

    public Timer timer;
    public int delay=8;

    public Player1Listener player1Listener;
    public Player2Listener player2Listener;

    public Bullet1 Bullet1 = null;
    public Bullet2 Bullet2 = null;
    public boolean play = true;
    public int index;
    private Clip backgroundMusic;


    public Game(int index) {
        this.index = index;
        br = new brick(index);
        Random random = new Random();
        do {
            player1X = random.nextInt(540);
            player1Y = random.nextInt(490);
            player1X = player1X - player1X%50;
            player1Y = player1Y - player1Y%50;
        } while (br.checkWall(player1X, player1Y));

        do {
            player2X = random.nextInt(540);
            player2Y = random.nextInt(490);
            player2X = player2X - player2X%50;
            player2Y = player2Y - player2Y%50;
        } while (br.checkWall(player2X, player2Y) || (player2X == player1X && player2Y == player1Y));

        player1Listener = new Player1Listener();
        player2Listener = new Player2Listener();
        setFocusable(true);
        //addKeyListener(this);
        addKeyListener(player1Listener);
        addKeyListener(player2Listener);
        setFocusTraversalKeysEnabled(false);
        timer=new Timer(delay,this);
        timer.start();
    }

    public void paint(Graphics g)
    {
        g.setColor(Color.black);
        g.fillRect(0, 0, 650, 600);

        g.setColor(Color.DARK_GRAY);
        g.fillRect(660, 0, 140, 600);

        br.drawSolids(this, g);

        br.draw(this, g);

        if(play)
        {
            if(player1up)
                player1=new ImageIcon("tank.jpg");
            else if(player1down)
                player1=new ImageIcon("tank-3.jpg");
            else if(player1right)
                player1=new ImageIcon("tank-4.jpg");
            else if(player1left)
                player1=new ImageIcon("tank-2.jpg");

            player1.paintIcon(this, g, player1X, player1Y);

            if(player2up)
                player2=new ImageIcon("tank.jpg");
            else if(player2down)
                player2=new ImageIcon("tank-3.jpg");
            else if(player2right)
                player2=new ImageIcon("tank-4.jpg");
            else if(player2left)
                player2=new ImageIcon("tank-2.jpg");

            player2.paintIcon(this, g, player2X, player2Y);

            if(Bullet1 != null && player1Shoot)
            {
                if(bulletShootDir1.equals(""))
                {
                    if(player1up)
                    {
                        bulletShootDir1 = "up";
                    }
                    else if(player1down)
                    {
                        bulletShootDir1 = "down";
                    }
                    else if(player1right)
                    {
                        bulletShootDir1 = "right";
                    }
                    else if(player1left)
                    {
                        bulletShootDir1 = "left";
                    }
                }
                else
                {
                    Bullet1.move(bulletShootDir1);
                    Bullet1.draw(g);
                }


                if(new Rectangle(Bullet1.getX(), Bullet1.getY(), 10, 10)
                        .intersects(new Rectangle(player2X, player2Y, 50, 50)))
                {
                    player2lives -= 1;
                    Bullet1 = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                    try {
                        File musicFile = new File("Blast.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                }

                if (Bullet1 != null && (br.checkCollision(Bullet1.getX(), Bullet1.getY())
                        || br.checkSolidCollision(Bullet1.getX(), Bullet1.getY()))) {
                    Bullet1 = null;
                    player1Shoot = false;
                    bulletShootDir1 = "";
                    try {
                        File musicFile = new File("Touch.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                }


                if (Bullet1 != null) {
                    if (Bullet1.getY() < 1 || Bullet1.getY() > 580
                            || Bullet1.getX() < 1 || Bullet1.getX() > 630) {
                        Bullet1 = null;
                        player1Shoot = false;
                        bulletShootDir1 = "";
                    }
                }

            }

            if(Bullet2 != null && player2Shoot)
            {
                if(bulletShootDir2.equals(""))
                {
                    if(player2up)
                    {
                        bulletShootDir2 = "up";
                    }
                    else if(player2down)
                    {
                        bulletShootDir2 = "down";
                    }
                    else if(player2right)
                    {
                        bulletShootDir2 = "right";
                    }
                    else if(player2left)
                    {
                        bulletShootDir2 = "left";
                    }
                }
                else
                {
                    Bullet2.move(bulletShootDir2);
                    Bullet2.draw(g);
                }


                if(new Rectangle(Bullet2.getX(), Bullet2.getY(), 10, 10)
                        .intersects(new Rectangle(player1X, player1Y, 50, 50)))
                {
                    player1lives -= 1;
                    Bullet2 = null;
                    player2Shoot = false;
                    bulletShootDir2 = "";
                    try {
                        File musicFile = new File("Blast.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                }

                if (Bullet2 != null && (br.checkCollision(Bullet2.getX(), Bullet2.getY())
                        || br.checkSolidCollision(Bullet2.getX(), Bullet2.getY())))
                {
                    Bullet2 = null;
                    player2Shoot = false;
                    bulletShootDir2 = "";
                    try {
                        File musicFile = new File("Touch.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                }


                if (Bullet2 != null) {
                    if (Bullet2.getY() < 1 || Bullet2.getY() > 580
                            || Bullet2.getX() < 1 || Bullet2.getX() > 630) {
                        Bullet2 = null;
                        player2Shoot = false;
                        bulletShootDir2 = "";
                    }
                }
            }
        }

        g.setColor(Color.white);
        g.setFont(new Font("serif",Font.BOLD, 15));

        g.drawString("Player 1", 670, 50);
        g.drawString("Player 2", 670, 100);

        BufferedImage heartImage = null;
        try {
            heartImage = ImageIO.read(new File("heart.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        int heartX = 670;
        int heartY = 60;

        int heartSize = 25;
        for (int i = 0; i < player1lives; i++) {
            drawImage(g, heartImage, heartX, heartY, heartSize);
            heartX += heartSize + 10;
        }

        heartX = 670;
        heartY = 110;
        for (int i = 0; i < player2lives; i++) {
            drawImage(g, heartImage, heartX, heartY, heartSize);
            heartX += heartSize + 10;
        }

        if(player1lives == 0)
        {
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD, 60));
            g.drawString(" Tank 2 Won", 180,380);
            play = false;
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("(Press B to Restart)", 230,430);
        }
        else if(player2lives == 0)
        {
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD, 60));
            g.drawString(" Tank 1 Won", 180,380);
            g.setColor(Color.white);
            g.setFont(new Font("serif",Font.BOLD, 30));
            g.drawString("(Press B to Restart)", 230,430);
            play = false;
        }
        g.dispose();
    }
    private void drawImage(Graphics g, BufferedImage image, int x, int y, int size) {
        if (image != null) {
            g.drawImage(image, x, y, size, size, null);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        timer.start();

        repaint();
    }

    public class Player1Listener implements KeyListener
    {
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_B && (player1lives == 0 || player2lives == 0))
            {
                br = new brick(index);
                player1right = false;
                player1left = false;
                player1down = false;
                player1up = true;

                player2right = false;
                player2left = false;
                player2down = false;
                player2up = true;

                player1lives = 3;
                player2lives = 3;
                play = true;

                Random random = new Random();
                do {
                    player1X = random.nextInt(540);
                    player1Y = random.nextInt(490);
                    player1X = player1X - player1X%50;
                    player1Y = player1Y - player1Y%50;
                } while (br.checkWall(player1X, player1Y));

                do {
                    player2X = random.nextInt(540);
                    player2Y = random.nextInt(490);
                    player2X = player2X - player2X%50;
                    player2Y = player2Y - player2Y%50;
                } while (br.checkWall(player2X, player2Y) || (player2X == player1X && player2Y == player1Y));

                repaint();
            }
            if(e.getKeyCode()== KeyEvent.VK_Q)
            {
                if(!player1Shoot)
                {
                    try {
                        File musicFile = new File("Shot.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                    if(player1up)
                    {
                        Bullet1 = new Bullet1(player1X + 20, player1Y);
                    }
                    else if(player1down)
                    {
                        Bullet1 = new Bullet1(player1X + 20, player1Y + 40);
                    }
                    else if(player1right)
                    {
                        Bullet1 = new Bullet1(player1X + 40, player1Y + 20);
                    }
                    else if(player1left)
                    {
                        Bullet1 = new Bullet1(player1X, player1Y + 20);
                    }

                    player1Shoot = true;
                }
            }
            if(e.getKeyCode()== KeyEvent.VK_W)
            {
                player1right = false;
                player1left = false;
                player1down = false;
                player1up = true;

                if(!(player1Y < 10) && !br.checkWall(player1X, player1Y-10) && ((player1X-player2X<-40 || player1X-player2X>40) || player1Y!=player2Y+50))
                    player1Y-=10;

            }
            if(e.getKeyCode()== KeyEvent.VK_A)
            {
                player1right = false;
                player1left = true;
                player1down = false;
                player1up = false;

                if(!(player1X < 10) && !br.checkWall(player1X-10, player1Y) && ((player1Y-player2Y<-40 || player1Y-player2Y>40) || player1X!=player2X+50))
                    player1X-=10;
            }
            if(e.getKeyCode()== KeyEvent.VK_S)
            {
                player1right = false;
                player1left = false;
                player1down = true;
                player1up = false;

                if(!(player1Y > 540) && !br.checkWall(player1X, player1Y+10) && ((player1X-player2X<-40 || player1X-player2X>40) || player1Y!=player2Y-50))
                    player1Y+=10;
            }
            if(e.getKeyCode()== KeyEvent.VK_D)
            {
                player1right = true;
                player1left = false;
                player1down = false;
                player1up = false;

                if(!(player1X > 590) && !br.checkWall(player1X+10, player1Y) && ((player1Y-player2Y<-40 || player1Y-player2Y>40) || player1X!=player2X-50))
                    player1X+=10;
            }
        }
    }

    public class Player2Listener implements KeyListener
    {
        public void keyTyped(KeyEvent e) {}
        public void keyReleased(KeyEvent e) {}
        public void keyPressed(KeyEvent e) {
            if(e.getKeyCode()== KeyEvent.VK_M)
            {
                if(!player2Shoot)
                {
                    try {
                        File musicFile = new File("Shot.wav");
                        AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
                        backgroundMusic = AudioSystem.getClip();
                        backgroundMusic.open(audioInputStream);
                        backgroundMusic.start();
                    } catch (UnsupportedAudioFileException | IOException | LineUnavailableException ee) {
                        ee.printStackTrace();
                    }
                    if(player2up)
                    {
                        Bullet2 = new Bullet2(player2X + 20, player2Y);
                    }
                    else if(player2down)
                    {
                        Bullet2 = new Bullet2(player2X + 20, player2Y + 40);
                    }
                    else if(player2right)
                    {
                        Bullet2 = new Bullet2(player2X + 40, player2Y + 20);
                    }
                    else if(player2left)
                    {
                        Bullet2 = new Bullet2(player2X, player2Y + 20);
                    }

                    player2Shoot = true;
                }
            }
            if(e.getKeyCode()== KeyEvent.VK_UP)
            {
                player2right = false;
                player2left = false;
                player2down = false;
                player2up = true;

                if(!(player2Y < 10) && !br.checkWall(player2X, player2Y-10) && ((player1X-player2X<-40 || player1X-player2X>40) || player2Y!=player1Y+50))
                    player2Y-=10;
            }
            if(e.getKeyCode()== KeyEvent.VK_LEFT)
            {
                player2right = false;
                player2left = true;
                player2down = false;
                player2up = false;

                if(!(player2X < 10) && !br.checkWall(player2X-10, player2Y) && ((player1Y-player2Y<-40 || player1Y-player2Y>40) || player2X!=player1X+50))
                    player2X-=10;
            }
            if(e.getKeyCode()== KeyEvent.VK_DOWN)
            {
                player2right = false;
                player2left = false;
                player2down = true;
                player2up = false;

                if(!(player2Y > 540) && !br.checkWall(player2X, player2Y+10) && ((player1X-player2X<-40 || player1X-player2X>40) || player2Y!=player1Y-50))
                    player2Y+=10;
            }
            if(e.getKeyCode()== KeyEvent.VK_RIGHT)
            {
                player2right = true;
                player2left = false;
                player2down = false;
                player2up = false;

                if(!(player2X > 590) && !br.checkWall(player2X+10, player2Y) && !br.checkWall(player2X-10, player2Y) && ((player1Y-player2Y<-40 || player1Y-player2Y>40) || player2X!=player1X-50))
                    player2X+=10;
            }
        }
    }

}