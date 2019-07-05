package net.runelite.client.plugins.zul.overlays;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

import javax.annotation.Nullable;

import com.google.inject.Inject;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.Perspective;
import net.runelite.api.Point;
import net.runelite.api.coords.LocalPoint;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.zul.ZulrahInstance;
import net.runelite.client.plugins.zul.ZulrahPlugin;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.ui.overlay.Overlay;
import net.runelite.client.ui.overlay.OverlayPosition;

@Slf4j
public class ZulrahOverlay extends Overlay {

    private static final Color TILE_BORDER_COLOR = new Color(0, 0, 0, 100);
    private static final Color NEXT_TEXT_COLOR = new Color(255, 255, 255, 0);

    private final Client client;
    private final ZulrahPlugin plugin;

    @Inject
    ZulrahOverlay(@Nullable Client client, ZulrahPlugin plugin) {

        setPosition(OverlayPosition.DYNAMIC);
        this.client = client;
        this.plugin = plugin;

    }

    @Override
    public Dimension render(Graphics2D graphics) {
        
        ZulrahInstance instance = plugin.getInstance();

        if(instance == null) {
            return(null);
        }

        ZulrahPhase currentPhase = instance.getPhase();
        ZulrahPhase nextPhase = instance.getNextPhase();

        if(currentPhase == null) {
            return(null);
        }

        WorldPoint startTile = instance.getStartLocation();

        if(nextPhase != null && currentPhase.getStandLoc() == nextPhase.getStandLoc()) {

            drawStandTiles(graphics, startTile, currentPhase, nextPhase);
        
        } else {

            drawStandTile(graphics, startTile, currentPhase, false);
            drawStandTile(graphics, startTile, nextPhase, true);

        }

        drawZulrahTileMinimap(graphics, startTile, currentPhase, false);
        drawZulrahTileMinimap(graphics, startTile, nextPhase, true);

        return(null);

    }

    private void drawStandTiles(Graphics2D graphics, WorldPoint startTile, ZulrahPhase currentPhase, ZulrahPhase nextPhase) {

        LocalPoint localPoint = LocalPoint.fromWorld(client, startTile);
        localPoint = new LocalPoint(localPoint.getX() + Perspective.LOCAL_HALF_TILE_SIZE, localPoint.getY() + Perspective.LOCAL_HALF_TILE_SIZE);

        Polygon northPoly = getCanvasTileNorthPoly(client, localPoint);
        Polygon southPoly = getCanvasTileSouthPoly(client, localPoint);
        Polygon poly = Perspective.getCanvasTilePoly(client, localPoint);
        Point textLoc = Perspective.getCanvasTextLocation(client, graphics, localPoint, "Next", 0);

        if(northPoly != null && southPoly != null && poly != null && textLoc != null) {

            Color northColor = currentPhase.getColor();
            Color southColor = nextPhase.getColor();

            graphics.setColor(northColor);
            graphics.fillPolygon(northPoly);

            graphics.setColor(southColor);
            graphics.fillPolygon(southPoly);

            graphics.setColor(TILE_BORDER_COLOR);
            graphics.setStroke(new BasicStroke(2));

            graphics.drawPolygon(poly);
            graphics.setColor(NEXT_TEXT_COLOR);

            graphics.drawString("Next", textLoc.getX(), textLoc.getY());
        }

        if(nextPhase.isJad()) {

            Image jadPrayerImg = ZulrahImageManager.getProtectionPrayerBufferedImage(nextPhase.getPrayer());
            if(jadPrayerImg != null) {

                Point imageLoc = Perspective.getCanvasImageLocation(client, localPoint, (BufferedImage) jadPrayerImg, 0);
                if(imageLoc != null) {

                    graphics.drawImage(jadPrayerImg, imageLoc.getX(), imageLoc.getY(), null);
                
                }

            }

        }

    }

