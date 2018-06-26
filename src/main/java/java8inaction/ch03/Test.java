package java8inaction.ch03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Created by zhangyunjie on 2017/4/18.
 */
public class Test {

    public static final String FILENAME = "data/data.txt";

    public static String processFile() throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            return reader.readLine();
        }
    }

    public static String processFile(BufferedReaderProcessor p) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILENAME))) {
            return p.process(reader);
        }
    }

    public static void main(String[] args) throws IOException {
        String oneLine = processFile((BufferedReader br) -> br.readLine());
        String twoLine = processFile((BufferedReader br) -> br.readLine() + br.readLine());
        System.out.println(oneLine);
        System.out.println(twoLine);
    }
}