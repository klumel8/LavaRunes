package klumel8.MoneyMaking.CrystalChests.Branches.TradeLoot.Leaves;

import klumel8.KlumAPI.Framework.Leaf;

public class StopMovingOn extends Leaf {
    @Override
    public boolean validate() {
        return true;
    }

    @Override
    public void execute() {
        System.out.println("StopMovingOn");
    }

    @Override
    public String status() {
        return "Not done with GE, holding";
    }
}
