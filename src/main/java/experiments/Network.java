//package experiments;
//
//import magma.system.Log;
//import magma.utils.Utils.DegreeDistribution;
//import org.jgrapht.alg.scoring.*;
//import org.jgrapht.graph.DefaultEdge;
//
//import java.util.Arrays;
//import java.util.HashMap;
//import java.util.Map;
//import java.util.concurrent.CompletableFuture;
//
//import static magma.utils.Utils.simpleDirectedOf;
//
//public class Network implements Runnable {
//
//    private BetweennessCentrality<Long, DefaultEdge> betweenness;
//    private ClusteringCoefficient<Long, DefaultEdge> clustering;
//    private DegreeDistribution<Long, DefaultEdge> degrees;
//    private KatzCentrality<Long, DefaultEdge> katz;
//    private PageRank<Long, DefaultEdge> pageRank;
//    private Coreness<Long, DefaultEdge> coreness;
//    private boolean isAnalyzed = false;
//
//    private final graph.Network network;
//
//
//    public Network(graph.Network network) {
//        this.network = network;
//    }
//
//    CompletableFuture<Void> betweenness() {
//        return CompletableFuture.runAsync(() -> {
//            betweenness = new BetweennessCentrality<>(simpleDirectedOf(network));
//        });
//    }
//
//    CompletableFuture<Void> clustering() {
//        return CompletableFuture.runAsync(() -> {
//            clustering = new ClusteringCoefficient<>(simpleDirectedOf(network));
//        });
//    }
//
//    CompletableFuture<Void> degrees() {
//        return CompletableFuture.runAsync(() -> {
//            degrees = new DegreeDistribution<>(simpleDirectedOf(network));
//        });
//    }
//
//    CompletableFuture<Void> katz() {
//        return CompletableFuture.runAsync(() -> {
//            katz = new KatzCentrality<>(simpleDirectedOf(network));
//        });
//    }
//
//    CompletableFuture<Void> coreness() {
//        return CompletableFuture.runAsync(() -> {
//            coreness = new Coreness<>(simpleDirectedOf(network));
//        });
//    }
//
//
//    Map<Long, Integer> degreesScores() { return degrees.getScores(); }
//    Map<Long, Double> pageRankScores() { return pageRank.getScores(); }
//    Map<Long, Double> clusteringScores() { return clustering.getScores(); }
//    Map<Long, Integer> corenessScores() { return coreness.getScores(); }
//    Map<Long, Double> betweenessScores() {
//        Map<Long, Double> res = new HashMap<>();
//        try { res = betweenness.getScores(); }
//        catch (IllegalArgumentException e) {
//            System.out.println(Arrays.toString(e.getStackTrace())); }
//        return res;
//    }
//
//    int corenessDegeneracy() { return coreness.getDegeneracy(); }
//    double clusteringGlobal() { return clustering.getGlobalClusteringCoefficient(); }
//    double clusteringAverage() { return clustering.getAverageClusteringCoefficient(); }
//
//    public void run() {
//        if (!isAnalyzed) {
//            Log.info("CORENESS");   corenessScores();
//            Log.info("DEGREES");    degreesScores();
//            Log.info("BETWEENESS"); betweenessScores();
//            Log.info("PAGE-RANK");  pageRankScores();
//            Log.info("CLUSTERING"); clusteringScores();
//            isAnalyzed = true;
//        }
//    }
//
////    public Table export() {
////        betweenness = new BetweennessCentrality<>(simpleDirected());
////        clustering = new ClusteringCoefficient<>(simpleDirected());
////        degrees = new Utils.DegreeDistribution<>(simpleDirected());
////        alpha = new KatzCentrality<>(simpleDirected());
////        pageRank = new PageRank<>(simpleDirected());
////        coreness = new Coreness<>(simpleDirected());
////        if (!isAnalyzed) analyze();
////        var uidCol = LongColumn.create("id");
////        var corCol = IntColumn.create("coreness");
////        var degCol = IntColumn.create("degree");
////        var betCol = DoubleColumn.create("betweeness");
////        var prkCol = DoubleColumn.create("pageRank");
////        var cltCol = DoubleColumn.create("clustering");
////        for (V v : vertexSet()) {
//////            uidCol.append(v.id());
//////            corCol.append(coreness.getVertexScore(v.id()));
//////            degCol.append(degrees.getVertexScore(v.id()));
////////            betCol.append(betweenness.getVertexScore(v.id()));
//////            prkCol.append(pageRank.getVertexScore(v.id()));
//////            cltCol.append(clustering.getVertexScore(v.id()));
////        }
////        return Table.create(
////                uidCol,
////                degCol,
////                corCol,
////                prkCol,
//////                betCol,
////                cltCol
////        );
////    }
//}
