import javax.print.attribute.standard.Media;
import javax.sound.sampled.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;


public class MainPage extends JFrame implements ActionListener {

    private JButton[] mapButtons;
    private JButton exitButton;
    private Clip backgroundMusic;

    public MainPage() {
        setTitle("Main Page");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Create a panel with a background image
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Load the background image
                ImageIcon backgroundImage = new ImageIcon("HD-wallpaper-anime-girls-clouds-winter-tank-girls-und-panzer-anime.jpg");
                // Draw the image at the panel's bounds
                g.drawImage(backgroundImage.getImage(), 0, 0, getWidth(), getHeight(), null);
            }
        };
        backgroundPanel.setLayout(new BorderLayout());
        add(backgroundPanel);

        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setOpaque(false);
        backgroundPanel.add(topPanel, BorderLayout.NORTH);

        JLabel titleLabel = new JLabel("Танки на двоих");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 32));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        topPanel.add(Box.createVerticalStrut(100)); // Adjust the height as desired
        topPanel.add(titleLabel, BorderLayout.SOUTH);

        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setOpaque(false);
        backgroundPanel.add(centerPanel, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setOpaque(false);
        centerPanel.add(buttonPanel, new GridBagConstraints());

        mapButtons = new JButton[4];
        for (int i = 0; i < 4; i++) {
            String buttonText = "Map " + (i + 1);
            String actionCommand = String.valueOf(i + 1);
            mapButtons[i] = createButton(buttonText, actionCommand);
            mapButtons[i].addActionListener(this);
            buttonPanel.add(mapButtons[i]);
        }
        exitButton = createButton("Exit","exit");
        exitButton.addActionListener(this);
        buttonPanel.add(exitButton);

        setVisible(true);

        try {
            File musicFile = new File("this-is-war-loopable-95413.wav");
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(musicFile);
            backgroundMusic = AudioSystem.getClip();
            backgroundMusic.open(audioInputStream);
            backgroundMusic.loop(Clip.LOOP_CONTINUOUSLY);
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    private JButton createButton(String text, String actionCommand) {
        JButton button = new JButton(text);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.PLAIN, 18));
        button.setActionCommand(actionCommand);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.red);
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setForeground(Color.WHITE);
            }
        });

        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == exitButton) {
            backgroundMusic.stop();
            dispose();
        } else {
            for (int i = 0; i < mapButtons.length; i++) {
                if (e.getSource() == mapButtons[i]) {
                    dispose();
                    final int mapIndex = i;
                    SwingUtilities.invokeLater(new Runnable() {
                        public void run() {
                            try {
                                backgroundMusic.stop();
                                Main.main(new String[0], mapIndex);
                            } catch (IOException ex) {
                                throw new RuntimeException(ex);
                            }
                        }
                    });
                    break;
                }
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainPage();
            }
        });
    }
}
