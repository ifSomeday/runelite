package net.runelite.client.plugins.zul.phase;

import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.zul.ZulrahConstants;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public enum ZulrahLocation {

    NORTH, SOUTH, EAST, WEST;

    public static ZulrahLocation valueOf(WorldPoint start, WorldPoint current) {

        int dx = start.getX() - current.getX();
        int dy = start.getY() - current.getY();

        log.info("Zulrah coords=({}, {})", current.getX(), current.getY());

        if(dx == ZulrahConstants.EAST_OFFSET_X && dy == ZulrahConstants.EAST_OFFSET_Y)
            return(ZulrahLocation.EAST);
        else if(dx == ZulrahConstants.WEST_OFFSET_X && dy == ZulrahConstants.WEST_OFFSET_Y)
            return(ZulrahLocation.WEST);
        else if(dx == ZulrahConstants.SOUTH_OFFSET_X && dy == ZulrahConstants.SOUTH_OFFSET_Y)
            return(ZulrahLocation.SOUTH);
        else if(dx == ZulrahConstants.NORTH_OFFSET_X && dy == ZulrahConstants.NORTH_OFFSET_Y)
            return(ZulrahLocation.NORTH);
            
        log.warn("Unknown Zulrah location dx = {}, dy = {}", dx, dy);
        return(null);
    
    }

}