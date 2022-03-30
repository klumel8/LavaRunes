package klumel8.Framework;

import java.util.List;

public abstract class Branch{

    public final List<Leaf> leaves;

    protected Branch(Leaf... leaves) {
        this.leaves = List.of(leaves);
    }

    public abstract boolean validate();

    public abstract String status();

}