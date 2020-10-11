import java.io.File;
import java.io.FileNotFoundException;
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

    public static int[] findPlayerChar(String room, int cols, int lines, char whatToFind) {

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

    public static String generaterator(int columns, int lines) {
        Random rand = new Random();

        // generating exit
        int charE = 0; while (charE == 0) { charE = rand.nextInt(columns); }
        String level = "";

        for(int i=0; i < columns; i++) {

            if (i != charE) {
                level += "#";
            } else {
                level += "E";
            }

            if (i == columns-1) {
                level = level.concat("\n");
            }
        }

        boolean lastLine = false;

        int levelMetaBoolCount = columns+1;

        boolean[] levelMeta;
        levelMeta = new boolean[levelMetaBoolCount];
        boolean[] level2Meta;
        level2Meta = new boolean[levelMetaBoolCount];
        int[] foundDoors;
        foundDoors = new int[levelMetaBoolCount];
        int door = 0;

        for (int i=0; i < lines-1; i++) {

            int freeCells = 0; while(freeCells == 0) { freeCells = rand.nextInt(columns-4); }
            int freeCellsStartBound = (columns-1) - freeCells;
            int freeCellsStart = rand.nextInt(freeCellsStartBound);

            // assigning cells to cells
            boolean isDoorPathExist = false;
            while (!isDoorPathExist) {

                if (i == 0) {
                    for (int k = 0; k < levelMetaBoolCount; k++) {
                        boolean isTaken = rand.nextBoolean();
                        levelMeta[k] = isTaken;
                    }

                    for (int k = 0; k < levelMetaBoolCount; k++) {
                        boolean isTaken = rand.nextBoolean();
                        level2Meta[k] = isTaken;
                    }
                }

                else {
                    for (int k = 0; k < levelMetaBoolCount; k++) {
                        levelMeta[k] = level2Meta[k];
                        boolean isTaken = rand.nextBoolean();
                        level2Meta[k] = isTaken;
                    }
                }

                // checking if matching
                for (int k = 0; k < levelMetaBoolCount; k++) {
                    if (levelMeta[k] == level2Meta[k]) {
                        appendArray(foundDoors, levelMetaBoolCount, k);
                        isDoorPathExist = true;
                    }
                }

            }

            if (i == lines-2) {
                lastLine = true;
            }

            // picking a door
            while (door == 0) {
                int doorsPick = 0;
                doorsPick = rand.nextInt(countingElements(foundDoors, levelMetaBoolCount));
                door = foundDoors[doorsPick];
            }

            for (int j=0; j < columns; j++) {

                if (j == columns-1 && !lastLine) {
                    if (i == 0) {
                        level += "#";
                    }
                    level += "\n";
                }

                if (j == 0 && !lastLine) {
                    level += "#";
                }

                else if (j >= freeCellsStart && j < columns && freeCells > 0 && !lastLine || j == door) {
                    level += ".";
                    freeCells -= 1;
                }

                else if (j == columns-1 && !lastLine) {
                    level += "#";
                }

                else if (lastLine && j != 0) {
                    level += '#';
                }

                else if (!lastLine) {
                    level += '#';
                }

            }
        }

        return level;

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

    public static String afterProccesing(int columns, int lines){
        Random rand = new Random();

        String room = generaterator(columns, lines);

        // finding Exit
        int cursor = 0;
        while (room.charAt(cursor) != 'E') { cursor++; }
        int charExit = cursor;
        int toReplace = columns+1 + charExit;
        room = replaceChar(room, '.', toReplace);

        // finding dead-ends
        String roomTemp = room;

        int charsToReplaceSize = countingChars(room, '.');
        int[] charsToReplace = new int[charsToReplaceSize];

        while (checkIfCharExitsts(roomTemp, '.')) {
            cursor = 0;
            while (roomTemp.charAt(cursor) != '.') { cursor++; }
            int charDot = cursor;

            int neededY = charDot / (columns + 1);
            int neededX = charDot - neededY * (columns + 1);
            int neededChar = neededY*(columns + 1) + neededX;

            if (neededY < 7 && neededY > 0 && neededX < columns && neededX > 0) {

                if (roomTemp.charAt(neededY * (columns + 1) + (neededX + 1)) == '#' &&
                        roomTemp.charAt(neededY * (columns + 1) + (neededX - 1)) == '#' &&
                        roomTemp.charAt((neededY + 1) * (columns + 1) + neededX) == '#' &&
                        roomTemp.charAt((neededY - 1) * (columns + 1) + neededX) == '#' ||
                        roomTemp.charAt((neededY - 1) * (columns + 1) + neededX) == 'E') {
                    appendArray(charsToReplace, charsToReplaceSize, neededChar);
                    roomTemp = replaceChar(roomTemp, '!', charDot);
                } else {
                    roomTemp = replaceChar(roomTemp, 'L', charDot);
                }
            } else { break; }
        }

        // fixing dead-ends
        for (int i = 0; i < charsToReplaceSize; i++) {
            if ( charsToReplace[i] != 0) {
                int nextCharInt = findNearestChar(roomTemp, charsToReplace[i], 'L');

                int neededY = charsToReplace[i] / (columns + 1); // cords of dead end
                int neededX = charsToReplace[i] - neededY * (columns + 1);
                int neededChar = neededY*(columns + 1) + neededX;

                int neededYNextChar = nextCharInt / (columns + 1); // final cords to make the route
                int neededXNextChar = nextCharInt - neededYNextChar * (columns + 1);
                int neededCharNextChar = neededYNextChar*(columns + 1) + neededX;

                while (true) {
                    neededChar = neededY*(columns + 1) + neededX;
                    room = replaceChar(room, '.', neededChar);

                    if (neededX < neededXNextChar) {
                        neededX++;
                    } else if (neededX > neededXNextChar) {
                        neededX--;
                    }

                    if (neededX == neededXNextChar) {
                        break;
                    }
                }
            }
        }

        // placing player
        int[] placesToPlace = new int[countStringChars(room)];
        int placeY;
        int placeX;

        for (int i = 0; i < countStringChars(room); i++) {
            if (room.charAt(i) == '.') {
                placeY = i / (columns + 1);;
                placeX = i - placeY * (columns + 1);
                if (placeY > 0 && placeX > 0 && placeX < columns-1 && i != 0) {
                    appendArray(placesToPlace, countStringChars(room), i);
                }
            }
        }

        int placePick = rand.nextInt(countStringChars(room));
        int place = placesToPlace[placePick];
        boolean placePlaced = false;

        while (!placePlaced) {
            if (place != 0) {
                room = replaceChar(room, '*', place);
                placePlaced = true;
            } else {
                placePick = rand.nextInt(countStringChars(room));
                place = placesToPlace[placePick];
            }
        }

        return room;
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
