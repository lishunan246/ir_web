package room713;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

/**
 * Created by Li Shunan on 2015/6/20.
 */
public class ViewFile {
    static String path = System.getProperty("user.home") + "/ir_resource";

    public static String getTitle(String id) {
        String filename = path + "/Reuters/" + id + ".html";
        File file = new File(filename); //for ex foo.txt
        try {
            Scanner scanner = new Scanner(file);
            return scanner.nextLine();

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String getContent(String id) {
        String content = "";
        String filename = path + "/Reuters/" + id + ".html";
        File file = new File(filename); //for ex foo.txt
        try {
            Scanner scanner = new Scanner(file);
            scanner.nextLine();
            while (scanner.hasNextLine()) {
                content += scanner.nextLine();
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            return content;
        }
    }

    public static String readFile(String filename) {
        String content = null;
        File file = new File(filename); //for ex foo.txt
        try {
            FileReader reader = new FileReader(file);
            char[] chars = new char[(int) file.length()];
            reader.read(chars);
            content = new String(chars);
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content;
    }
}
