import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MyFrame extends JFrame implements ActionListener {

    JButton upB;
    JButton downB;
    JButton leftB;
    JButton rightB;
    String room;
    JFrame canvas;
    JTextArea textArea;

    int columns = 12;
    int lines = 8;

    int playerChar;
    int playerY;
    int playerX;


    MyFrame() throws FileNotFoundException {

        // room
        File file = new File("src/levels.dat");
        Scanner scanFile = new Scanner(file);
        room = "";
        while (scanFile.hasNextLine()) {
            room = room.concat(scanFile.nextLine() + "\n");
        }

        playerY = Methods.findPlayerChar(room, columns, lines, '*')[0];
        playerX = Methods.findPlayerChar(room, columns, lines, '*')[1];
        playerChar = Methods.findPlayerChar(room, columns, lines, '*')[2];

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

        // textArea
        textArea = new JTextArea(room);
        textArea.setEditable(false);
        textArea.setFont(new Font("Roboto", Font.PLAIN, 15));


        canvas.add(logo);
        canvas.add(textArea);
        canvas.add(upB);
        canvas.add(downB);
        canvas.add(leftB);
        canvas.add(rightB);
        canvas.setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == upB) {

            if (playerY > -1) {
                int newPlayerY = playerY - 1;
                int newPlayerChar = newPlayerY * (columns + 1) + playerX;

                if (room.charAt(newPlayerChar) != '#') {
                    room = Methods.replaceChar(room, '.', playerChar);
                    playerY = newPlayerY;
                    playerChar = playerY * (columns + 1) + playerX;
                    System.out.println("You moved to " + room.charAt(playerChar));
                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                    } else {  System.out.println("You've won!"); }

                } else { System.out.println("You can't"); }
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
                    System.out.println("You moved to " + room.charAt(playerChar));
                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
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
                    System.out.println("You moved to " + room.charAt(playerChar));
                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
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
                    System.out.println("You moved to " + room.charAt(playerChar));
                    if (room.charAt(newPlayerChar) != 'E') {
                        room = Methods.replaceChar(room, '*', playerChar);
                    } else {  System.out.println("You've won!"); }
                } else { System.out.println("You can't"); }
            }

            textArea.setText(room);
        }


    }
}
