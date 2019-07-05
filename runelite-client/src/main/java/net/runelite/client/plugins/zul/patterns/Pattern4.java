package net.runelite.client.plugins.zul.patterns;

import net.runelite.api.Prayer;
import net.runelite.client.plugins.zul.phase.StandLocation;
import net.runelite.client.plugins.zul.phase.ZulrahLocation;
import net.runelite.client.plugins.zul.phase.ZulrahType;

public class Pattern4 extends ZulrahPattern {

    public Pattern4() {

		add(ZulrahLocation.NORTH, ZulrahType.RANGE, StandLocation.TOP_EAST, null);
		add(ZulrahLocation.EAST, ZulrahType.MAGIC, StandLocation.TOP_EAST, Prayer.PROTECT_FROM_MAGIC);
		add(ZulrahLocation.SOUTH, ZulrahType.RANGE, StandLocation.PILLAR_WEST_INSIDE, Prayer.PROTECT_FROM_MISSILES);
		add(ZulrahLocation.WEST, ZulrahType.MAGIC, StandLocation.PILLAR_WEST_INSIDE, Prayer.PROTECT_FROM_MAGIC);
		add(ZulrahLocation.NORTH, ZulrahType.MELEE, StandLocation.PILLAR_EAST_OUTSIDE, null);
		add(ZulrahLocation.EAST, ZulrahType.RANGE, StandLocation.PILLAR_EAST_OUTSIDE, Prayer.PROTECT_FROM_MISSILES);
		add(ZulrahLocation.SOUTH, ZulrahType.RANGE, StandLocation.PILLAR_WEST_OUTSIDE, null);
		add(ZulrahLocation.WEST, ZulrahType.MAGIC, StandLocation.PILLAR_WEST_OUTSIDE, Prayer.PROTECT_FROM_MAGIC);
		add(ZulrahLocation.NORTH, ZulrahType.RANGE, StandLocation.TOP_EAST, Prayer.PROTECT_FROM_MISSILES);
		add(ZulrahLocation.NORTH, ZulrahType.MAGIC, StandLocation.TOP_EAST, Prayer.PROTECT_FROM_MAGIC);
		addJad(ZulrahLocation.EAST, ZulrahType.MAGIC, StandLocation.TOP_EAST, Prayer.PROTECT_FROM_MAGIC);
		add(ZulrahLocation.NORTH, ZulrahType.MAGIC, StandLocation.TOP_EAST, null);

    }

    @Override
    public String toString() {
        return("Pattern 4");
    }

}