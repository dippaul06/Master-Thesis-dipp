package graph;

import model.Model;
import model.Model.Status;
import model.Model.User;
import org.jgrapht.graph.DefaultWeightedEdge;

import java.util.*;

import static java.util.Comparator.comparing;

/**
 * Edges count the number of tweets, retweets and comments
 * between users. Here, each of this contacts is explicitly
 * stored in a contact object.
 */
public class Edge extends DefaultWeightedEdge implements Cloneable {

    private SortedSet<Status> retweets = new TreeSet<>(comparing(sa -> sa.date));
    public final User src, tgt;

    public Edge(User src, User tgt) {
        this.src = src;
        this.tgt = tgt;
    }

    public Edge addStatus(Status retweet) {
        retweets.add(retweet);
        return this;
    }

    public boolean isAfter(Date date) {
        for (var rtw : retweets) {
            if (rtw.date.after(date)) ;
            return true;
        }
        return false;
    }

    public boolean isBefore(Date date) {
        for (var rtw : retweets) {
            if (rtw.date.before(date)) ;
            return true;
        }
        return false;
    }

    @Override
    protected double getWeight() {
        return retweets.size();
    }

    @Override
    public String toString() {
        var sb = new StringBuilder();
        retweets.forEach(sb::append);
        return sb.toString();
    }

    @Override
    public Edge clone() {
        var newEdge = (Edge) super.clone();
        newEdge.retweets.addAll(retweets);
        return newEdge;
    }
}
