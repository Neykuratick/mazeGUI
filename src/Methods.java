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

    public static int isThisIntInArray(int[] array, int arraySize, int number) {
        for (int i = 0; i < arraySize; i++) {
            if (array[i] == number) {
                return number;
            }
        }
        return -1;
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

    public static boolean canPlaceAsterics(char charr) {
        boolean result = false;
        if (charr == '.' || charr == 'K' || charr == 'T' || charr == 'X') {
            result = true;
        } else {
            result = false;
        }
        return result;
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
