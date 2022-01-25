package graph;

import corona_5g.Model.User;

public class Component extends Graph<User, Edge> {

    final Slice slice;

    Component(Slice _slice) {
        super(Edge.class);
        slice = _slice;
    }
}
