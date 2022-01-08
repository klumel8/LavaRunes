package klumel8.Lavas.nodes;

import klumel8.Lavas.LavasMain;

public abstract class Node {

    private LavasMain lavasMain;

    public Node(final LavasMain lavasMain) {
        this.lavasMain = lavasMain;
    }

    public abstract boolean validate();

    public abstract void execute();

    public abstract String status();

}