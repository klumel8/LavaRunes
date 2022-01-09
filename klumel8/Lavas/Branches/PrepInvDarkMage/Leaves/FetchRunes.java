package klumel8.Lavas.Branches.PrepInvDarkMage.Leaves;

import klumel8.Lavas.Framework.Leaf;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Inventory;

public class FetchRunes extends Leaf {
    @Override
    public boolean validate() {
        return Bank.opened();
    }

    @Override
    public void execute() {
        if(Inventory.stream().name("Cosmic rune").isEmpty() && Bank.stream().name("Cosmic rune").first().stackSize() != 0){
            Bank.withdraw("Cosmic rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Air rune").isEmpty() && Bank.stream().name("Air rune").first().stackSize() != 0){
            Bank.withdraw("Air rune", Bank.Amount.ALL);
        }
        if(Inventory.stream().name("Astral rune").isEmpty() && Bank.stream().name("Astral rune rune").first().stackSize() != 0
                && Inventory.stream().name("Rune pouch").isEmpty()){
            Bank.withdraw("Astral rune", Bank.Amount.ALL);
        }
    }

    @Override
    public String status() {
        return "fetching contact npc runes";
    }
}
