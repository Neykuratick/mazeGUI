import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MyFrame extends JFrame implements ActionListener {

    JButton upB;
    JButton downB;
    JButton leftB;
    JButton rightB;
    JButton saveB;
    JButton generateWorld;
    public static String room;
    String config;
    String configPath = "src/files/config.ini";
    String levelPath = "src/files/generatedlvl.dat";
    public String stats;
    JFrame canvas;
    public static JTextArea textArea;
    public static JTextArea textStats;
    JTextArea keysInput;
    int charE;
    int keysAmount;
    int tresAmount;

    int columns = 12;
    int lines = 8;

    int playerChar;
    int playerY;
    int playerX;
    public static int[] playerStats = new int[4]; // 0 keys, 1 treasures, 2 moves, 3 won\lose (-1 to 1)


    MyFrame() throws IOException {

        File f = new File(configPath);
        if(!f.exists()) {
            FileHandler.createConfig(configPath);
            config = Methods.scanFile(configPath);
        }

        if (Methods.scanFile(levelPath).equals("nul")) {
            System.out.println("Error");
            FileHandler.writer(config, configPath, levelPath);
            room = Methods.scanFile(levelPath);
        } else { room = Methods.scanFile(levelPath); }


        config = Methods.scanFile(configPath);
        playerStats[2] = FileHandler.readConfig(configPath, 3);

        System.out.println(room);

        if (!room.equals("nul")) {
            playerY = Methods.findSpecificChar(room, columns, lines, '*')[0];
            playerX = Methods.findSpecificChar(room, columns, lines, '*')[1];
            playerChar = Methods.findSpecificChar(room, columns, lines, '*')[2];
            charE = Methods.findSpecificChar(room, columns, lines, 'E')[2];
            try {playerStats[2] = FileHandler.readConfig(configPath, 5);}
            catch (IOException ioException) {ioException.printStackTrace();}
        } else {
            System.out.println("Error");
        }

        // assigning stats
        keysAmount = FileHandler.readConfig(configPath, 1);
        tresAmount = FileHandler.readConfig(configPath, 2);
        stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);

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

        textStats = new JTextArea(stats);
        textStats.setEditable(false);
        textStats.setFont(new Font("JetBrainsMono", Font.PLAIN, 15));
        textStats.setColumns(columns);


        canvas.add(textStats);
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
            FileHandler.overrideConfig(config, configPath);
        }

        if (e.getSource() == generateWorld) {
            FileHandler.writer(config, configPath, levelPath);
            try {
                room = Methods.scanFile(levelPath);
            } catch (FileNotFoundException fileNotFoundException) {
                System.out.println("Not found");
            }

            playerY = Methods.findSpecificChar(room, columns, lines, '*')[0];
            playerX = Methods.findSpecificChar(room, columns, lines, '*')[1];
            playerChar = Methods.findSpecificChar(room, columns, lines, '*')[2];
            charE = Methods.findSpecificChar(room, columns, lines, 'E')[2];
            playerStats[0] = 0; playerStats[1] = 0; playerStats[3] = 0;

            try {playerStats[2] = FileHandler.readConfig(configPath, 5);}
            catch (IOException ioException) {ioException.printStackTrace();}

            System.out.println(room);
            stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);
            textStats.setText(stats);
            textArea.setText(room);
        }

        if (e.getSource() == upB && playerStats[3] == 0 && playerStats[2] > 0) {

            if (playerY > 0) {
                int newPlayerY = playerY - 1;
                int newPlayerChar = newPlayerY * (columns + 1) + playerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerY = newPlayerY;
                    playerChar = playerY * (columns + 1) + playerX;

                    try {PlayerController.cellHandler(room.charAt(playerChar), playerStats, configPath);}
                    catch (IOException ioException) {ioException.printStackTrace();}

                    if (playerStats[3] == 0 && Methods.canPlaceAsterics(room.charAt(newPlayerChar))) {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    }

                }  else { System.out.println("You can't"); }
            }

            stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);
            textStats.setText(stats);
            textArea.setText(room);
        }

        if (e.getSource() == downB && playerStats[3] == 0 && playerStats[2] > 0) {

            if (playerY < lines - 1) {
                int newPlayerY = playerY + 1;
                int newPlayerChar = newPlayerY * (columns + 1) + playerX;

                if (room.charAt(newPlayerChar) != '#') {
                    if (room.charAt(playerChar) != 'E') {
                        room = Methods.replaceChar(room, '.', playerChar);
                    }
                    playerY = newPlayerY;
                    playerChar = playerY * (columns + 1) + playerX;

                    try {PlayerController.cellHandler(room.charAt(playerChar), playerStats, configPath);}
                    catch (IOException ioException) {ioException.printStackTrace();}

                    if (playerStats[3] == 0 && Methods.canPlaceAsterics(room.charAt(newPlayerChar))) {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    }

                } else { System.out.println("You can't"); }
            }

            stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);
            textStats.setText(stats);
            textArea.setText(room);
        }

        if (e.getSource() == leftB && playerStats[3] == 0 && playerStats[2] > 0) {

            if (playerX > 1) {
                int newPlayerX = playerX - 1;
                int newPlayerChar = playerY * (columns + 1) + newPlayerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerX = newPlayerX;
                    playerChar = playerY * (columns + 1) + playerX;

                    try {PlayerController.cellHandler(room.charAt(playerChar), playerStats, configPath);}
                    catch (IOException ioException) {ioException.printStackTrace();}

                    if (playerStats[3] == 0 && Methods.canPlaceAsterics(room.charAt(newPlayerChar))) {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    }
                } else { System.out.println("You can't"); }
            }

            stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);
            textStats.setText(stats);
            textArea.setText(room);
        }

        if (e.getSource() == rightB && playerStats[3] == 0 && playerStats[2] > 0) {

            if (playerX < columns - 1) {
                int newPlayerX = playerX + 1;
                int newPlayerChar = playerY * (columns + 1) + newPlayerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerX = newPlayerX;
                    playerChar = playerY * (columns + 1) + playerX;

                    try {PlayerController.cellHandler(room.charAt(playerChar), playerStats, configPath);}
                    catch (IOException ioException) {ioException.printStackTrace();}

                    if (playerStats[3] == 0 && Methods.canPlaceAsterics(room.charAt(newPlayerChar))) {
                        room = Methods.replaceChar(room, '*', playerChar);
                        System.out.println(room);
                    }
                } else { System.out.println("You can't"); }
            }

            if (playerStats[3] != -1 && room.charAt(playerChar) == 'K') {
                System.out.println("You found a key!");
            }

            stats = PlayerController.getStatistics(playerStats[2], keysAmount, playerStats[0], playerStats[1]);
            textStats.setText(stats);
            textArea.setText(room);
        }
    }
}
