import javax.swing.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Scanner;

public class PlayerController {
    public static String getStatistics(int moves, int keys, int keysCollected, int treasuresCollected) {
        int keysLeft = keys - keysCollected;
        String stats = "Your moves: " + moves + "\n" +
                "Keys left: " + keysLeft + "\n" +
                "Treasures colleted: " + treasuresCollected;
        return stats;
    }

    public static int[] cellHandler(char charr, int[] playerStats, String configPath) throws IOException { // keys, tres, moves, is won
        // System.out.println("You moved to " + charr);
        int keys = FileHandler.readConfig(configPath, 1);
        MyFrame.playerStats[2] -= 1;

        if (charr == 'K') {
            playerStats[0] += 1;
        }

        if (charr == 'T') {
            playerStats[1] += 1;
        }

        if (charr == 'E' && playerStats[0] == keys) {
            MyFrame.room = "You Won!\n";
          //  System.out.println("You won!");
            playerStats[3] = 1;
        } else if (charr == 'E') {
            JOptionPane.showMessageDialog(null, "Come back to your tasks!");
        }

        if (MyFrame.playerStats[2] == 0) {
            MyFrame.room = "Ran out of moves!\n";
            // System.out.println("Ran out of moves!");
            playerStats[3] = -1;
        }

        int trapsAmount = FileHandler.readConfig(configPath, 3);
        try (FileInputStream fis = new FileInputStream(MyFrame.trapsPath)) {
            Scanner sc = new Scanner(fis);
            for (int i = 0; i < trapsAmount; i++) {
                if (MyFrame.playerChar == sc.nextInt()) {
                    MyFrame.room = "JHONNY, IT'S A TRAP!\n";
                    //  System.out.println("It'S A TRAP!");
                    playerStats[3] = -1;
                }
            }
        }

        return playerStats;
    }

}
