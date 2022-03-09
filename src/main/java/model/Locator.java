package model;

import datastructures.other.Tuples;
import datastructures.other.Tuples.Tuple2;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import utils.FileUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static main.Config.*;
import static utils.FileUtils.newBufferedReader;
import static utils.Utils.createOptionalFromLocation;

// Location Database
public enum Locator {
    get;

    private final Map<String, Location> locations = new HashMap<>();
    private final Object2IntOpenHashMap<String> unresolvedLocations = new Object2IntOpenHashMap<>();

    public static class Location {
//        double gps_x, gps_y;
        public final Optional<String> countryCode;
        public final Optional<String> country;
        public final Optional<String> state;
        public final Optional<String> city;
        public Location(String countryCode,
                        String country,
                        String state,
                        String city) {
            this.countryCode = createOptionalFromLocation(countryCode);
            this.country = createOptionalFromLocation(country);
            this.state = createOptionalFromLocation(state);
            this.city = createOptionalFromLocation(city);
        }

        public String toString() {
            return "Location{" +
                    "countryCode=" + countryCode.orElse("not set") +
                    ", country=" + country.orElse("not set") +
                    ", state=" + state.orElse("not set") +
                    ", city=" + city.orElse("not set") +
                    '}';
        }
    }

    // TODO read locations from file
    public void read() throws IOException {
        var reader = newBufferedReader(LOCATIONS_FILE);
        String line = "";
        String writtenLocationFromUser = "";
        int lineCount = 0;
        while ((line = reader.readLine()) != null) {
            if (lineCount++ % 2 == 0) {
                writtenLocationFromUser = line.toLowerCase(Locale.ROOT);
            }
            else {
                var split = line.substring(1, line.length() - 2).split(",");
                var location = new Location(split[0], split[1], split[2], split[3]);
                if (!locations.containsKey(writtenLocationFromUser)) {
                    locations.put(writtenLocationFromUser, location);
                }
            }
        }
    }

    // Kassel, Deutschland --> kassel, deutschland
    // KaSSel, deutschland --> kassel, deutschland
    public Optional<Location> resolve(String writtenLocationFromUser) {
        var location = locations.get(writtenLocationFromUser.toLowerCase(Locale.ROOT));
        if (isNull(location)) {
            unresolvedLocations.addTo(writtenLocationFromUser, 1);
            return Optional.empty();
        }
        else {
            return Optional.of(location);
        }
    }

    public List<Tuple2<String, Integer>> unresolvedLocations() {
        return unresolvedLocations.object2IntEntrySet().stream()
                        .sorted((e1, e2) -> Integer.compare(e2.getIntValue(), e1.getIntValue()))
                        .map(e -> Tuples.of(e.getKey(), e.getIntValue()))
                        .collect(Collectors.toList());
    }

    public void exportUnresolvedLocations() throws IOException {
        if (!Files.exists(UNREOLVED_LOCATIONS_FILE)) Files.createFile(UNREOLVED_LOCATIONS_FILE);
        var writer = FileUtils.newBufferedWriterAppend(UNREOLVED_LOCATIONS_FILE);
        unresolvedLocations.forEach((loc, cnt) -> {
            try {
                writer.write(cnt + "," + loc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        writer.close();
    }

    public String toString() {
        return "Location Resolver has "
                + locations.size()
                + " resolved locations and "
                + unresolvedLocations.size()
                + " unresolved locations";
    }
}


