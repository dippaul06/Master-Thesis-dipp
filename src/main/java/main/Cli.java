package main;

package cli;

import corona_5g.Converter;
import corona_5g.Store;
import corona_5g.analyze.Contacts;
import corona_5g.analyze.Users;
import corona_5g.experiments.*;
import corona_5g.experiments.kaspara.experiment_14;
import corona_5g.theo_johan.GraphGenerator;
import medival.Graph;
import medival.Main;
import medival.Scraper;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import politicians.Data;
import spread.RGraph;
import tweets.Loader;
import tweets.Pedro;
import tweets.benchmarks.Concurrent;
import tweets.benchmarks.Sequential;
import tweets.c_ij.GraphImporter;
import tweets.c_ij.Metrics;
import tweets.c_ij.ShortestPathBenchmarks;
import tweets.paper_2.CovidData;
import twitter.Twitter;

import java.io.IOException;
import java.nio.file.Path;

import static corona_5g.Consistency.checkStore;
import static corona_5g.Loader.*;
import static corona_5g.Twitter.*;
import static corona_5g.analyze.Users.findUser;
import static corona_5g.analyze.Users.storeUserIds;
import static utils.StrUtils.Color.boldBrightBlue;

// --------------------------------------------------
//  ANALYSIS.
// --------------------------------------------------
public class Cli {

    static void DONE() { System.out.println(boldBrightBlue("DONE DONE DONE")); }

    public static void main(String[] args) throws Exception {
        var options = new Options()
                .addOption("h", "help", false, "print help")
                .addOption("a", "loadAll", false, "load all tweets")
                .addOption("p1", "testSeq", false, "test sequential ds")
                .addOption("p2", "testCon", false, "test concurrent ds")
                .addOption("s", "shortest", false, "shortest Path Benchmark")
                .addOption("c", "cij", false, "calcloadAllulate cij-scores")
                .addOption("ferdi", "ferdi", false, "generate test data for ferdinand")
                .addOption("store5g", "store5g", false, "store 5g data")
                .addOption("reload5g", "reload5g", false, "reload 5g data")
                .addOption("loadEnrch5g", "loadEnrch5g", false, "loadEnrch5g")
                .addOption("loadInit5g", "loadInit5g", false, "loadInit5g")
                .addOption("users", "users", false, "users")
                .addOption("reloadUsers", "reloadUsers", false, "reloadUsers")
                .addOption("basicStats", "basicStats", false, "basicStats")
                .addOption("loadUsers", "loadUsers", false, "loadUsers")
                .addOption("reloadDeletedUsers", "reloadDeletedUsers", false, "reloadDeletedUsers")
                .addOption("loadAll", "loadAll", false, "loadAll")
                .addOption("loadContacts", "loadContacts", false, "loadContacts")
                .addOption("kaspara_exp12", "kaspara_exp12", false, "kaspara_exp12")
                .addOption("kaspara_exp13", "kaspara_exp13", false, "kaspara_exp13")
                .addOption("kaspara_exp14", "kaspara_exp14", false, "kaspara_exp14")
                .addOption("testGraph", "testGraph", false, "testGraph")
                .addOption("mentions", "mentions", false, "mentions")
                .addOption("usersA", "usersA", false, "usersA")
                .addOption("bugfix", "bugfix", false, "bugfix")
                .addOption("overTime", "overTime", false, "overTime")
                .addOption("firstTweet", "firstTweet", false, "firstTweet")
                .addOption("RtOverTime", "RtOverTime", false, "RtOverTime")
                .addOption("RtOverTimePedro", "RtOverTimePedro", false, "RtOverTimePedro")
                .addOption("RtOverTimePedro2", "RtOverTimePedro2", false, "RtOverTimePedro2")
                .addOption("jesper", "jesper", false, "jesper")
                .addOption("degdeg", "degdeg", false, "degdeg")
                .addOption("exdeg", "exdeg", false, "exdeg")
                .addOption("scrape", "scrape", false, "scrape")
                .addOption("select", "select", false, "select")
                .addOption("mergeNonConspUsers", "mergeNonConspUsers", false, "mergeNonConspUsers")
                .addOption("betweenessSampling", "betweenessSampling", false, "betweenessSampling")
                .addOption("shortestPath", "shortestPath", false, "shortestPath")
                .addOption("storeDieLinke", "storeDieLinke", false, "storeDieLinke")
                .addOption("abstractGraphPaper1", "abstractGraphPaper1", false, "abstractGraphPaper1")
                .addOption("abstractGraphPaper2", "abstractGraphPaper2", false, "abstractGraphPaper2")
                .addOption("countUsers", "countUsers", false, "countUsers")
                .addOption("extractUsers", "extractUsers", false, "extractUsers")
                .addOption("sampleForGraphistry", "sampleForGraphistry", false, "sampleForGraphistry")
                .addOption("createTable", "createTable", false, "createTable")
                .addOption("createUserStats", "createUserStats", false, "createUserStats")
                .addOption("scores", "scores", false, "scores")
                .addOption("c_tweets", "c_tweets", false, "c_tweets")
                ;

        var cmd = new DefaultParser().parse(options, args);

        if (cmd.hasOption("h")) {
            new HelpFormatter()
                    .printHelp("analysis lib", options);
        }
        if (cmd.hasOption("a")) {
            var argl = cmd.getArgList();
            var mongoDump = Path.of(argl.get(0));
//            var michaelDump = Path.of(argl.get(1));
            new Loader()
//                    .loadMichaelDump(michaelDump)
                    .loadMongoDump(mongoDump);
//                    .map();
            DONE();
        }
    }
}

