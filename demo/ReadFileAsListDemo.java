/**
 * Created by EUJ346 on 5/11/2017.
 */
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ReadFileAsListDemo {
//    public static void main(String[] args) {
//        ReadFileAsListDemo demo = new ReadFileAsListDemo();
//        demo.readFileAsList();
//        System.out.println(args + "########################################################" + "/n");
//    }

    public static void main(String[] args) {
        ReadFileAsListDemo ReadFileAsListDemo = new ReadFileAsListDemo();
        String fileName = "/nlp.txt";

        try {
            URI uri = ReadFileAsListDemo.getClass().getResource(fileName).toURI();
            List<String> lines = Files.readAllLines(Paths.get(uri),
                    Charset.defaultCharset());

            for (String line : lines) {
                System.out.println(line);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}