import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        // room
        File file = new File("src/levels.dat");
        Scanner scanFile = new Scanner(file);
        String room = "";
        while (scanFile.hasNextLine()) {
            room = room.concat(scanFile.nextLine() + "\n");
        }


        // frame constants
        int canvasWidth = 600; int canvasHeight = 600;
        String canvasBackgroundColor = "#ede8e8";

        // setting main frame window
        JFrame canvas = new JFrame("Maze");
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
        JButton up = new JButton();
        up.setBounds(100, 100, 50, 50);


        JButton down = new JButton();
        down.setBounds(150, 100, 50, 50);


        JButton left = new JButton();
        left.setBounds(250, 100, 50, 50);


        JButton right = new JButton();
        right.setBounds(300, 100, 50, 50);

        // textArea
        JTextArea textArea = new JTextArea(room);
        textArea.setEditable(false);
        textArea.setFont(new Font("Roboto", Font.TRUETYPE_FONT, 15));


        canvas.add(logo);
        canvas.add(textArea);
        canvas.add(up);
        canvas.add(down);
        canvas.add(left);
        canvas.add(right);
        canvas.setVisible(true);
    }
}
