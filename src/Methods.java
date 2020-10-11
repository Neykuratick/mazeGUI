import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;
import java.util.Scanner;

public class Methods {

    public static int[] appendArray(int[] array, int arraySize, int number) {
        for (int i = 0; i < arraySize; i++) {
            if (array[i] == 0) {
                array[i] = number;
                break;
            }
        }
        return array;
    }

    public static int countingElements(int[] array, int arraySize) {
        int count = 0;
        for (int i = 0; i < arraySize; i++) {
            count++;
        }

        return count;
    }

    public static int countingChars(String str, char charr) {
        int count = 0;
        for (int i = 0; i < countStringChars(str); i++) {
            if (str.charAt(i) == charr) {
                count++;
            }
        }

        return count;
    }

    public static int countStringChars(String string) {
        int count = 0;

        // Counts each character except space
        for(int i = 0; i < string.length(); i++) {
            if(string.charAt(i) != ' ')
                count++;
        }

        return count;
    }

    public static int[] findSpecificChar(String room, int cols, int lines, char whatToFind) {

        int pos = 0;
        int[] result = new int[3];

        while (room.charAt(pos) != whatToFind) { pos++; }

        result[0] = pos/(cols+1); // line
        result[1] = pos - result[0]*(cols+1); // column
        result[2] = pos;

        return result;
    }

    public static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public static boolean checkIfCharExitsts(String str, char charr) {
        for (int i = 0; i < countStringChars(str); i++) {
            if (str.charAt(i) == charr) {
                return true;
            }
        }

        return false;
    }

    public static int findNearestChar(String str, int startPoint, char charr) {
        int charInt = 0;
        for (int i = startPoint; i < countStringChars(str); i++) {
            if (str.charAt(i) == charr) {
                charInt = i;
                break;
            }
        }

        return charInt;
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

        switch (row) {
            case 1:
                return keys;
            case 2:
                return tres;
            case 3:
                return traps;
            case 4:
                return difficulty;
            case 5:
                return moves;
            default:
                return keys;
        }

    }

    public static String scanFile(String filePath) throws FileNotFoundException {
        try {
            String room = "";
            File file = new File(filePath);
            Scanner scanFile = new Scanner(file);
            room = "";
            while (scanFile.hasNextLine()) {
                room = room.concat(scanFile.nextLine() + "\n");
            }
            return room;
        } catch(Exception e) {
            return  "nul";
        }
    }

}
