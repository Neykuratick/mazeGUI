import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

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
}
