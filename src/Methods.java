import java.util.Random;

public class Methods {

    public static String removeLastChars(String str, int chars) {
        return str.substring(0, str.length() - chars);
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

        for (int i=0; i < lines-1; i++) {

            int freeCells = 0; while(freeCells == 0) { freeCells = rand.nextInt(10); }
            int freeCellsStartBound = (columns-1) - freeCells;
            int freeCellsStart = rand.nextInt(freeCellsStartBound);
            if (i == lines-2) {
                lastLine = true;
            }

            for (int j=0; j < columns+1; j++) {

                if (j == 0 && !lastLine) {
                    level += "#";
                }

                else if (j >= freeCellsStart && j < columns && freeCells > 0 && !lastLine) {
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
