package klumel8.Skilling.WildyAltar.Branches.GoToBank.Leaves;

import klumel8.KlumAPI.Framework.Leaf;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Players;

public class OpenBank extends Leaf {
    @Override
    public boolean validate() {
        return Bank.nearest().tile().distanceTo(Players.local().tile()) <= 14;
    }

    @Override
    public void execute() {
        if(Bank.open()){
            System.out.println("Opened bank, set shouldBank to false");
        }
    }

    @Override
    public String status() {
        return "Opening bank";
    }
}
