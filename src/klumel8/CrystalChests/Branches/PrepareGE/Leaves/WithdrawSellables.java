package klumel8.CrystalChests.Branches.PrepareGE.Leaves;

import klumel8.CrystalChests.CrystalConstants;
import klumel8.Framework.Leaf;
import org.powbot.api.Filter;
import org.powbot.api.rt4.Bank;
import org.powbot.api.rt4.Item;
import org.powbot.api.rt4.ItemType;
import org.powbot.api.rt4.stream.item.BankItemStream;

import java.util.ArrayList;
import java.util.List;

public class WithdrawSellables extends Leaf {
    @Override
    public boolean validate() {
        Item it = Bank.stream().name(CrystalConstants.sellItems).filtered(i -> i.getInventoryIndex() == -1).first();
        System.out.println("first: " + it.name() + " " + it.getStack() + " " + it.getInventoryIndex() + " " + it.valid());

        if(Bank.stream().name(CrystalConstants.sellItems).filtered(i -> i.getInventoryIndex() == -1).isNotEmpty()){
            return true;
        }
        return false;
    }

    @Override
    public void execute() {
        if(!Bank.withdrawModeNoted()){
            Bank.withdrawModeNoted(true);
        }
        Item it = Bank.stream().name(CrystalConstants.sellItems).first();
        System.out.println(it.name() + " stack " + it.stackSize());

        BankItemStream items = Bank.stream().name(CrystalConstants.sellItems).filtered(i -> i.stackSize() != 0);
        List<String> nameList = new ArrayList<>();
        for (Item item : items){
            nameList.add(item.name());
        }


        List<String> unfoundList = new ArrayList<>();
        for (String name : nameList){
            System.out.println("Withdrawing " + name);
            if(!Bank.stream().name(name).first().interact("Withdraw-All")){
                unfoundList.add(name);
            }
        }

        for (String name : unfoundList){
            System.out.println("Withdrawing (using bank) " + name);
            Bank.withdraw(name, Bank.Amount.ALL);
        }

        Bank.withdrawModeNoted(false);
    }

    @Override
    public String status() {
        return "Withdrawing items";
    }
}
