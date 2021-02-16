package EDU.UCSD.Extension;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

/**
 * The  Lesson3Streams class contains methods for counting words in a text file.
 * By  default, Lesson3Streams counts the number of 16-digit hexadecimal numbers
 * in a file by searching for character strings matching the regular expression,
 * "[0]{8}[0-9a-f]{8}".  An example  of a sequence that would match this pattern
 * is "00000000afc9db01".
 * <p>
 * When  instantiated, objects of the Lesson3Streams class must pass an argument
 * to  the  constructor specifying the  name of  the input file. Optionally, the
 * constructor  will  accept a  regular expression as  the second argument. When
 * specified,  the  second argument will be  used instead of the default pattern
 * (see previous paragraph).
 * <p>
 * The Lesson3Streams class uses Java's Streams and stream pipelines to search a
 * text file and return the number of times a sequence of characters matches the
 * regular  expression pattern.  Lesson3Streams  provides this  function in  two
 * methods.  One method utilizes a sequential stream, while the other utilizes a
 * parallel   stream.   A  parallel   stream  can  utilize  the  multi-threading
 * capabilities  provided  by  computers containing  multiple  processor  cores;
 * typically resulting in enhanced performance.
 * <p>
 * This  program expects  a single argument containing the name of the text file
 * from  which  the data  will be read.  When the argument  is omitted, an error
 * message  and a  stack trace will be  displayed on the console and the program
 * exits immediately.
 * <p>
 * When  invoked,  Lesson3Streams processes the input  file twice – once using a
 * sequential  stream,  and again using a  parallel stream. Next, it doubles the
 * size  of the  data and repeats the two steps. Lesson3Streams will repeat this
 * process  until the parallel stream runs faster than the sequential stream, or
 * until it has completed ten iterations.
 */

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

    /**
     * @return
     * @throws NullPointerException
     */
    public int getStringSize() throws NullPointerException {
        return this.inputData.length();
    }

    /**
     * @return
     */
    public long countTheOccurrencesUsingStream() {
        Pattern pattern = Pattern.compile(regex);
        List<String> words = Arrays.asList(inputData.split("\\b"));
        return words.stream()
                .filter(pattern.asPredicate())
                .count();
    }

    /**
     * @return
     */
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
        long count = 0;
        long startTime = 0;
        long endTime = 0;
        long singleThreaded = 0;
        long multiThreaded = 0;

        try {
            fileName = arguments[0];
        } catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
            System.err.println("Oops! This program requires an argument.");
            arrayIndexOutOfBoundsException.printStackTrace();
        }

        Lesson3Streams lesson3Streams = new Lesson3Streams(fileName, regex);
        lesson3Streams.readTheFile();
        while (count < 10) {
            System.out.println("Try 1:");
            System.out.printf("String size: %d%n", lesson3Streams.getStringSize());

            startTime = System.currentTimeMillis();
            lesson3Streams.countTheOccurrencesUsingStream();
            endTime = System.currentTimeMillis();
            singleThreaded = endTime - startTime;

            System.out.printf("Milliseconds using stream: %,.2f%n", (double) singleThreaded);

            startTime = System.currentTimeMillis();
            lesson3Streams.countTheOccurrencesUsingParallelStream();
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
            lesson3Streams.countTheOccurrencesUsingStream();
            endTime = System.currentTimeMillis();

            System.out.printf("Milliseconds using stream: %,.2f%n", (double) singleThreaded);

            startTime = System.currentTimeMillis();
            lesson3Streams.countTheOccurrencesUsingParallelStream();
            endTime = System.currentTimeMillis();
            multiThreaded = endTime - startTime;

            System.out.printf("Milliseconds using parallelStream: %,.2f%n", (double) multiThreaded);

            if (multiThreaded < singleThreaded) {
                System.out.printf("Results: ParallelStream was %,.2f milliseconds " +
                                "faster than the single-threaded stream.%n",
                        (double) (singleThreaded - multiThreaded));
                break;
            } else if (singleThreaded < multiThreaded) {
                System.out.printf("Results: stream was %,.2f milliseconds faster than the parallelStream.%n",
                        (double) (multiThreaded - singleThreaded));
            } else {
                System.out.println("Results: No difference.");
            }
            count++;
        }
        System.out.println("All done!");
    }
}
