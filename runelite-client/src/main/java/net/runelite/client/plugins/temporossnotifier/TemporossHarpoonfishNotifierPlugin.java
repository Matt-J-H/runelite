package net.runelite.client.plugins.temporossnotifier;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.api.NpcID;
import net.runelite.api.events.NpcSpawned;
import net.runelite.client.Notifier;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.game.FishingSpot;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.Plugin;

import javax.inject.Inject;

@PluginDescriptor(
        name = "Tempoross Harpoonfish Notifier",
        description = "Notifies you when a double Harpoonfish spot appears."
)
@Slf4j
public class TemporossHarpoonfishNotifierPlugin extends Plugin
{
    @Inject
    private Notifier notifier;

    @Subscribe
    public void onNpcSpawned(NpcSpawned event)
    {
        final NPC npc = event.getNpc();
        FishingSpot spot = FishingSpot.findSpot(npc.getId());
        if (spot == null)
        {
            return;
        }

        if (spot == FishingSpot.HARPOONFISH && npc.getId() == NpcID.FISHING_SPOT_10569)
        {
            notifier.notify("A double Harpoonfish spot has appeared.");
        }
    }
}
