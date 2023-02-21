package main;


import experiments.*;
import magma.system.Log;
import model.Transformer;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import static magma.utils.StrUtils.Color.*;
import static main.Config.*;

// --------------------------------------------------
//  CLI.
// --------------------------------------------------
public class Cli {

    static void DONE() { System.out.println(boldBrightBlue("DONE DONE DONE")); }

    // MAIN METHOD
    public static void main(String[] args) throws Exception {
        var options = new Options()
                .addOption("h", "help", false, "print help")
                .addOption("t", "transform", false, "transform files to LZ4")
                .addOption("all", "all", false, "All available analysis");

        var cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption("h")) {
            new HelpFormatter()
                    .printHelp("analysis lib", options);
        }
        if (cmd.hasOption("t")) {
            Log.info("transform files to LZ4");
            Transformer.run(DATA_FOLDER, DATA_COMPRESSED_FOLDER_REMOTE);
        }
        if (cmd.hasOption("hw")) {
            //var arg = cmd.getArgList();
            //var ar1 = Path.of(arg.get(0));
            System.out.println(boldBrightGreen("HELLO WORLD"));
            //System.out.println(boldBrightCyan("ARGUMENT WAS: " + ar1));
            DONE();
        }
        if (cmd.hasOption("all")) {
            Log.info("RUN ALL ANALYSIS");
            Log.info("START WITH TIMELINE");
            var timeline = new Timeline(DATA_COMPRESSED_FOLDER_REMOTE);
            Log.info("START WITH TWEETS");
            Tweets.run(timeline, RESULT_FOLDER_REMOTE);
            Log.info("START WITH HASHTAGS");
            Hashtags.run(timeline, RESULT_FOLDER_REMOTE);
            Log.info("START WITH USERS");
            Users.run(timeline, RESULT_FOLDER_REMOTE);
            Log.info("START WITH LOCATIONS");
            Locations.run(timeline, RESULT_FOLDER_REMOTE);
            DONE();
        }
    }
}

