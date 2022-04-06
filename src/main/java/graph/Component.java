package graph;

import model_Final.Model;

//import model.Model.User;
public class Component extends Graph<Model.User, Edge> {

    final Slice slice;

    Component(Slice _slice) {
        super(Edge.class);
        slice = _slice;
    }
}
