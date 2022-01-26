package utils;

import datastructures.other.Tuples;
import datastructures.other.Tuples.Tuple2;
import it.unimi.dsi.fastutil.ints.Int2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import org.jgrapht.Graph;
import org.jgrapht.Graphs;

import java.util.LinkedHashSet;
import java.util.Set;

public class GraphUtils extends Graphs {

    private GraphUtils() {}

    public static <V, E> Object2IntMap<V> degreeDistribution(Graph<V, E> g) {
        var result = new Object2IntOpenHashMap<V>(g.vertexSet().size());
        for (V v : g.vertexSet()) result.put(v, g.degreeOf(v));
        return result;
    }

    public static <V, E> Object2IntMap<V> inDegreeDistribution(Graph<V, E> g) {
        var result = new Object2IntOpenHashMap<V>(g.vertexSet().size());
        for (V v : g.vertexSet()) result.put(v, g.inDegreeOf(v));
        return result;
    }

    public static <V, E> Object2IntMap<V> outDegreeDistribution(Graph<V, E> g) {
        var result = new Object2IntOpenHashMap<V>(g.vertexSet().size());
        for (V v : g.vertexSet()) result.put(v, g.outDegreeOf(v));
        return result;
    }

    // TODO
    public static <V, E> double[] degreeDegreeCorrelation(Graph<V, E> g) {
        var maxDegree = maxDegree(g);
        final var size = maxDegree + 1;
        var result = new double[size];
        var nNodesArr = new int[size];
        var sumDegArr = new int[size];
        for (V v : g.vertexSet()) {
            var _vtxDegree = g.degreeOf(v);
            var _sumDegree = 0;
            for (V n : neighborListOf(g, v)) {
                _sumDegree = g.degreeOf(n);
                sumDegArr[_vtxDegree] += _sumDegree;
                nNodesArr[_vtxDegree] += _vtxDegree;
            }
        }
        for (int i = 0; i < size; i++)
            if (nNodesArr[i] != 0)
                result[i] = (double) sumDegArr[i]
                      / (double) nNodesArr[i];
            else result[i] = 0;
        return result;
    }

    public static <V, E> Int2ObjectMap<Tuple2<Integer, Integer>>
            degreeDegreeCorrelationPre(Graph<V, E> g) {
        var maxDegree = maxDegree(g);
        final var size = maxDegree + 1;
        var result = new double[size];
        var nNodesArr = new int[size];
        var sumDegArr = new int[size];
        for (V v : g.vertexSet()) {
            var _vtxDegree = g.degreeOf(v);
            var _sumDegree = 0;
            for (V n : neighborListOf(g, v)) {
                _sumDegree = g.degreeOf(n);
                sumDegArr[_vtxDegree] += _sumDegree;
                nNodesArr[_vtxDegree] += _vtxDegree;
            }
        }
        var map = new Int2ObjectLinkedOpenHashMap<Tuple2<Integer, Integer>>();
        for (int i = 0; i < size; i++)
            map.put(i, Tuples.of(
                    sumDegArr[i],
                    nNodesArr[i]));
        return map;
    }

    public static <V, E> int maxDegree(Graph<V, E> g) {
        int result = 0;
        for (V v : g.vertexSet()) {
            int degree = g.degreeOf(v);
            if (degree > result) result = degree;
        }
        return result;
    }

    public static <V, E> Set<V> neighborIncomingOf(Graph<V, E> g, V vertex) {
        var neighbors = new LinkedHashSet<V>();
        for (E e : g.incomingEdgesOf(vertex))
            neighbors.add(g.getEdgeSource(e));
        return neighbors;
    }

    public static <V, E> Set<V> neighborOutgoingOf(Graph<V, E> g, V vertex) {
        var neighbors = new LinkedHashSet<V>();
        for (E e : g.outgoingEdgesOf(vertex))
            neighbors.add(g.getEdgeTarget(e));
        return neighbors;
    }

    public static <V, E> int sumNeighborsIn(Graph<V, E> g, Set<V> vertices) {
        int result = 0; for (V v : vertices) result += g.inDegreeOf(v);
        return result;
    }

    public static <V, E> int sumNeighborsOut(Graph<V, E> g, Set<V> vertices) {
        int result = 0; for (V v : vertices) result += g.outDegreeOf(v);
        return result;
    }

    public static <V, E> int sumNeighbors(Graph<V, E> g, Set<V> vertices) {
        int result = 0; for (V v : vertices) result += g.degreeOf(v);
        return result;
    }

    public static <V, E> int numNodes(Graph<V, E> g) {
        return g.vertexSet().size();
    }

    public static <V, E> int numEdges(Graph<V, E> g) {
        return g.edgeSet().size();
    }

    public static <V, E>  String status(Graph<V, E> g) {
        return "NODES: " + numNodes(g) + " EDGES " +  numEdges(g);
    }
}
