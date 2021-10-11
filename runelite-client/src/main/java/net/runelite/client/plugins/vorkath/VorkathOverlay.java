package net.runelite.client.plugins.vorkath;

import net.runelite.api.*;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import java.awt.*;

public class VorkathOverlay extends Overlay
{
    private static final int MAX_DISTANCE = 2500;

    private static final int yOffset = -100;

    private final Client client;
    private final VorkathPlugin plugin;
    private final TextComponent textComponent = new TextComponent();

    @Inject
    private VorkathOverlay(Client client, VorkathPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.client = client;
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final int animationID = plugin.getCurrentAnimationID();

        if (animationID == -1)
        {
            return null;
        }

        LocalPoint playerPosition = client.getLocalPlayer().getLocalLocation();
        net.runelite.api.Point displayLocation = Perspective.getCanvasTextLocation(client,
            graphics,
            playerPosition,
            String.valueOf(animationID), 200);
        if (displayLocation != null)
        {
            textComponent.setText(String.valueOf(animationID));
            textComponent.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
            textComponent.setPosition(new java.awt.Point(displayLocation.getX(), displayLocation.getY() + yOffset));
            textComponent.setColor(new Color(255, 255, 255, 200));
            textComponent.render(graphics);
        }

        return null;
    }
}
