import org.w3c.dom.ls.LSOutput;

import javax.swing.*;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class MyFrame extends JFrame implements ActionListener {

    JButton upB;
    JButton downB;
    JButton leftB;
    JButton rightB;
    JButton saveB;
    JButton generateWorld;
    String room;
    String config;
    String confipPath = "src/config.ini";
    String levelPath = "src/generatedlvl.dat";
    JFrame canvas;
    JTextArea textArea;
    JTextArea keysInput;
    int charE;

    int columns = 12;
    int lines = 8;

    int playerChar;
    int playerY;
    int playerX;
    int[] playerStats = new int[3]; // keys, tres, moves


    MyFrame() throws IOException {

        File f = new File(confipPath);
        if(!f.exists()) {
            FileHandler.createConfig();
            config = Methods.scanFile(confipPath);
        }

        if (Methods.scanFile(levelPath).equals("nul")) {
            System.out.println("Error");
            FileHandler.writer(config, confipPath);
            room = Methods.scanFile(levelPath);
        } else { room = Methods.scanFile(levelPath); }


        config = Methods.scanFile(confipPath);
        playerStats[2] = Methods.readConfig(confipPath, 3);

        System.out.println(room);

        if (!room.equals("nul")) {
            playerY = Methods.findPlayerChar(room, columns, lines, '*')[0];
            playerX = Methods.findPlayerChar(room, columns, lines, '*')[1];
            playerChar = Methods.findPlayerChar(room, columns, lines, '*')[2];
        } else {
            System.out.println("Error");
        }

        // frame constants
        int canvasWidth = 600; int canvasHeight = 600;
        String canvasBackgroundColor = "#ede8e8";

        // setting main frame window
        canvas = new JFrame("Maze");
        canvas.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        canvas.setSize(canvasWidth, canvasHeight);
        canvas.setResizable(false);
        canvas.getContentPane().setBackground(Color.decode(canvasBackgroundColor));
        // canvas.setLayout(new BorderLayout());
        canvas.setLayout(new GridLayout(3, 1)); // this is the least crappy one

        // text
        JLabel logo = new JLabel("Super Cool maze!", JLabel.CENTER);
        logo.setVerticalAlignment(JLabel.TOP);
        logo.setFont(new Font("Roboto", Font.BOLD, 25));

        // buttons
        generateWorld = new JButton("Generate Level");
        generateWorld.setBounds(400, 300, 50, 50);
        generateWorld.addActionListener(this);

        saveB = new JButton("Save Config");
        saveB.setBounds(100, 100, 50, 50);
        saveB.addActionListener(this);

        upB = new JButton("Up");
        upB.setBounds(100, 100, 50, 50);
        upB.addActionListener(this);


        downB = new JButton("Down");
        downB.setBounds(150, 100, 50, 50);
        downB.addActionListener(this);


        leftB = new JButton("Left");
        leftB.setBounds(250, 100, 50, 50);
        leftB.addActionListener(this);


        rightB = new JButton("Right");
        rightB.setBounds(300, 100, 50, 50);
        rightB.addActionListener(this);

        // textAreas
        textArea = new JTextArea(room);
        textArea.setEditable(false);
        textArea.setFont(new Font("JetBrainsMono", Font.PLAIN, 15));
        textArea.setColumns(columns);

        keysInput = new JTextArea(config);
        keysInput.setFont(new Font("JetBrainsMono", Font.PLAIN, 15));
        keysInput.setColumns(columns);


        canvas.add(logo);
        canvas.add(textArea);
        canvas.add(keysInput);
        canvas.add(saveB);
        canvas.add(upB);
        canvas.add(generateWorld);
        canvas.add(leftB);
        canvas.add(downB);
        canvas.add(rightB);
        canvas.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == saveB) {
            config = keysInput.getText();
            keysInput.setText(config);
            FileHandler.overrideConfig(config);
        }

        if (e.getSource() == generateWorld) {
            FileHandler.writer(config, confipPath);
            try {
                room = Methods.scanFile(levelPath);
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Not found");
            }

            playerY = Methods.findPlayerChar(room, columns, lines, '*')[0];
            playerX = Methods.findPlayerChar(room, columns, lines, '*')[1];
            playerChar = Methods.findPlayerChar(room, columns, lines, '*')[2];
            playerStats[0] = 0; playerStats[1] = 0;

            try {
                playerStats[2] = Methods.readConfig(confipPath, 3);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }

            System.out.println(room);
            textArea.setText(room);
        }

        if (e.getSource() == upB) {

            if (playerY > -1) {
                int newPlayerY = playerY - 1;
                int newPlayerChar = newPlayerY * (columns + 1) + playerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerY = newPlayerY;
                    playerChar = playerY * (columns + 1) + playerX;
                    Methods.cellHandler(room.charAt(playerChar), playerStats);

                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    } else {  System.out.println("You've won!"); }

                } else { System.out.println("You can't."); }
            }

            textArea.setText(room);
        }

        if (e.getSource() == downB) {

            if (playerY < lines - 1) {
                int newPlayerY = playerY + 1;
                int newPlayerChar = newPlayerY * (columns + 1) + playerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerY = newPlayerY;
                    playerChar = playerY * (columns + 1) + playerX;
                    Methods.cellHandler(room.charAt(playerChar), playerStats);

                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    } else {  System.out.println("You've won!"); }

                } else { System.out.println("You can't"); }
            }

            textArea.setText(room);
        }

        if (e.getSource() == leftB) {

            if (playerX > 1) {
                int newPlayerX = playerX - 1;
                int newPlayerChar = playerY * (columns + 1) + newPlayerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerX = newPlayerX;
                    playerChar = playerY * (columns + 1) + playerX;
                    Methods.cellHandler(room.charAt(playerChar), playerStats);

                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    } else {  System.out.println("You've won!"); }
                } else { System.out.println("You can't"); }
            }

            textArea.setText(room);
        }

        if (e.getSource() == rightB) {

            if (playerX < columns - 1) {
                int newPlayerX = playerX + 1;
                int newPlayerChar = playerY * (columns + 1) + newPlayerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerX = newPlayerX;
                    playerChar = playerY * (columns + 1) + playerX;
                    Methods.cellHandler(room.charAt(playerChar), playerStats);

                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    } else {  System.out.println("You've won!"); }
                } else { System.out.println("You can't"); }
            }

            if (room.charAt(playerChar) == 'K') {
                System.out.println("You found a key!");
            }

            textArea.setText(room);
        }


    }
}
