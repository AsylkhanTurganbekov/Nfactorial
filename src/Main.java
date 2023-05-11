import java.awt.Color;
import java.io.IOException;

import javax.swing.JFrame;


public class Main {

    public static void main(String[] args, int index) throws IOException {
        JFrame obj=new JFrame();
        Game gamePlay = new Game(index);

        obj.setBounds(10, 10, 800, 630);
        obj.setTitle("2 Player Tanks");
        obj.setResizable(false);

        obj.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        obj.add(gamePlay);
        obj.setVisible(true);

    }

}