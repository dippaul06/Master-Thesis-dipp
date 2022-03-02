package main;

import model_Final.Loader;

//import experiments.DipsTask_1;


//import model.Loader;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
//import transform.Transformer;
//import transform.Loader_Test_Daniel;

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
                .addOption("hw", "helloWorld", false, "simple hello world program")
                .addOption("test", "test", false, "This is Dipps test")
                ;

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
            var pathToFolder = Path.of(arg.get(0));
            System.out.println(boldBrightGreen("WE TRY TO LOAD ALL FILES IN: ") + boldBrightBlue(pathToFolder.toString()));
 //           new Transformer().loadXzDump(pathToFolder);
 //           new Loader_Test_Daniel().loadXzDump(pathToFolder);
            new Loader().loadXzDump(pathToFolder);
//            new DipsTask_1().dipsFirstTask(pathToFolder);
//            Loader.get.loadFile(pathToFolder);
        }
    }
}

