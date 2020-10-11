import java.util.Random;

public class Methods {

    public static int[] appendArray(int array[], int arraySize, int number) {
        for (int i = 0; i < arraySize; i++) {
            if (array[i] == 0) {
                array[i] = number;
                break;
            }
        }
        return array;
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

    public static int[] findPlayerChar(String room) {
        int chars = countStringChars(room);
        int pos = 0;
        int[] result = new int[3];


        for (int i = 0; i < chars; ++i) {
            if (room.charAt(i) == '*') {
                pos += i;
                break;
            }
        }

        result[0] = pos/14; // line
        result[1] = pos - result[0]*13; // column
        result[2] = pos;
        
        return result;
    }

    public static String replaceChar(String str, char ch, int index) {
        StringBuilder myString = new StringBuilder(str);
        myString.setCharAt(index, ch);
        return myString.toString();
    }

    public static void generaterator(int columns, int lines) {
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

        boolean levelMeta[];
        levelMeta = new boolean[levelMetaBoolCount];
        boolean level2Meta[];
        level2Meta = new boolean[levelMetaBoolCount];
        int foundDoors[];
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
            int doorPick = 0;
            while (doorPick == 0) {
                doorPick = rand.nextInt(levelMetaBoolCount);
            }

            System.out.println(doorPick);

            for (int j=0; j < columns+1; j++) {

                if (j == 0 && !lastLine) {
                    level += "#";
                }

                else if (j >= freeCellsStart && j < columns && freeCells > 0 && !lastLine || j == doorPick) {
                    level += ".";
                    freeCells -= 1;
                }

                else if (j == columns-1 && !lastLine) {
                    level += "#";
                }

                else if (j == columns && !lastLine) {
                    level += "\n";
                }

                else if (lastLine && j != 0) {
                    level += '#';
                }

                else if (!lastLine) {
                    level += '#';
                }

            }
        }

        System.out.println(level);

    }




}
