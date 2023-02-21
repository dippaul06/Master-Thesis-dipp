package model;

import magma.concurrent.NBHashMap;
import magma.exa.value.tuple.Tuple;
import magma.exa.value.tuple.Tuple2;
import magma.utils.FileUtils;
import org.bson.BsonDocument;
import org.json.simple.JSONObject;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;
import static magma.exa.base.contract.Assert.isTrue;
import static magma.utils.FileUtils.newBufferedReader;
import static magma.utils.Utils.createOptionalFromLocation;
import static magma.utils.Utils.extractString;
import static main.Config.UNRESOLVED_LOCATIONS_FILE;

public enum Locator {
    get;

    private final Map<String, Location> locations = new HashMap<>();

    private final NBHashMap<String, Integer> unresolvedLocations = new NBHashMap<>();

    public static class Location {
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
        public JSONObject toJson() {
            var json = new JSONObject();
            countryCode.ifPresentOrElse(c -> json.put("co", c), () -> json.put("co", ""));
            state.ifPresentOrElse(c -> json.put("st", c), () -> json.put("st", ""));
            city.ifPresentOrElse(c -> json.put("ci", c), () -> json.put("ci", ""));
            return json;
        }
        public static Location fromJson(BsonDocument bson) {
            return new Location(
                    extractString(bson, "co"),
                    "",
                    extractString(bson, "st"),
                    extractString(bson, "ci")
            );
        }
    }
    public void readJohannesLocations(Path locPth, Path resPth) {
        var linLoc = newBufferedReader(locPth).lines().collect(Collectors.toList());
        var linSlv = newBufferedReader(resPth).lines().collect(Collectors.toList());
        isTrue(linLoc.size() == linSlv.size(), "FILES HAVE WRONG SIZE");
        for (int i = 0; i < linLoc.size(); i++) {
            var loc = linLoc.get(i).toLowerCase();
            var slv = linSlv.get(i);
            if (!locations.containsKey(loc)) {
                var split = slv.substring(1, slv.length() - 2).split(",");
                var location = new Location(split[0], split[1], split[2], split[3]);
                locations.put(loc, location);
            }
        }
    }

    public Optional<Location> resolve(String writtenLocationFromUser) {
        var location = locations.get(writtenLocationFromUser.toLowerCase());
        if (isNull(location)) {
            if (!unresolvedLocations.containsKey(writtenLocationFromUser)) {
                unresolvedLocations.put(writtenLocationFromUser, 1);
            }
            else {
                unresolvedLocations.computeIfPresent(writtenLocationFromUser, (key, val) -> val + 1);
            }
            return Optional.empty();
        }
        else {
            return Optional.of(location);
        }
    }

    public List<Tuple2<String, Integer>> unresolvedLocations() {
        return unresolvedLocations.entrySet().stream()
                .sorted((e1, e2) -> Integer.compare(e2.getValue(), e1.getValue()))
                .map(e -> Tuple.of(e.getKey(), e.getValue()))
                .collect(Collectors.toList());
    }

    public void exportUnresolvedLocations() throws IOException {
        if (!Files.exists(UNRESOLVED_LOCATIONS_FILE)) Files.createFile(UNRESOLVED_LOCATIONS_FILE);
        var writer = FileUtils.newBufferedWriterAppend(UNRESOLVED_LOCATIONS_FILE);
        unresolvedLocations().forEach(tpl -> {
            try {
                writer.write(tpl._1.replace(System.lineSeparator(), " ") + "\n");
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


