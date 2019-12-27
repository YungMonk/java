import java.io.*;
import java.io.BufferedReader;
import java.io.IOException;

/**
 * BRRead
 */
public class BRRead {

    public static void main(String args[]) throws IOException {
        String c;

        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Input some string, press 'q' to exit.");

        do {
            c = br.readLine();
            System.out.println(c);
        } while (!c.equals("end"));
    }
}