package net.runelite.client.plugins.deomicgorillas;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.events.AnimationChanged;
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

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (event.getActor() != gorilla)
        {
            return;
        }

        if (gorilla.getAnimation() == DemonicGorillasAttack.MAGIC.getAnimation())
        {
            attack = DemonicGorillasAttack.MAGIC;
        }
        else if (gorilla.getAnimation() == DemonicGorillasAttack.RANGE.getAnimation())
        {
            attack = DemonicGorillasAttack.RANGE;
        }
        else if (gorilla.getAnimation() == DemonicGorillasAttack.MELEE.getAnimation())
        {
            attack = DemonicGorillasAttack.MELEE;
        }
    }
}
