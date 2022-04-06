package main;

//import transform.Transformer;
import model_Final.Locator;

import java.io.IOException;
import java.nio.file.Path;

// THIS IS THE MAIN CLASS FOR LOCAL
// TESTING FROM THE IDE.
public class Main {

    public static void main(String[] args) throws InterruptedException, IOException {
 //       Path path = Path.of("/global/D1/projects/umod/dipp/");
        Path path = Path.of("/global/D1/projects/umod/dipp/location_test_latest_mar_09/Freq-Files-Merged/frequentplaces60000_Merge.txt");
//        new Transformer().loadXzDump(path);
//        new Loader_Test_Daniel().loadXzDump(path);
        new Locator().read(path);
    }
}
