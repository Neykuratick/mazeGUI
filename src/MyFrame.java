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


    MyFrame() throws FileNotFoundException {

        // room
        File file = new File("src/levels.dat");
        Scanner scanFile = new Scanner(file);
        room = "";
        while (scanFile.hasNextLine()) {
            room = room.concat(scanFile.nextLine() + "\n");
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
            System.out.println("Up");
            room = "lala";
            textArea.setText(room);
        }

        if (e.getSource() == downB) {
            System.out.println("down");
        }

        if (e.getSource() == leftB) {
            System.out.println("left");
        }

        if (e.getSource() == rightB) {
            System.out.println("right");
        }


    }
}
