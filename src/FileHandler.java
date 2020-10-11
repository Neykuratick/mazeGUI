import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

    public static void writer(String config, String configPath) {

        try {
            File myObj = new File("src/generatedlvl.dat");
            if (myObj.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            System.out.println("Shit, error");
        }

        try {
            FileWriter myWriter = new FileWriter("src/generatedlvl.dat");
            String room = Methods.afterProccesing(12, 8, config, configPath);
            myWriter.write(room);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }

    public static void createConfig() {
        try {
            File myObj = new File("src/config.ini");
            if (myObj.createNewFile()) {
                System.out.println("File created");
            }
        } catch (IOException e) {
            System.out.println("Shit, error");
        }

        try {
            FileWriter myWriter = new FileWriter("src/config.ini");
            myWriter.write("Keys = 0\nTreasures = 0\nTraps = 0\nMoves = 100");
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }

    public static void overrideConfig(String str) {
        try {
            FileWriter myWriter = new FileWriter("src/config.ini");
            myWriter.write(str);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, everything's wrong");
        }
    }
}
