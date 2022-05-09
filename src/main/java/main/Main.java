package main;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import magma.utils.FileUtils;
import model.Locator;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Locale;

import static main.Config.*;

public class Main {

    static void resortFile() throws IOException {
        Path src = Path.of("/global/D1/projects/umod/dipp/Test/locations");
        Path tgt = Path.of("/global/D1/projects/umod/dipp/Test/locations/locations.csv");
        var reader = FileUtils.newBufferedReader(src);
        if (!Files.exists(tgt)) Files.createFile(tgt);
        var writer = FileUtils.newBufferedWriterAppend(tgt);
        String line = "";
        while ((line = reader.readLine()) != null) {
            var split = line.split(",");
            StringBuilder newLine = new StringBuilder();
            for (int i = 0; i < split.length - 1; i++) {
                if (i == split.length - 2) {
                    newLine.append(split[i]);
                }
                else {
                    newLine.append(split[i] + ",");
                }
            }
            var nl = newLine.toString();
            writer.write(nl + "\n");
        }
        writer.close();
        System.out.println("DONE DONE DONE");
    }


    static void hashtags() throws IOException {
        var map = new Object2IntOpenHashMap<String>();
        Path src = Path.of("/global/D1/projects/umod/dipp/Test/tags.csv");
        var reader = FileUtils.newBufferedReader(src);

        String line = "";
        while ((line = reader.readLine()) != null) {
            var split = line.split(",");
            var tag = split[1];
            map.addTo(tag.toLowerCase(Locale.ROOT), 1);
        }
        map.object2IntEntrySet()
                .stream()
                .limit(100)
                .sorted((e1, e2) -> Integer.compare(e2.getIntValue(), e1.getIntValue()))
                .forEach(e -> System.out.println(e.getKey()));
    }

    public static void main(String[] args) {
  //      Locator.get.readJohannesLocations(LOCATIONS_FILE_1_LOC, LOCATIONS_FILE_1_SLV);
//        Locator.get.readJohannesLocations(LOCATIONS_FILE_2_LOC, LOCATIONS_FILE_2_SLV);
//        Locator.get.readJohannesLocations(LOCATIONS_FILE_3_LOC, LOCATIONS_FILE_3_SLV);
  //      System.out.println(Locator.get);
    }
}