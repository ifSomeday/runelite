package net.runelite.client.plugins.zul.patterns;

import java.util.ArrayList;
import java.util.List;

import net.runelite.api.Prayer;
import net.runelite.client.plugins.zul.phase.ZulrahType;
import net.runelite.client.plugins.zul.phase.StandLocation;
import net.runelite.client.plugins.zul.phase.ZulrahLocation;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;

public abstract class ZulrahPattern {

    private final List<ZulrahPhase> pattern = new ArrayList<>();

    /**
     * Adds a non jad phase
     * @param loc
     * @param type
     * @param standLoc
     * @param prayer
     */
    protected final void add(ZulrahLocation loc, ZulrahType type, StandLocation standLoc, Prayer prayer) {
        add(loc, type, standLoc, false, prayer);
    }

    protected final void addJad(ZulrahLocation loc, ZulrahType type, StandLocation standLoc, Prayer prayer) {
        add(loc, type, standLoc, true, prayer);
    }

    private void add(ZulrahLocation loc, ZulrahType type, StandLocation standLoc, boolean jad, Prayer prayer) {
        pattern.add(new ZulrahPhase(loc, type, jad, standLoc, prayer));
    }

    public ZulrahPhase get(int i) {

        if (canReset(i)) {
            return(null);
        }

        return(pattern.get(i));
    }

    public boolean stageMatches(int i, ZulrahPhase instance) {
        ZulrahPhase patternInstance = get(i);
        return(patternInstance != null && patternInstance.equals(instance));
    }

    public boolean canReset(int i) {
        return(i >= pattern.size());
    }

}