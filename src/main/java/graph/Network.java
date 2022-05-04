package graph;

import model.Model.User;
import org.eclipse.collections.impl.factory.Sets;
import org.jgrapht.graph.DefaultDirectedGraph;

import java.util.HashSet;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

public abstract class Network extends DefaultDirectedGraph<User, Edge> {

    Network() { super(Edge.class); }

    public Set<Long> vertexIds() {
        return vertexSet()
                .stream()
                .map(user -> user.usrId)
                .collect(toSet());
    }

    void removeUnconnectedNodes() {
        var set = vertexSet()
                .stream()
                .filter(vertex -> degreeOf(vertex) == 0)
                .collect(toSet());
        removeAllVertices(set);
    }

    Set<User> intersect(Network _graph) {
        if (_graph.equals(this)) return new HashSet<>(vertexSet());
        return Sets.intersect(_graph.vertexSet(), vertexSet());
    }

    boolean hasIntersect(graph.Network _graph) {
        return intersect(_graph).size() > 0;
    }
}
