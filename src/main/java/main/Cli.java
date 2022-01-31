package main;


import experiments.DipsTask_1;
import model.Loader;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;

import java.nio.file.Path;

import static utils.StrUtils.Color.*;

// --------------------------------------------------
//  CLI.
// --------------------------------------------------
public class Cli {

    static void DONE() { System.out.println(boldBrightBlue("DONE DONE DONE")); }


    // MAIN METHOD
    public static void main(String[] args) throws Exception {
        var options = new Options()
                .addOption("h", "help", false, "print help")
                .addOption("hw", "helloWorld", false, "simple hello world program");

        var cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption("h")) {
            new HelpFormatter()
                    .printHelp("analysis lib", options);
        }
        if (cmd.hasOption("hw")) {
            var arg = cmd.getArgList();
            var ar1 = Path.of(arg.get(0));
            System.out.println(boldBrightGreen("HELLO WORLD"));
            System.out.println(boldBrightCyan("ARGUMENT WAS: " + ar1));
            DONE();
        }
        if (cmd.hasOption("test")) {
            var arg = cmd.getArgList();
            var pathToFile = Path.of(arg.get(0));
            System.out.println(boldBrightGreen("WE TRY TO LOAD ") + boldBrightBlue(pathToFile.toString()));
            new DipsTask_1().dipsFirstTask(pathToFile);
            Loader.get.loadFile(pathToFile);
        }
    }
}

