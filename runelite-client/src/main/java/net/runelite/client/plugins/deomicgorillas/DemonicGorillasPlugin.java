package net.runelite.client.plugins.deomicgorillas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.Hitsplat;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.HitsplatApplied;
import net.runelite.api.events.InteractingChanged;
import net.runelite.api.events.NpcDespawned;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.annotation.Nullable;
import javax.inject.Inject;


@PluginDescriptor(
        name = "!Demonic Gorillas",
        enabledByDefault = true
)
@Slf4j
public class DemonicGorillasPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private DemonicGorillasOverlay overlay;

    @Getter(AccessLevel.PACKAGE)
    @Nullable
    private DemonicGorillasAttack attack;

    @Getter(AccessLevel.PACKAGE)
    private int counter;

    @Getter(AccessLevel.PACKAGE)
    @Nullable
    private Actor gorilla;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        gorilla = null;
        attack = null;
        counter = 0;
    }

    @Subscribe
    public void onInteractingChanged(InteractingChanged event)
    {
        Actor source = event.getSource();
        Actor target = event.getTarget();

        if (source == null || target == null)
        {
            return;
        }
        else if (target == client.getLocalPlayer() && source.getName().equals("Demonic gorilla"))
        {
            gorilla = source;
        }
        else if (source == client.getLocalPlayer() && target.getName().equals("Demonic gorilla"))
        {
            gorilla = target;
        }
    }

    @Subscribe
    public void onNpcDespawned(final NpcDespawned event)
    {
        if (gorilla == event.getActor())
        {
            gorilla = null;
            attack = null;
        }
    }

    private void checkAnimationChange(DemonicGorillasAttack thisAttack)
    {
        if (attack != thisAttack)
        {
            attack = thisAttack;
            counter = 3;
        }
    }

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (event.getActor() != gorilla)
        {
            return;
        }

        if (gorilla.getAnimation() == DemonicGorillasAttack.MAGIC.getAnimation())
        {
            checkAnimationChange(DemonicGorillasAttack.MAGIC);
        }
        else if (gorilla.getAnimation() == DemonicGorillasAttack.RANGE.getAnimation())
        {
            checkAnimationChange(DemonicGorillasAttack.RANGE);
        }
        else if (gorilla.getAnimation() == DemonicGorillasAttack.MELEE.getAnimation())
        {
            checkAnimationChange(DemonicGorillasAttack.MELEE);
        }
    }

    @Subscribe
    public void onHitsplatApplied(HitsplatApplied hitsplatApplied)
    {
        if (hitsplatApplied.getActor() != client.getLocalPlayer())
        {
            return;
        }
        if (gorilla != null && hitsplatApplied.getHitsplat().getHitsplatType() == Hitsplat.HitsplatType.BLOCK_ME)
        {
            counter--;
            if (counter < 0)
            {
                counter = 2; //Doing another round of the same attack type
            }
        }
    }
}
