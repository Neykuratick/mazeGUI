import java.io.IOException;

public class PlayerController {

    public static int[] cellHandler(char charr, int[] playerStats, String configPath) throws IOException { // keys, tres, moves, is won
        // System.out.println("You moved to " + charr);

        int keys = FileHandler.readConfig(configPath, 1);
        MyFrame.playerStats[2] -= 1;

        if (charr == 'K') {
            playerStats[0] += 1;
            System.out.println(playerStats[0]);
        }

        if (charr == 'E' && playerStats[0] == keys) {
            MyFrame.room = "You Won!\n";
            System.out.println("You won!");
            playerStats[3] = 1;
        } else if (charr == 'E') {
            System.out.println("Come back to your tasks!");
        }
        return playerStats;
    }

}
