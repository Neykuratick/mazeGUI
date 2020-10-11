import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.Random;

public class Generate {
    public static String generators(int columns, int lines, String configPath) throws IOException {
        Random rand = new Random();
        int difficulty = FileHandler.readConfig(configPath, 4);
        if (difficulty > 10) { difficulty = 10; } else if (difficulty < 1) { difficulty = 1; }

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

            int freeCells = 0; while(freeCells == 0) { freeCells = rand.nextInt(columns-difficulty); }
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
                        Methods.appendArray(foundDoors, levelMetaBoolCount, k);
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
                doorsPick = rand.nextInt(Methods.countingElements(foundDoors, levelMetaBoolCount));
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

    public static String world(int columns, int lines, String config, String configPath) throws IOException {
        Random rand = new Random();

        String room = generators(columns, lines, configPath);

        // finding Exit
        int cursor = 0;
        while (room.charAt(cursor) != 'E') { cursor++; }
        int charExit = cursor;
        int toReplace = columns+1 + charExit;
        room = Methods.replaceChar(room, '.', toReplace);

        // finding dead-ends
        String roomTemp = room;

        int charsToReplaceSize = Methods.countingChars(room, '.');
        int[] charsToReplace = new int[charsToReplaceSize];

        while (Methods.checkIfCharExitsts(roomTemp, '.')) {
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
                    Methods.appendArray(charsToReplace, charsToReplaceSize, neededChar);
                    roomTemp = Methods.replaceChar(roomTemp, '!', charDot);
                } else {
                    roomTemp = Methods.replaceChar(roomTemp, 'L', charDot);
                }
            } else { break; }
        }

        // fixing dead-ends
        for (int i = 0; i < charsToReplaceSize; i++) {
            if ( charsToReplace[i] != 0) {
                int nextCharInt = Methods.findNearestChar(roomTemp, charsToReplace[i], 'L');

                int neededY = charsToReplace[i] / (columns + 1); // cords of dead end
                int neededX = charsToReplace[i] - neededY * (columns + 1);
                int neededChar = neededY*(columns + 1) + neededX;

                int neededYNextChar = nextCharInt / (columns + 1); // final cords to make the route
                int neededXNextChar = nextCharInt - neededYNextChar * (columns + 1);
                int neededCharNextChar = neededYNextChar*(columns + 1) + neededX;

                while (true) {
                    neededChar = neededY*(columns + 1) + neededX;
                    room = Methods.replaceChar(room, '.', neededChar);

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
        int[] placesToPlace = new int[Methods.countStringChars(room)];
        int placeY;
        int placeX;

        for (int i = 0; i < Methods.countStringChars(room); i++) {
            if (room.charAt(i) == '.') {
                placeY = i / (columns + 1);;
                placeX = i - placeY * (columns + 1);
                if (placeY > 0 && placeX > 0 && placeX < columns-1 && i != 0) {
                    Methods.appendArray(placesToPlace, Methods.countStringChars(room), i);
                }
            }
        }

        int placePick = rand.nextInt(Methods.countStringChars(room));
        int place = placesToPlace[placePick];
        boolean placePlaced = false;

        while (!placePlaced) {
            if (place != 0) {
                room = Methods.replaceChar(room, '*', place);
                placePlaced = true;
            } else {
                placePick = rand.nextInt(Methods.countStringChars(room));
                place = placesToPlace[placePick];
            }
        }
        room = keysAndTreasures(room, configPath);
        return room;
    }


    public static int[] traps;

    public static String keysAndTreasures(String room, String configPath) throws IOException {
        Random rand = new Random();
        FileInputStream fis = new FileInputStream(configPath);
        Properties prop = new Properties();
        prop.load(fis);

        // collecting info
        String keysString = prop.getProperty("Keys");
        int keys = Integer.parseInt(keysString);

        String tresString = prop.getProperty("Treasures");
        int treasures = Integer.parseInt(tresString);

        String trapsString = prop.getProperty("Traps");
        int trapsAmount = Integer.parseInt(trapsString);

        String movesString = prop.getProperty("Moves");
        int moves = Integer.parseInt(movesString);


        // spawning, defining free cells
        int charsInRoom = Methods.countStringChars(room);
        int freeCellsCount = 0;
        int[] freeCells;
        freeCells = new int[charsInRoom];

        for (int i = 0; i < charsInRoom; i++) {
            if (room.charAt(i) == '.') {
                Methods.appendArray(freeCells, charsInRoom, i);
                freeCellsCount++;
            }
        }

        // keys
        while(keys > 0 && freeCellsCount > 0) {
            int placeToSpawn = 0;
            while (placeToSpawn == 0) {
                placeToSpawn = freeCells[rand.nextInt(charsInRoom)];
            }
            if (room.charAt(placeToSpawn) == '.') {
                room = Methods.replaceChar(room, 'K', placeToSpawn);
                keys--;
                freeCellsCount--;
            }
        }

        // tres
        while(treasures > 0 && freeCellsCount > 0) {
            int placeToSpawn = 0;
            while (placeToSpawn == 0) {
                placeToSpawn = freeCells[rand.nextInt(charsInRoom)];
            }
            if (room.charAt(placeToSpawn) == '.') {
                room = Methods.replaceChar(room, 'T', placeToSpawn);
                treasures--;
                freeCellsCount--;
            }
        }

        // traps
        traps = new int[trapsAmount];
        int trapsAmountCounter = trapsAmount;

        while(trapsAmountCounter > 0 && freeCellsCount > 0) {
            int placeToSpawn = 0;
            while (placeToSpawn == 0) {
                placeToSpawn = freeCells[rand.nextInt(charsInRoom)];
            }
            if (room.charAt(placeToSpawn) == '.') {
                room = Methods.replaceChar(room, '.', placeToSpawn);
                Methods.appendArray(traps, trapsAmountCounter, placeToSpawn);
                trapsAmountCounter--;
                freeCellsCount--;
            }
        }
        FileHandler.createTraps(MyFrame.trapsPath, traps);
        return room;
    }

}
