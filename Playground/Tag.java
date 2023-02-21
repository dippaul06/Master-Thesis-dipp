package playground;

import magma.utils.FileUtils;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Stream;


// The main idea is not to read the entire ds into a 
// Map but use a cursor instead that only reads the 
// current line.
public class Tag {

    // This is the object representing one line
    // A record is just an immutable class
    // {1357033683744612353, 1357058831453462531, 1357028144138620928},rt @prisonr4rmact: it was confirmed by an inside source, that a prison officer  @hmp_wandsworth knows that she has covid, but refuses to ta…
    // text = rt @prisonr4rmact: it was confirmed by an inside source, that a prison officer  @hmp_wandsworth knows that she has covid, but refuses to ta…
    // count = 3
    record Cell(String text, int count) { }

    // The file you want to read
    static final Path FILE = Path.of("/home/dipp/Github/Playground/Data/tags.json");
    // The regex that divides text from {1357033683744612353, 1357058831453462531, 1357028144138620928}
    static final String PATTERN = "(^\\[[[0-9]*[,]?[ ]?]*][,])";
    // This is the array that contains the top 500 at any point in time
    static Cell[] TOP_500;
    // This is the lowest count of the TOP_500
    static int threshold = 0;
    
    // This Method initializes the TOP_500 with the first 500 lines
    static void initialize() {
        // TODO read the first 500 cells/lines
        TOP_500 = new Cell[10];
    }


    // Might not work out of the box
    static List<Cell> run() throws IOException {
        var reader = FileUtils.newBufferedReader(FILE);
        var pattern = Pattern.compile(PATTERN);
        var line = "";
        while ((line = reader.readLine()) != null) {
            var result = pattern.matcher(line);
            var count = Integer.parseInt(result.group(0)); // TODO count number of numbers
            var text = result.group(1);
            // Exchanging the minimum value in the array
            if (count > threshold) {
                threshold = count;
                var newCell = new Cell(text, count);
                int minimumIndex = 0;
                int minimum = Integer.MAX_VALUE;
                for (int i = 0; i < TOP_500.length; i++) {
                    if (TOP_500[i].count < minimum) {
                        minimum = TOP_500[i].count;
                        minimumIndex = i;
                    }
                }
                TOP_500[minimumIndex] = newCell;
            }
        }
        // The TOP_500 are not sorted yet. We do that once at the end.
        return Stream.of(TOP_500)
                .sorted((c1, c2) -> Integer.compare(c1.count, c2.count))
                .toList();
    }

    public static void main(String[] args) throws IOException {
        initialize();
        run();
    }
}