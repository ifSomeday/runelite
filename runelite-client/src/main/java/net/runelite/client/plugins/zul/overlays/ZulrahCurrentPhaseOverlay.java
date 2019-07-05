package net.runelite.client.plugins.zul.overlays;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.inject.Inject;

import net.runelite.client.plugins.zul.ZulrahInstance;
import net.runelite.client.plugins.zul.ZulrahPlugin;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;
import net.runelite.client.ui.overlay.OverlayPriority;
import net.runelite.client.ui.overlay.components.InfoBoxComponent;

public class ZulrahCurrentPhaseOverlay extends Overlay {

    private final ZulrahPlugin plugin;

    @Inject
    ZulrahCurrentPhaseOverlay(ZulrahPlugin plugin) {
        setPosition(OverlayPosition.ABOVE_CHATBOX_RIGHT);
        setPriority(OverlayPriority.HIGH);
        this.plugin = plugin;
    }

    @Override
    public Dimension render(Graphics2D graphics) {

        ZulrahInstance instance = this.plugin.getInstance();

        if (instance == null) {
            return (null);
        }

        ZulrahPhase currentPhase = instance.getPhase();

        if (currentPhase == null) {
            return (null);
        }

        //Prepare assets
        String pattern = instance.getPattern() != null ? instance.getPattern().toString() : "Unknown";
        String title = currentPhase.isJad() ? "JAD PHASE" : pattern;
        Color backgroundColor = currentPhase.getColor();
        BufferedImage zulrahImage = ZulrahImageManager.getZulrahBufferedImage(currentPhase.getType());

        //load assets
        InfoBoxComponent infoBoxComponent = new InfoBoxComponent();
        infoBoxComponent.setText(title);
        infoBoxComponent.setBackgroundColor(backgroundColor);
        infoBoxComponent.setImage(zulrahImage);

        //render assets
        return infoBoxComponent.render(graphics);
        
    }
    
}