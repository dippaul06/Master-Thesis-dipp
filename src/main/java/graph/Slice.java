package graph;


import graph.Contacts.CommentContact;
import graph.Contacts.Contact;
import graph.Contacts.QuoteContact;
import graph.Contacts.RetweetContact;
import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.objects.Object2IntLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import org.jgrapht.alg.clustering.LabelPropagationClustering;
import org.jgrapht.alg.connectivity.ConnectivityInspector;
import org.jgrapht.alg.interfaces.ClusteringAlgorithm.Clustering;
import org.jgrapht.nio.matrix.MatrixExporter;
import tech.tablesaw.api.DoubleColumn;
import tech.tablesaw.api.IntColumn;
import tech.tablesaw.api.LongColumn;
import tech.tablesaw.api.Table;
import tech.tablesaw.io.csv.CsvWriteOptions;
import tech.tablesaw.io.csv.CsvWriter;
import utils.GraphUtils;
import model.Model.User;
import utils.UserUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

import static java.lang.String.valueOf;
import static org.jgrapht.nio.matrix.MatrixExporter.Format.SPARSE_ADJACENCY_MATRIX;
import static system.Contracts.checkArgument;
import static utils.Utils.reverseSortBySize;

public class Slice extends Graph<User, Edge> {

    public Clustering<User> clustering;
    public List<Component> components;
    public Slice() { super(Edge.class); }
    public Slice(Collection<Contact<?>> contacts) { this();
        for (Contact<?> contact : contacts)
            this.addContact(contact);
    }

    private static final Object2IntMap<User> INDEX = new Object2IntLinkedOpenHashMap<>();
    private int sequencer = 0;

    public void addContact(Contact<?> contact) {
        var u1 = contact.parentUser();
        var u2 = contact.childUser();
        if (!containsVertex(u1)) addVertex(u1);
        if (!containsVertex(u2)) addVertex(u2);
        if (!containsEdge(u1, u2)) addEdge(u1, u2, new Edge(contact));
        else getEdge(u1, u2).addContact(contact);
    }

    public Clustering<User> cluster() {
        LabelPropagationClustering<User, Edge> label = new LabelPropagationClustering<>(this);
        clustering = label.getClustering();
        return clustering;
    }

    public Set<User> neighbourOf(User u) {
        return GraphUtils.neighborSetOf(this, u);
    }

    public int neighbourHoodSize(User u) {
        return neighbourOf(u).size();
    }

    public boolean addVertex(User user) {
        if (containsVertex(user)) return false;
        INDEX.putIfAbsent(user, sequencer++);
        return super.addVertex(user);
    }

    public Set<Contact<?>> contacts() {
        var result = new HashSet<Contact<?>>();
        for (Edge edge : edgeSet()) {
            result.addAll(edge.contacts);
        }
        return result;
    }

    private List<Set<User>> componentNodes() {
        var inspector = new ConnectivityInspector<>(this);
        return inspector.connectedSets();
    }

    public void calculateComponents() {
        var cmpNds = componentNodes();
        var result = new ArrayList<Component>();
        for (Set<User> nodes : cmpNds) {
            var component = new Component(this);
            result.add(component);
            for (var node : nodes) {
                var edges = edgesOf(node);
                for (var edge : edges) {
                    var src = getEdgeSource(edge);
                    var tgt = getEdgeTarget(edge);
                    if (nodes.contains(src)
                            && nodes.contains(tgt)) {
                        component.addVertex(src);
                        component.addVertex(tgt);
                        component.addEdge(src, tgt, edge);
                    }
                }
            }
        }
        components = reverseSortBySize(result);
    }

    public Path exportComponents(Path dir, String name) throws IOException {
        checkArgument(Files.isDirectory(dir));
        checkArgument(Files.notExists(dir.resolve(name)));
        int componentCnt = 0;
        var uidCol = LongColumn.create("id");
        var corCol = IntColumn.create("coreness");
        var degCol = IntColumn.create("degree");
//        var betCol = DoubleColumn.create("betweeness");
        var prkCol = DoubleColumn.create("pageRank");
        var cltCol = DoubleColumn.create("clustering");
        var cmpCol = IntColumn.create("component");
        for (var component : components) {
            var table = component.metrics();
            int finalComponentCnt = componentCnt;
            table.forEach(row -> {
                uidCol.append(row.getLong("id"));
                corCol.append(row.getInt("coreness"));
                degCol.append(row.getInt("degree"));
//                betCol.append(row.getDouble("betweeness"));
                prkCol.append(row.getDouble("pageRank"));
                cltCol.append(row.getDouble("clustering"));
                cmpCol.append(finalComponentCnt);
            });
            componentCnt++;
        }
        var table = Table.create(
                uidCol,
                degCol,
                corCol,
                prkCol,
                cltCol,
                cmpCol
        );
        var pth = Files.createFile(dir.resolve(name));
        CsvWriteOptions options =
                CsvWriteOptions.builder(pth.toFile())
                        .header(true)
                        .separator(',')
                        .build();
        new CsvWriter().write(table, options);
        return pth;
    }

    public void exportSlice(Path path, String name) throws IOException {
        checkArgument(Files.isDirectory(path));
        checkArgument(Files.notExists(path.resolve(name)));
        var file = Files.createFile(path.resolve(name)).toFile();
        var exporter = new MatrixExporter<User, Edge>(SPARSE_ADJACENCY_MATRIX);
        exporter.setVertexIdProvider(user -> valueOf(INDEX.getInt(user)));
        exporter.exportGraph(this, file);
    }

    public void exportUserLabels(Path path, String name) throws IOException {
        checkArgument(Files.notExists(path.resolve(name)));
        var stats = new UserUtils();
        var map = new LongOpenHashSet();
        vertexSet().forEach(user -> {
            if (edgesOf(user).size() > 0
                    && !map.contains(user.id())) {
                map.add(user.id());
                stats.addUser(user, INDEX.getInt(user));
            }
        });
        stats.write(path, name);
    }

    public void exportClustering(Path resDir) throws IOException {
        var cnt = 0;
        for (var cluster : clustering.getClusters()) {
            var uidCol = LongColumn.create("uid");
            var alkCol = IntColumn.create("all_K");
            var rtkCol = IntColumn.create("rt_K");
            var qteCol = IntColumn.create("qt_K");
            var cmtCol = IntColumn.create("cmt_K");
            var edgCol = IntColumn.create("edges");
            var resTbl = Table.create(uidCol, alkCol, rtkCol, qteCol, cmtCol, edgCol);
            for (var user : cluster) {
                var all = 0; var cmt = 0;
                var qte = 0; var rtw = 0;
                var edges = this.edgesOf(user);
                for (Edge edge : edges) {
                    for (Contact<?> contact : edge.contacts) {
                       if (contact instanceof RetweetContact) { rtw++; }
                       else if (contact instanceof QuoteContact) { qte++; }
                       else if (contact instanceof CommentContact) {cmt++;}
                       all++;
                    }
                }
                uidCol.append(user.id());
                alkCol.append(all);
                rtkCol.append(rtw);
                qteCol.append(qte);
                cmtCol.append(cmt);
                edgCol.append(edges.size());
            }
            resTbl.write().csv(resDir.resolve(cnt + ".csv").toString());
            cnt++;
        }
    }
}
