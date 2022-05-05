package main;

import java.nio.file.Path;

public class Config {

    static final Path LOCATION_FOLDER = Path.of("/global/D1/projects/umod/dipp/Test");
//    public static final Path LOCATIONS_FILE_1_LOC = LOCATION_FOLDER.resolve("locations_1.csv");
//    public static final Path LOCATIONS_FILE_2_LOC = LOCATION_FOLDER.resolve("locations_2.csv");
//    public static final Path LOCATIONS_FILE_3_LOC = LOCATION_FOLDER.resolve("locations_3.csv");
//    public static final Path LOCATIONS_FILE_1_SLV = LOCATION_FOLDER.resolve("locations_1.csv.resolved");
//    public static final Path LOCATIONS_FILE_2_SLV = LOCATION_FOLDER.resolve("locations_2.csv.resolved");
//    public static final Path LOCATIONS_FILE_3_SLV = LOCATION_FOLDER.resolve("locations_3.csv.resolved");
//
//    public static Path DATA_FOLDER = Path.of("/global/D1/homes/daniels/ukraine/data");
//
//    public static Path DATA_COMPRESSED_FOLDER_REMOTE = Path.of("/global/D1/homes/daniels/ukraine/compressed/");
//    public static Path DATA_COMPRESSED_FOLDER_LOCAL = Path.of("/Users/daniels/Data/UMOD/compressed_data");
//
//    public static final Path RESULT_FOLDER_LOCAL = Path.of("/Users/daniels/Data/UMOD/results");
//    public static final Path RESULT_FOLDER_REMOTE = Path.of("/global/D1/homes/daniels/ukraine/result");
//    public static final Path UNRESOLVED_LOCATIONS_FILE = RESULT_FOLDER_REMOTE.resolve("unresolved_locations");

    public static final Path COMPRESSED_DATA_FILE = LOCATION_FOLDER.resolve("out");
    public static final Path UNRESOLVED_LOCATIONS_FILE = LOCATION_FOLDER.resolve("frequentplaces120000.txt");
    public static final Path RESOLVED_LOCATIONS_FILE = LOCATION_FOLDER.resolve("frequentplaces120000.txt.resolved");

}
