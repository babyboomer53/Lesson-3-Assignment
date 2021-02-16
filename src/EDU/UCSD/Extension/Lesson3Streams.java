package EDU.UCSD.Extension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;


public class Lesson3Streams {
    private String filename = null;
    private String inputData = null;
    private String regex;

    /**
     * @param filename
     */
    public Lesson3Streams(String filename) {
        this.filename = filename;
        // The default pattern matches a 16-digit hexadecimal number that begins
        // with 8 leading zeros.
        this.regex = "[0]{8}[0-9a-f]{8}";
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

    public int getStringSize() throws NullPointerException {
        return this.inputData.length();
    }

    public long countTheOccurrencesUsingStream() {
        Pattern pattern = Pattern.compile(regex);
        List<String> words = Arrays.asList(inputData.split("\\b"));
        return words.stream()
                .filter(pattern.asPredicate())
                .count();
    }

    public long countTheOccurrencesUsingParallelStream() {
        Pattern pattern = Pattern.compile(regex);
        List<String> words = Arrays.asList(inputData.split("\\b"));
        return words.parallelStream()
                .filter(pattern.asPredicate())
                .count();
    }

    /**
     *
     */
    public void dumpTheContents() {
        System.out.println(inputData);
    }

    public void doubleDown() throws IOException {
        inputData = inputData + new String(Files.readAllBytes(Paths.get(filename)));
    }

    public static void main(String[] arguments) throws IOException {
        String fileName = null;
        String regex = "[0]{8}[0-9a-f]{8}";
        long count = 0, startTime = 0, endTime = 0, singleThreaded = 0, multiThreaded = 0;

        try {
            fileName = arguments[0];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.err.println("Oops! This program requires an argument.");
            arrayIndexOutOfBoundsException.printStackTrace();
        }

        Lesson3Streams lesson3Streams = new Lesson3Streams(fileName, regex);
        lesson3Streams.readTheFile();
        singleThreaded = endTime - startTime;
        System.out.println("Try 1:");
        System.out.printf("String size: %d%n", lesson3Streams.getStringSize());

        startTime = System.currentTimeMillis();
        count = lesson3Streams.countTheOccurrencesUsingStream();
        endTime = System.currentTimeMillis();
        singleThreaded = endTime - startTime;

        System.out.printf("Milliseconds using stream: %,.2f%n", (double) singleThreaded);

        startTime = System.currentTimeMillis();
        count = lesson3Streams.countTheOccurrencesUsingParallelStream();
        endTime = System.currentTimeMillis();
        multiThreaded = endTime - startTime;

        System.out.printf("Milliseconds using parallelStream: %,.2f%n", (double) multiThreaded);

        if (multiThreaded < singleThreaded) {
            System.out.printf("Results: ParallelStream was %,.2f milliseconds " +
                            "faster than the single-threaded stream.%n",
                    (double) (singleThreaded - multiThreaded));
        } else if (singleThreaded < multiThreaded) {
            System.out.printf("Results: stream was %,.2f milliseconds faster than the parallelStream.%n",
                    (double) (multiThreaded - singleThreaded));
        } else {
            System.out.println("Results: No difference.");
        }

        System.out.println("Doubling String size and trying again…");

        lesson3Streams.doubleDown();

        System.out.println("Try 2:");
        System.out.printf("String size: %d%n", lesson3Streams.getStringSize());

        startTime = System.currentTimeMillis();
        count = lesson3Streams.countTheOccurrencesUsingStream();
        endTime = System.currentTimeMillis();

        System.out.printf("Milliseconds using stream: %,.2f%n", (double) singleThreaded);

        startTime = System.currentTimeMillis();
        count = lesson3Streams.countTheOccurrencesUsingParallelStream();
        endTime = System.currentTimeMillis();
        multiThreaded = endTime - startTime;

        System.out.printf("Milliseconds using parallelStream: %,.2f%n", (double) multiThreaded);

        if (multiThreaded < singleThreaded) {
            System.out.printf("Results: ParallelStream was %,.2f milliseconds " +
                            "faster than the single-threaded stream.%n",
                    (double) (singleThreaded - multiThreaded));
        } else if (singleThreaded < multiThreaded) {
            System.out.printf("Results: stream was %,.2f milliseconds faster than the parallelStream.%n",
                    (double) (multiThreaded - singleThreaded));
        } else {
            System.out.println("Results: No difference.");
        }
    }
}
