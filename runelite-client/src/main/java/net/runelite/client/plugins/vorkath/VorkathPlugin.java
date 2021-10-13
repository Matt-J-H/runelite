package net.runelite.client.plugins.vorkath;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Actor;
import net.runelite.api.Client;
import net.runelite.api.events.AnimationChanged;
import net.runelite.api.events.InteractingChanged;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.ui.overlay.OverlayManager;

import javax.annotation.Nullable;
import javax.inject.Inject;

@PluginDescriptor(
        name = "!Vorkath"
)
@Slf4j
public class VorkathPlugin extends Plugin
{
    @Inject
    private Client client;

    @Inject
    private OverlayManager overlayManager;

    @Inject
    private VorkathOverlay overlay;

    @Getter(AccessLevel.PACKAGE)
    @Nullable
    private Actor vorkath;

    @Getter(AccessLevel.PACKAGE)
    private int currentAnimationID;

    @Override
    protected void startUp() throws Exception
    {
        overlayManager.add(overlay);
    }

    @Override
    protected void shutDown() throws Exception
    {
        overlayManager.remove(overlay);
        vorkath = null;
    }

    @Subscribe
    public void onInteractingChanged(InteractingChanged event)
    {
        if (vorkath != null)
        {
            return;
        }

        Actor source = event.getSource();
        Actor target = event.getTarget();

        if (source == null || target == null)
        {
            return;
        }
        else if (target == client.getLocalPlayer() && source.getName().equals("Vorkath"))
        {
            vorkath = source;
        }
        else if (source == client.getLocalPlayer() && target.getName().equals("Vorkath"))
        {
            vorkath = target;
        }
    }

    @Subscribe
    public void onAnimationChanged(final AnimationChanged event)
    {
        if (event.getActor() != vorkath)
        {
            return;
        }

        System.out.println("Vorkath Animation: " + vorkath.getAnimation());
        currentAnimationID = vorkath.getAnimation();
        //if (vorkath.getAnimation() == fireballAnimation)
        //{
        //    checkAnimationChange(DemonicGorillasAttack.MAGIC);
        //}
    }
}
