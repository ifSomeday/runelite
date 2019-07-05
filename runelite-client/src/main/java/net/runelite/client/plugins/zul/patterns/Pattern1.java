package net.runelite.client.plugins.zul.patterns;

import net.runelite.api.Prayer;
import net.runelite.client.plugins.zul.phase.StandLocation;
import net.runelite.client.plugins.zul.phase.ZulrahLocation;
import net.runelite.client.plugins.zul.phase.ZulrahType;

public class Pattern1 extends ZulrahPattern {

    public Pattern1() {

        add(ZulrahLocation.NORTH, ZulrahType.RANGE, StandLocation.TOP_EAST, null);
        add(ZulrahLocation.NORTH, ZulrahType.MELEE, StandLocation.TOP_EAST, null);
        add(ZulrahLocation.NORTH, ZulrahType.MAGIC, StandLocation.PILLAR_WEST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
        add(ZulrahLocation.SOUTH, ZulrahType.RANGE, StandLocation.PILLAR_WEST_INSIDE, Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahLocation.NORTH, ZulrahType.MELEE, StandLocation.PILLAR_WEST_INSIDE, null);
        add(ZulrahLocation.WEST, ZulrahType.MAGIC, StandLocation.PILLAR_WEST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
        add(ZulrahLocation.SOUTH, ZulrahType.RANGE, StandLocation.PILLAR_EAST_OUTSIDE, null);
        add(ZulrahLocation.SOUTH, ZulrahType.MAGIC, StandLocation.SOUTH_WEST, Prayer.PROTECT_FROM_MAGIC);
        addJad(ZulrahLocation.WEST, ZulrahType.RANGE, StandLocation.TOP_WEST, Prayer.PROTECT_FROM_MISSILES);
        add(ZulrahLocation.NORTH, ZulrahType.MELEE, StandLocation.TOP_WEST, null);

    }

    @Override
    public String toString() {
        return("Pattern 1");
    }

}