package net.runelite.client.plugins.deomicgorillas;

import net.runelite.api.Client;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.fightcavejadhelper.FightCaveJadHelperPlugin;
import net.runelite.client.plugins.fightcavejadhelper.JadAttack;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayLayer;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.ComponentConstants;
import net.runelite.client.ui.overlay.components.ImageComponent;
import net.runelite.client.ui.overlay.components.PanelComponent;

import javax.inject.Inject;
import java.awt.*;
import java.awt.image.BufferedImage;

public class DemonicGorillasOverlay extends Overlay
{
    private static final Color NOT_ACTIVATED_BACKGROUND_COLOR = new Color(255, 66, 66, 150);

    private final Client client;
    private final DemonicGorillasPlugin plugin;
    private final SpriteManager spriteManager;
    private final PanelComponent imagePanelComponent = new PanelComponent();

    @Inject
    private DemonicGorillasOverlay(Client client, DemonicGorillasPlugin plugin, SpriteManager spriteManager)
    {
        setPosition(OverlayPosition.BOTTOM_RIGHT);
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

        if (attack == null)
        {
            return null;
        }

        final BufferedImage prayerImage = getPrayerImage(attack);

        imagePanelComponent.getChildren().clear();
        imagePanelComponent.getChildren().add(new ImageComponent(prayerImage));
        imagePanelComponent.setBackgroundColor(client.isPrayerActive(attack.getPrayer())
                ? ComponentConstants.STANDARD_BACKGROUND_COLOR
                : NOT_ACTIVATED_BACKGROUND_COLOR);

        return imagePanelComponent.render(graphics);
    }

    private BufferedImage getPrayerImage(DemonicGorillasAttack attack)
    {
        final int prayerSpriteID = attack == DemonicGorillasAttack.MAGIC ? SpriteID.PRAYER_PROTECT_FROM_MAGIC :
                                   attack == DemonicGorillasAttack.RANGE ? SpriteID.PRAYER_PROTECT_FROM_MISSILES :
                                   SpriteID.PRAYER_PROTECT_FROM_MELEE;
        return spriteManager.getSprite(prayerSpriteID, 0);
    }
}
