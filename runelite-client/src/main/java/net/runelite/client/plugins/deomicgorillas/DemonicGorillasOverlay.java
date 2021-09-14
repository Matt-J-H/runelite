package net.runelite.client.plugins.deomicgorillas;

import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.SpriteID;
import net.runelite.api.coords.LocalPoint;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.api.Actor;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;
import net.runelite.client.ui.overlay.components.TextComponent;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DemonicGorillasOverlay extends Overlay
{
    private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(255, 66, 66, 150);
    private static final Color ACTIVATED_BACKGROUND_COLOR = new Color(101, 255, 66, 150);
    private static final int MAX_DISTANCE = 2500;

    private static final int yOffset = -110;

    private final Client client;
    private final DemonicGorillasPlugin plugin;
    private final SpriteManager spriteManager;
    private final PanelComponent imagePanelComponent = new PanelComponent();
    private final TextComponent textComponent = new TextComponent();

    @Inject
    private DemonicGorillasOverlay(Client client, DemonicGorillasPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.DYNAMIC);
        setPriority(OverlayPriority.HIGH);
        setLayer(OverlayLayer.ALWAYS_ON_TOP);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;
    }

    @Override
    public Dimension render(Graphics2D graphics)
    {
        final DemonicGorillasAttack attack = plugin.getAttack();
        Actor gorilla = plugin.getGorilla();

        if (attack == null || gorilla == null)
        {
            return null;
        }

        LocalPoint gorillaPosition = plugin.getGorilla().getLocalLocation();
        if (gorillaPosition == null)
        {
            return null;
        }

        LocalPoint playerPosition = client.getLocalPlayer().getLocalLocation();
        if (playerPosition.distanceTo(gorillaPosition) <= MAX_DISTANCE)
        {
            net.runelite.api.Point counterLocation = Perspective.getCanvasTextLocation(client,
                    graphics,
                    gorillaPosition,
                    String.valueOf(plugin.getCounter()), 200);
            if (counterLocation != null)
            {
                textComponent.setText(String.valueOf(plugin.getCounter()));
                textComponent.setFont(new Font(Font.DIALOG, Font.BOLD, 30));
                textComponent.setPosition(new java.awt.Point(counterLocation.getX(), counterLocation.getY() + yOffset));
                textComponent.setColor(plugin.getCounter() == 0 ? NOT_ACTIVATED_BACKGROUND_COLOR : new Color(255, 255, 255, 200));
                textComponent.render(graphics);
            }

            net.runelite.api.Point spriteLocation = Perspective.getCanvasTextLocation(client,
                    graphics,
                    gorillaPosition,
                    String.valueOf(plugin.getCounter()), 190);
            final BufferedImage prayerImage = getPrayerImage(attack);
            imagePanelComponent.getChildren().clear();
            imagePanelComponent.getChildren().add(new ImageComponent(prayerImage));
            imagePanelComponent.setPreferredLocation(new java.awt.Point(spriteLocation.getX(), spriteLocation.getY() + yOffset));
            imagePanelComponent.setBackgroundColor(client.isPrayerActive(attack.getPrayer())
                    ? ACTIVATED_BACKGROUND_COLOR
                    : NOT_ACTIVATED_BACKGROUND_COLOR);
            imagePanelComponent.render(graphics);
        }

        return null;
    }

    private BufferedImage getPrayerImage(DemonicGorillasAttack attack)
    {
        final int prayerSpriteID = attack == DemonicGorillasAttack.MAGIC ? SpriteID.PRAYER_PROTECT_FROM_MAGIC :
                                   attack == DemonicGorillasAttack.RANGE ? SpriteID.PRAYER_PROTECT_FROM_MISSILES :
                                   SpriteID.PRAYER_PROTECT_FROM_MELEE;
        return spriteManager.getSprite(prayerSpriteID, 0);
    }
}
