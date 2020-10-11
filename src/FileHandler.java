import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class FileHandler {

    public static void writer() {

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
            String room = Methods.afterProccesing(12, 8);
            myWriter.write(room);
            myWriter.close();
        } catch (IOException e) {
            System.out.println("Shit, all's wrong");
        }
    }
}
