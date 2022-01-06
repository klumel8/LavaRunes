package klumel8.Lavas;

import klumel8.CorpKiller.Main;
import klumel8.Lavas.nodes.*;
import klumel8.Lavas.store.Store;
import org.powbot.api.Condition;
import org.powbot.api.event.InventoryChangeEvent;
import org.powbot.api.event.InventoryItemActionEvent;
import org.powbot.api.event.TickEvent;
import org.powbot.api.event.WidgetActionEvent;
import org.powbot.api.rt4.*;
import org.powbot.api.rt4.walking.model.Skill;
import org.powbot.api.script.AbstractScript;
import org.powbot.api.script.OptionType;
import org.powbot.api.script.ScriptConfiguration;
import org.powbot.api.script.ScriptManifest;
import org.powbot.api.script.paint.Paint;
import org.powbot.api.script.paint.PaintBuilder;
import org.powbot.mobile.service.ScriptUploader;

import java.util.ArrayList;
import java.util.List;

@ScriptManifest(
        name = "KlumLavas",
        description = "Makes lava runes using magic imbue spell. Start anywhere with all items you want to use in your inventory.",
        version = "1.0"
)

//Decides not to include because who the fuck doesnt have stams and is going to take 4 earth talisman every time there...
@ScriptConfiguration.List(
        {
                @ScriptConfiguration(
                        name = "type",
                        description = "Do you want to use magic imbue or talismans?",
                        defaultValue = "Magic Imbue",
                        allowedValues = {"Magic Imbue", "Earth Talisman"},
                        optionType = OptionType.STRING
                ),
                @ScriptConfiguration(
                        name = "stamina",
                        description = "Do you want to use stamina potions or ferox enclave?",
                        defaultValue = "Stamina",
                        allowedValues = {"Stamina", "Ferox enclave"},
                        optionType = OptionType.STRING
                )
        }
)

public class LavasMain extends AbstractScript {
    public List<Node> nodes = new ArrayList<>();
    KlumScripts ks = new KlumScripts();
    String status = "none";
    public Task task = Task.TravelToAltar;

    public static void main(String[] args){
            new ScriptUploader().uploadAndStart("KlumLavas", "N/A", "127.0.0.1:5575", true, false);
    }

    @Override
    public void onStart(){
        Condition.sleep(300);
        System.out.println("Started " + getManifest().name() + " " + getManifest().version() + "!");
        String useStamina = getOption("stamina");
        String talismanType = getOption("type");

        Store.magicImbue = talismanType.equals("Magic Imbue");
        Store.useStaminas = useStamina.equals("Stamina");
        Store.getPouches();
        Store.setInitialItems();
        Store.lastImbue = System.currentTimeMillis() - 20000;
        //System.out.println("Detected: " + Store.pouches + " with total capacity of " + Store.pouchCapacity());

        nodes.add(new TravelToAltar(this));
        nodes.add(new CraftRunes(this));
        nodes.add(new TravelToBank(this));
        nodes.add(new Banking(this));
        nodes.add(new RepairPouch(this));
        nodes.add(new PrepareInvForMage(this));
        nodes.add(new EmptyPouches(this));
        nodes.add(new MagicImbue(this));

        Paint paint = new PaintBuilder().trackSkill(Skill.Runecrafting)
                .addString(() -> lavasMade())
                .build();
        addPaint(paint);
    }

    public String lavasMade(){
        return "Lava runes made: " + Store.lavasMade;
    }

    @Override
    public void onStop(){
        System.out.println("");
    }

    @Override
    public void poll() {
        for (final Node node : nodes) {
            if (node.validate()) {
                status = node.status();
                node.execute();
                break;
            }
        }
    }

    public enum Task{
        TravelToAltar, CraftRunes, TravelToBank, Banking, RepairPouch, PrepareInvForMage, EmptyPouches, MagicImbue
    }

    @com.google.common.eventbus.Subscribe
    void onInventoryChange(InventoryChangeEvent evt){
        if(evt.getItemName().equals("Lava rune") && evt.getQuantityChange() > 0){
            Store.lavasMade += evt.getQuantityChange();
        }
    }
    @com.google.common.eventbus.Subscribe
    void onInventoryItemAction(InventoryItemActionEvent evt){
        System.out.println(evt.getName()+":"+evt.getId());
    }
}
