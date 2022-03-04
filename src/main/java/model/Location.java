package model;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// Location Database
public enum Location {
    get;


    Map<Long, String> uid_Location = new HashMap<>();
    Map<String, String> Location_Location = new HashMap<>();



    void readLocations() {
        // id, userLocation, goggle_location([xxx, xxx, xxx, xxx])
        // for over all files
        // --- add id, userLocation, goggle_location([xxx, xxx, xxx, xxx]) to uid_Location & Location_Location
    }

    Optional<String> getLocationFromId(long userId) {

    }

    Optional<String> getLocationFromLocation(long userId) {

    }



//    final String name;
//    final double xCoordinate;
//    final double yCoordinate;
//
//    public Location(String name, double xCoordinate, double yCoordinate) {
//        this.name = name;
//        this.xCoordinate = xCoordinate;
//        this.yCoordinate = yCoordinate;
//    }
}
