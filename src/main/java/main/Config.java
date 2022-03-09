package main;

import java.nio.file.Path;

public class Config {

    private static final Path MAIN_FOLDER = Path.of("/global/D1/homes/dipp/");

    // TODO fill create folder for location files
    public static final Path LOCATIONS_FILE = MAIN_FOLDER.resolve("LOCATION_FILE");
    public static final Path UNREOLVED_LOCATIONS_FILE = MAIN_FOLDER.resolve("UNRESOLVED_LOCATION_FILE");

}
