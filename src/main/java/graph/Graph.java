package graph;

import datastructures.other.Tuples;
import datastructures.other.Tuples.Tuple2;

import org.eclipse.collections.impl.factory.Sets;
import org.jgrapht.alg.scoring.*;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultUndirectedWeightedGraph;
import org.jgrapht.nio.graph6.Graph6Sparse6Exporter;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvWriteOptions;
import tech.tablesaw.io.csv.CsvWriter;
import system.Log;
import model_Final.Model;
import utils.Utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;


import static java.util.Objects.isNull;
import static java.util.stream.Collectors.toSet;
import static org.jgrapht.nio.graph6.Graph6Sparse6Exporter.Format.SPARSE6;
import static system.Contracts.checkArgument;
import static utils.Utils.copyGraph;
import static utils.Utils.simpleGraphOf;

public abstract class Graph<V extends Model.Id, E> extends DefaultUndirectedWeightedGraph<V, E> {
    private BetweennessCentrality<Long, DefaultEdge> betweenness;
    private ClusteringCoefficient<Long, DefaultEdge> clustering;
    private Utils.DegreeDistribution<Long, DefaultEdge> degrees;
    private AlphaCentrality<Long, DefaultEdge> alpha;
    private PageRank<Long, DefaultEdge> pageRank;
    private Coreness<Long, DefaultEdge> coreness;
    private boolean isAnalyzed = false;
    private final Class<E> eClass;

    Graph(Class<E> _eClass) {
        super(_eClass);
        eClass = _eClass;
    }

    void removeUnconnectedNodes() {
        var set = vertexSet()
                .stream()
                .filter(vertex -> degreeOf(vertex) == 0)
                .collect(toSet());
        removeAllVertices(set);
    }

    Set<V> intersect(Graph<V, ?> _graph) {
        if (_graph.equals(this)) return new HashSet<>(vertexSet());
        return Sets.intersect(_graph.vertexSet(), vertexSet());
    }

    Map<Long, Integer> degreesScores() { return degrees.getScores(); }
    Map<Long, Double> betweenessScores() {
        Map<Long, Double> res = new HashMap<>();
        try { res = betweenness.getScores(); }
            catch (IllegalArgumentException e) {
            System.out.println(Arrays.toString(e.getStackTrace())); }
        return res;
    }
    Map<Long, Double> pageRankScores() { return pageRank.getScores(); }
    Map<Long, Double> clusteringScores() { return clustering.getScores(); }
    Map<Long, Integer> corenessScores() {
        if (isNull(coreness))
            coreness = new Coreness<>(simpleGraphOf(this));
        return coreness.getScores();
    }

    int corenessDegeneracy() { return coreness.getDegeneracy(); }
    double clusteringGlobal() { return clustering.getGlobalClusteringCoefficient(); }
    double clusteringAverage() { return clustering.getAverageClusteringCoefficient(); }
    boolean hasIntersect(Graph<V, ?> _graph) { return intersect(_graph).size() > 0; }

    Graph<V, E> analyze() {
        if (!isAnalyzed) {
            Log.info("CORENESS");   corenessScores();
            Log.info("DEGREES");    degreesScores();
//            Log.info("BETWEENESS"); betweenessScores();
            Log.info("PAGE-RANK");  pageRankScores();
            Log.info("CLUSTERING"); clusteringScores();
            isAnalyzed = true;
        }
        return this;
    }

    public Path storeSparse6(Path dir, String name) throws IOException {
        checkArgument(Files.isDirectory(dir));
        checkArgument(Files.notExists(dir.resolve(name)));
        var pth = Files.createFile(dir.resolve(name));
        var exporter = new Graph6Sparse6Exporter<V, E>(SPARSE6);
        exporter.exportGraph(this, pth.toFile());
        return pth;
    }

    public Table metrics() {
//        betweenness = new BetweennessCentrality<>(copyGraph(this));
        clustering = new ClusteringCoefficient<>(copyGraph(this));
        degrees = new Utils.DegreeDistribution<>(copyGraph(this));
        alpha = new AlphaCentrality<>(copyGraph(this));
        pageRank = new PageRank<>(copyGraph(this));
        coreness = new Coreness<>(simpleGraphOf(this));
        if (!isAnalyzed) analyze();
        var uidCol = LongColumn.create("id");
        var corCol = IntColumn.create("coreness");
        var degCol = IntColumn.create("degree");
//        var betCol = DoubleColumn.create("betweeness");
        var prkCol = DoubleColumn.create("pageRank");
        var cltCol = DoubleColumn.create("clustering");
        for (V v : vertexSet()) {
            uidCol.append(v.id());
            corCol.append(coreness.getVertexScore(v.id()));
            degCol.append(degrees.getVertexScore(v.id()));
//            betCol.append(betweenness.getVertexScore(v.id()));
            prkCol.append(pageRank.getVertexScore(v.id()));
            cltCol.append(clustering.getVertexScore(v.id()));
        }
        return Table.create(
                uidCol,
                degCol,
                corCol,
                prkCol,
//                betCol,
                cltCol
        );
    }

    public Path storeMetrics(Path dir, String name) throws IOException {
        checkArgument(Files.isDirectory(dir));
        checkArgument(Files.notExists(dir.resolve(name)));
        var pth = Files.createFile(dir.resolve(name));
        CsvWriteOptions options =
                CsvWriteOptions.builder(pth.toFile())
                        .header(true)
                        .separator(',')
                        .build();
        new CsvWriter().write(metrics(), options);
        return pth;
    }

    public Set<Long> vertexIds() {
        return vertexSet()
                .stream()
                .map(Model.Id::id)
                .collect(toSet());
    }

    public Set<Tuple2<Long, Long>> edgesIds() {
        return edgeSet().stream().map(e ->
            Tuples.of(
                    getEdgeSource(e).id(),
                    getEdgeTarget(e).id()
            )).collect(toSet());
    }


//    public Path storeMatrix(Path dir, String name) throws IOException {
//        checkArgument(Files.isDirectory(dir));
//        checkArgument(Files.notExists(dir.resolve(name)));
//
//        var exporter = new MatrixExporter<V, E>(SPARSE_ADJACENCY_MATRIX);
//        exporter.setVertexIdProvider(node -> node.index() + "");
//        var pth = Files.createFile(dir.resolve(name));
//        var exporter = new Graph6Sparse6Exporter<V, E>(SPARSE6);
//        exporter.exportGraph(this, pth.toFile());
//        return pth;
//    }

}
