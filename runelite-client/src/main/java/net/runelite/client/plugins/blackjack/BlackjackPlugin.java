package net.runelite.client.plugins.blackjack;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.Multimap;
import com.google.inject.Provides;
import net.runelite.api.*;
import lombok.Getter;
import net.runelite.api.events.*;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.menuentryswapper.Swap;
import net.runelite.client.util.Text;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

@PluginDescriptor(
        name = "!Blackjack Helper",
        description = "Dynamically change default menu entry while blackjacking to make knock-out or pickpocket the default option",
        tags = {"thieving", "npcs"},
        enabledByDefault = false
)
public class BlackjackPlugin extends Plugin {

    @Inject
    private Client client;

    @Getter
    private Map<Integer, Actor> blackjackTargets;

    private final Multimap<String, Swap> swaps = LinkedHashMultimap.create();
    private final ArrayListMultimap<String, Integer> optionIndexes = ArrayListMultimap.create();

    @Override
    public void startUp()
    {
        blackjackTargets = new HashMap<>();
    }

    @Override
    public void shutDown()
    {
        blackjackTargets = null;
    }

    private boolean isNpcBlackjackTarget(int npcId)
    {
        return npcId == NpcID.VILLAGER ||
                npcId == NpcID.VILLAGER_3553 ||
                npcId == NpcID.VILLAGER_3554 ||
                npcId == NpcID.VILLAGER_3555 ||
                npcId == NpcID.VILLAGER_3556 ||
                npcId == NpcID.VILLAGER_3557 ||
                npcId == NpcID.VILLAGER_3558 ||
                npcId == NpcID.VILLAGER_3559 ||
                npcId == NpcID.VILLAGER_3560 ||
                npcId == NpcID.MENAPHITE_THUG ||
                npcId == NpcID.MENAPHITE_THUG_3550 ||
                npcId == NpcID.BANDIT_734 ||
                npcId == NpcID.BANDIT_735 ||
                npcId == NpcID.BANDIT_736 ||
                npcId == NpcID.BANDIT_737;
    }

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        NPC npc = event.getNpc();
        if (isNpcBlackjackTarget(npc.getId()))
        {
            blackjackTargets.put(npc.getIndex(), event.getActor());
        }
    }

    @Subscribe
    public void onNpcDespawned(NpcDespawned event)
    {
        NPC npc = event.getNpc();
        if (isNpcBlackjackTarget(npc.getId()))
        {
            blackjackTargets.remove(npc.getIndex());
        }
    }

    /*
    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (blackjackTargets.containsValue(event.getActor()))
        {
            System.out.println("Animation Changed ("+ event.getActor().getName() + "): " + event.getActor().getAnimation());
        }
    }
    */

    @Subscribe
    public void onMenuEntryAdded(MenuEntryAdded event)
    {
        if (blackjackTargets.containsKey(event.getIdentifier()))
        {
            final int eventId = event.getIdentifier();
            Actor npc = blackjackTargets.get(eventId);

            final String option = Text.removeTags(event.getOption()).toLowerCase();
            final String target = Text.removeTags(event.getTarget()).toLowerCase();

            //808 - argh my head
            //838 - asleep
            //395 - attack

            final int animationID = npc.getAnimation();
            if (animationID == 838 || animationID == 395)
            {
                swap("pickpocket", option, target, true);
            }
            else
            {
                swap("knock-out", option, target, true);
            }
        }
    }

    //swap() and searchIndex() are copied from MenuEntrySwapperPlugin.
    private void swap(String optionA, String optionB, String target, boolean strict)
    {
        MenuEntry[] entries = client.getMenuEntries();

        int idxA = searchIndex(entries, optionA, target, strict);
        int idxB = searchIndex(entries, optionB, target, strict);

        if (idxA >= 0 && idxB >= 0)
        {
            MenuEntry entry = entries[idxA];
            entries[idxA] = entries[idxB];
            entries[idxB] = entry;

            client.setMenuEntries(entries);
        }
    }

    private int searchIndex(MenuEntry[] entries, String option, String target, boolean strict)
    {
        for (int i = entries.length - 1; i >= 0; i--)
        {
            MenuEntry entry = entries[i];
            String entryOption = Text.removeTags(entry.getOption()).toLowerCase();
            String entryTarget = Text.removeTags(entry.getTarget()).toLowerCase();

            if (strict)
            {
                if (entryOption.equals(option) && entryTarget.equals(target))
                {
                    return i;
                }
            }
            else
            {
                if (entryOption.contains(option.toLowerCase()) && entryTarget.equals(target))
                {
                    return i;
                }
            }
        }

        return -1;
    }


}