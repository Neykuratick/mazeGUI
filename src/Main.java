import java.io.FileNotFoundException;
import java.util.Random;


public class Main {
    public static void main(String[] args) throws FileNotFoundException {

        new MyFrame();

//        for (int i=0; i < 10; i++) {
//            System.out.println(i);
//        }

        for (int i = 0; i < 10; i++) {
            System.out.println(Methods.afterProccesing(12, 8));
            System.out.println("");
        }
    }
}
