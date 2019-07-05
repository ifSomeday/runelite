package net.runelite.client.plugins.zul.phase;

import java.awt.Color;

import lombok.extern.slf4j.Slf4j;
import net.runelite.api.NPC;
import net.runelite.api.Prayer;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.zul.ZulrahConstants;

@Slf4j
public class ZulrahPhase {

    private final ZulrahLocation zulrahLoc;
    private final ZulrahType type;
    private final boolean jad;
    private final StandLocation standLoc;
    private final Prayer prayer;

    private static final Color RANGE_COLOR = new Color(150, 255, 0, 100);
    private static final Color MAGIC_COLOR = new Color(20, 170, 200, 100);
    private static final Color MELEE_COLOR = new Color(180, 50, 20, 100);
    private static final Color JAD_COLOR = new Color(150, 115, 0, 100);

    public ZulrahPhase(ZulrahLocation zulrahLoc, ZulrahType type, boolean jad, StandLocation standLoc, Prayer prayer) {

        this.zulrahLoc = zulrahLoc;
        this.type = type;
        this.jad = jad;
        this.standLoc = standLoc;
        this.prayer = prayer;

    }

    public static ZulrahPhase valueOf(NPC zulrah, WorldPoint start) {
        ZulrahLocation zulrahLocation = ZulrahLocation.valueOf(start, zulrah.getWorldLocation());
        log.info("Determined zulrahLocation to be {}", zulrahLocation);

        ZulrahType zulrahType = ZulrahType.valueOf(zulrah.getId());

        if(zulrahLocation == null || zulrahType == null) {
            log.info("location or type is null");
            return(null);
        }

        StandLocation standLocation = zulrahType == ZulrahType.MAGIC ? StandLocation.PILLAR_WEST_OUTSIDE : StandLocation.TOP_EAST;
        Prayer prayer = zulrahType == ZulrahType.MAGIC ? Prayer.PROTECT_FROM_MAGIC : null;
        return new ZulrahPhase(zulrahLocation, zulrahType, false, standLocation, prayer);
    }

    public WorldPoint getZulrahTile(WorldPoint startTile) {

        int plane = startTile.getPlane();
        int x = startTile.getX();
        int y = startTile.getY();

        if(zulrahLoc == ZulrahLocation.SOUTH)
            return(new WorldPoint(x + ZulrahConstants.SOUTH_OFFSET_X, y + ZulrahConstants.SOUTH_OFFSET_Y, plane));
        if(zulrahLoc == ZulrahLocation.EAST)
            return(new WorldPoint(x + ZulrahConstants.EAST_OFFSET_X, y + ZulrahConstants.EAST_OFFSET_Y, plane));
        if(zulrahLoc == ZulrahLocation.WEST)
            return(new WorldPoint(x + ZulrahConstants.WEST_OFFSET_X, y + ZulrahConstants.WEST_OFFSET_Y, plane));
        
        //North is the start tile
        return(startTile);
        
    }

    public WorldPoint getStandTile(WorldPoint startTile) {

        int plane = startTile.getPlane();
        int x = startTile.getX();
        int y = startTile.getY();

        if(standLoc == StandLocation.WEST)
            return(new WorldPoint(x + ZulrahConstants.P_WEST_OFFSET_X, y + ZulrahConstants.P_WEST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.EAST)
            return(new WorldPoint(x + ZulrahConstants.P_EAST_OFFSET_X, y + ZulrahConstants.P_EAST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.SOUTH)
            return(new WorldPoint(x + ZulrahConstants.P_SOUTH_OFFSET_X, y + ZulrahConstants.P_SOUTH_OFFSET_Y, plane));

        else if(standLoc == StandLocation.SOUTH_WEST)
            return(new WorldPoint(x + ZulrahConstants.P_SOUTH_WEST_OFFSET_X, y + ZulrahConstants.P_SOUTH_WEST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.SOUTH_EAST)
            return(new WorldPoint(x + ZulrahConstants.P_SOUTH_EAST_OFFSET_X, y + ZulrahConstants.P_SOUTH_EAST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.TOP_EAST)
            return(new WorldPoint(x + ZulrahConstants.P_TOP_EAST_OFFSET_X, y + ZulrahConstants.P_TOP_EAST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.TOP_WEST)
            return(new WorldPoint(x + ZulrahConstants.P_TOP_WEST_OFFSET_X, y + ZulrahConstants.P_TOP_WEST_OFFSET_Y, plane));

        else if(standLoc == StandLocation.PILLAR_WEST_INSIDE)
            return(new WorldPoint(x + ZulrahConstants.P_PILLAR_WEST_INSIDE_OFFSET_X, y + ZulrahConstants.P_PILLAR_WEST_INSIDE_OFFSET_Y, plane));

        else if(standLoc == StandLocation.PILLAR_WEST_OUTSIDE)
            return(new WorldPoint(x + ZulrahConstants.P_PILLAR_WEST_OUTSIDE_OFFSET_X, y + ZulrahConstants.P_PILLAR_WEST_OUTSIDE_OFFSET_Y, plane));

        else if(standLoc == StandLocation.PILLAR_EAST_INSIDE)
            return(new WorldPoint(x + ZulrahConstants.P_PILLAR_EAST_INSIDE_OFFSET_X, y + ZulrahConstants.P_PILLAR_EAST_INSIDE_OFFSET_Y, plane));

        else if(standLoc == StandLocation.PILLAR_EAST_OUTSIDE)
            return(new WorldPoint(x + ZulrahConstants.P_PILLAR_EAST_OUTSIDE_OFFSET_X, y + ZulrahConstants.P_PILLAR_EAST_OUTSIDE_OFFSET_Y, plane));

        return(startTile);
    } 

    public Color getColor() {
        if(jad) {
            return(JAD_COLOR);
        } else {
            if(type == ZulrahType.RANGE)
                return(RANGE_COLOR);
            else if(type == ZulrahType.MAGIC)
                return(MAGIC_COLOR);
            else if(type == ZulrahType.MELEE)
                return(MELEE_COLOR);
        }

        return(RANGE_COLOR);
    }

    /**
     * @return the zulrahLoc
     */
    public ZulrahLocation getZulrahLoc() {
        return zulrahLoc;
    }

    /**
     * @return the type
     */
    public ZulrahType getType() {
        return type;
    }

    /**
     * @return the jad
     */
    public boolean isJad() {
        return jad;
    }

    /**
     * @return the standLoc
     */
    public StandLocation getStandLoc() {
        return standLoc;
    }

    /**
     * @return the prayer
     */
    public Prayer getPrayer() {
        return prayer;
    }

    @Override
    public boolean equals(Object obj) {
        if(this == obj) {
            return(true);
        }
        if(obj == null || getClass() != obj.getClass()) {
            return(false);
        }
        ZulrahPhase other = (ZulrahPhase) obj;
        return(this.jad == other.jad && this.zulrahLoc == other.zulrahLoc && this.type == other.type);
    }

    @Override
    public String toString() {
        return( "ZulrahPhase{" +
                " type=" + type + 
                ", jad=" + jad + 
                ", standLoc=" + standLoc +
                ", prayer=" + prayer +
                " }"
        );
    }

    

}