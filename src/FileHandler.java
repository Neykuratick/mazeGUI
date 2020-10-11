import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class FileHandler {

    public static void writer(String config, String configPath, String levelPath) {

        try {
            File myObj = new File(levelPath);
            if (myObj.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            System.out.println("Shit, error");
        }

        try {
            FileWriter myWriter = new FileWriter(levelPath);
            String room = Generate.world(12, 8, config, configPath);
            myWriter.write(room);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }

    public static void createConfig(String configPath) {
        try {
            File myObj = new File(configPath);
            if (myObj.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            System.out.println("Shit, error");
        }

        try {
            FileWriter myWriter = new FileWriter(configPath);
            myWriter.write("Keys = 0\nTreasures = 0\nTraps = 0\nDifficulty = 4\nMoves = 100");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }

    public static void overrideConfig(String str, String configPath) {
        try {
            FileWriter myWriter = new FileWriter(configPath);
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }

    public static int readConfig(String configPath, int row) throws IOException {
        Random rand = new Random();
        FileInputStream fis = new FileInputStream(configPath);
        Properties prop = new Properties();
        prop.load(fis);

        // collecting info
        String keysString = prop.getProperty("Keys");
        int keys = Integer.parseInt(keysString);

        String tresString = prop.getProperty("Treasures");
        int tres = Integer.parseInt(tresString);

        String trapsString = prop.getProperty("Traps");
        int traps = Integer.parseInt(trapsString);

        String movesString = prop.getProperty("Moves");
        int moves = Integer.parseInt(movesString);

        String difficultyString = prop.getProperty("Difficulty");
        int difficulty = Integer.parseInt(difficultyString);

        return switch (row) {
            case 1 -> keys;
            case 2 -> tres;
            case 3 -> traps;
            case 4 -> difficulty;
            case 5 -> moves;
            default -> keys;
        };

    }

}
