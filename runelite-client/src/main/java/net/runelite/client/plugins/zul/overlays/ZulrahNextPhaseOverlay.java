package net.runelite.client.plugins.zul.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import com.google.inject.Inject;

import net.runelite.client.plugins.zul.ZulrahInstance;
import net.runelite.client.plugins.zul.ZulrahPlugin;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;

public class ZulrahNextPhaseOverlay extends Overlay {

    private final ZulrahPlugin plugin;

    @Inject
    ZulrahNextPhaseOverlay(ZulrahPlugin plugin) {

        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.plugin = plugin;

    }

    @Override
    public Dimension render(Graphics2D graphics) {

        ZulrahInstance instance = plugin.getInstance();

        if(instance == null) {
            return(null);
        }

        ZulrahPhase nextPhase = instance.getNextPhase();
        
        if(nextPhase == null) {
            return(null);
        }

        Color backgroundColor = nextPhase.getColor();
        BufferedImage zulrahImage = ZulrahImageManager.getSmallZulrahBufferedImage(nextPhase.getType());

        InfoBoxComponent infoBoxComponent = new InfoBoxComponent();
        infoBoxComponent.setText("Next");
        infoBoxComponent.setImage(zulrahImage);
        infoBoxComponent.setBackgroundColor(backgroundColor);

        return(infoBoxComponent.render(graphics));
    }
}