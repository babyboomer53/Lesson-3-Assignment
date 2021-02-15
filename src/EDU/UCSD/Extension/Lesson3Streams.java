package EDU.UCSD.Extension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Lesson3Streams {
    private String filename = null;
    private String inputData = null;
    // The default pattern matches a 16-digit hexadecimal number that begins
    // with 8 leading zeros.
    private String regex = "[0]{8}[0-9a-f]{8}";

    /**
     * @param filename
     */
    public Lesson3Streams(String filename) {
        this.filename = filename;
    }

    /**
     * @param filename
     * @param regex
     */
    public Lesson3Streams(String filename, String regex) {
        this.filename = filename;
        this.regex = regex;
    }

    /**
     * @throws IOException
     */
    public void readTheFile() throws IOException {
        inputData = new String(Files.readAllBytes(Paths.get(filename)));
    }

    /**
     *
     */
    public void dumpTheContents() {
        System.out.println(inputData);
    }

    public static void main(String[] arguments) throws IOException {
        String fileName = null;
        try {
            fileName = arguments[0];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.err.println("Oops! This program requires an argument.");
            arrayIndexOutOfBoundsException.printStackTrace();
        }
        Lesson3Streams lesson3Streams = new Lesson3Streams(fileName);
        lesson3Streams.readTheFile();
        lesson3Streams.dumpTheContents();
    }
}
