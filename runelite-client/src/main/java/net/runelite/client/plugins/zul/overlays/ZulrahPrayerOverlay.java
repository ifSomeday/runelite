package net.runelite.client.plugins.zul.overlays;

import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.annotation.Nullable;
import javax.inject.Inject;

import net.runelite.api.Client;
import net.runelite.api.Prayer;
import net.runelite.api.SpriteID;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.zul.ZulrahInstance;
import net.runelite.client.plugins.zul.ZulrahPlugin;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;

public class ZulrahPrayerOverlay extends Overlay {

    private final Client client;
    private final ZulrahPlugin plugin;
    private final SpriteManager spriteManager;

    @Inject
    ZulrahPrayerOverlay(@Nullable Client client, ZulrahPlugin plugin, SpriteManager spriteManager) {

        setPosition(OverlayPosition.BOTTOM_RIGHT);
        setPriority(OverlayPriority.MED);
        this.client = client;
        this.plugin = plugin;
        this.spriteManager = spriteManager;

    }

    @Override
    public Dimension render(Graphics2D graphics) {

        ZulrahInstance instance = plugin.getInstance();

        if (instance == null) {
            return (null);
        }

        ZulrahPhase currentPhase = instance.getPhase();

        if (currentPhase == null) {
            return (null);
        }

        Prayer prayer = currentPhase.isJad() ? null : currentPhase.getPrayer();

        if(prayer == null || client.isPrayerActive(prayer)) {
            return(null);
        }

        BufferedImage prayerImage = getPrayerImage(prayer);

        InfoBoxComponent infoBoxComponent = new InfoBoxComponent();
        infoBoxComponent.setText("Switch!");
        infoBoxComponent.setImage(prayerImage);

        return(infoBoxComponent.render(graphics));

    }

    private BufferedImage getPrayerImage(Prayer prayer) {
		int prayerSpriteID = prayer == Prayer.PROTECT_FROM_MAGIC ? SpriteID.PRAYER_PROTECT_FROM_MAGIC : SpriteID.PRAYER_PROTECT_FROM_MISSILES;
		return(spriteManager.getSprite(prayerSpriteID, 0));
	}
    
}