    private void drawStandTile(Graphics2D graphics, WorldPoint startTile, ZulrahPhase phase, boolean next) {

        if(phase == null) {
            return;
        }

        LocalPoint localPoint = LocalPoint.fromWorld(client, phase.getStandTile(startTile));
        localPoint = new LocalPoint(localPoint.getX() + Perspective.LOCAL_HALF_TILE_SIZE, localPoint.getY() + Perspective.LOCAL_HALF_TILE_SIZE); 
        
        Polygon poly = Perspective.getCanvasTilePoly(client, localPoint);
        Color color = phase.getColor();
        
        if(poly != null) {

            graphics.setColor(TILE_BORDER_COLOR);
            graphics.setStroke(new BasicStroke(2));
            graphics.drawPolygon(poly);

            graphics.setColor(color);
            graphics.fillPolygon(poly);

        }

        if(next) {

            Point textLoc = Perspective.getCanvasTextLocation(client, graphics, localPoint, "Next", 0);
            if(textLoc != null) {

                graphics.setColor(NEXT_TEXT_COLOR);
                graphics.drawString("Next", textLoc.getX(), textLoc.getY());

            }

            if(phase.isJad()) {

                Image jadPrayerImg = ZulrahImageManager.getProtectionPrayerBufferedImage(phase.getPrayer());
                if(jadPrayerImg != null) {
    
                    Point imageLoc = Perspective.getCanvasImageLocation(client, localPoint, (BufferedImage) jadPrayerImg, 0);
                    if(imageLoc != null) {

                        graphics.drawImage(jadPrayerImg, imageLoc.getX(), imageLoc.getY(), null);

                    }
    
                }

            }

        }

    }

    private void drawZulrahTileMinimap(Graphics2D graphics, WorldPoint startTile, ZulrahPhase phase, boolean next) {

        if(phase == null) {
            return;
        }

        LocalPoint zulrahLocalTile = LocalPoint.fromWorld(client, phase.getZulrahTile(startTile));
        Point zulrahMinimapPoint = Perspective.localToMinimap(client, zulrahLocalTile);

        Color color = phase.getColor();

        graphics.setColor(color);
        graphics.fillOval(zulrahMinimapPoint.getX(), zulrahMinimapPoint.getY(), 5, 5);
        
        graphics.setColor(TILE_BORDER_COLOR);
        graphics.setStroke(new BasicStroke(1));
        graphics.drawOval(zulrahMinimapPoint.getX(), zulrahMinimapPoint.getY(), 5, 5);

        if(next) {

            graphics.setColor(NEXT_TEXT_COLOR);
            FontMetrics fm = graphics.getFontMetrics();
            graphics.drawString("Next", zulrahMinimapPoint.getX() - fm.stringWidth("Next") / 2, zulrahMinimapPoint.getY() - 2);

        }
    
    }

    private Polygon getCanvasTileNorthPoly(Client client, LocalPoint loc) {

		int plane = client.getPlane();
		int halfTile = Perspective.LOCAL_HALF_TILE_SIZE;

        LocalPoint p1 = new LocalPoint(loc.getX() - halfTile, loc.getY() - halfTile);
		LocalPoint p2 = new LocalPoint(loc.getX() - halfTile, loc.getY() + halfTile);
        LocalPoint p3 = new LocalPoint(loc.getX() + halfTile, loc.getY() + halfTile);

		if (p1 == null || p2 == null || p3 == null)
		{
			return null;
		}

		Polygon poly = new Polygon();
		poly.addPoint(p1.getX(), p1.getY());
		poly.addPoint(p2.getX(), p2.getY());
		poly.addPoint(p3.getX(), p3.getY());

		return poly;
	}

    private Polygon getCanvasTileSouthPoly(Client client, LocalPoint loc) {

		int plane = client.getPlane();
		int halfTile = Perspective.LOCAL_HALF_TILE_SIZE;

        LocalPoint p1 = new LocalPoint(loc.getX() - halfTile, loc.getY() - halfTile);
		LocalPoint p2 = new LocalPoint(loc.getX() + halfTile, loc.getY() + halfTile);
        LocalPoint p3 = new LocalPoint(loc.getX() + halfTile, loc.getY() - halfTile);

		if (p1 == null || p2 == null || p3 == null)
		{
			return null;
		}

		Polygon poly = new Polygon();
		poly.addPoint(p1.getX(), p1.getY());
		poly.addPoint(p2.getX(), p2.getY());
		poly.addPoint(p3.getX(), p3.getY());

		return poly;
	}
}