package net.runelite.client.plugins.zul;

import net.runelite.api.NPC;

import net.runelite.api.Prayer;
import net.runelite.api.coords.WorldPoint;
import net.runelite.client.plugins.zul.patterns.ZulrahPattern;
import net.runelite.client.plugins.zul.phase.StandLocation;
import net.runelite.client.plugins.zul.phase.ZulrahLocation;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.plugins.zul.phase.ZulrahType;

/**
 * An instance of the Zulrah NPC herself
 */
public class ZulrahInstance {

    // These phases track the initial phases before the pattern can be determined

    // do not need to pray vs the first phase
    private static final ZulrahPhase START_PHASE = new ZulrahPhase(ZulrahLocation.NORTH, ZulrahType.RANGE, false,
            StandLocation.TOP_EAST, Prayer.PROTECT_FROM_MISSILES);

    private static final ZulrahPhase NO_PATTERN_MAGIC_PHASE = new ZulrahPhase(ZulrahLocation.NORTH, ZulrahType.MAGIC, false,
            StandLocation.PILLAR_EAST_OUTSIDE, Prayer.PROTECT_FROM_MAGIC);

    private static final ZulrahPhase NO_PATTERN_MELEE_PHASE = new ZulrahPhase(ZulrahLocation.NORTH, ZulrahType.MAGIC, false,
            StandLocation.TOP_EAST, null);

    private final WorldPoint startLocation;
    private ZulrahPattern pattern;
    private int stage;
    private ZulrahPhase phase;

    public ZulrahInstance(NPC zulrah) {
        
        this.startLocation = zulrah.getWorldLocation();

    }

    /**
     * @return the startLocation
     */
    public WorldPoint getStartLocation() {
        return startLocation;
    }

    /**
     * @return the pattern
     */
    public ZulrahPattern getPattern() {
        return pattern;
    }

    /**
     * @param pattern the pattern to set
     */
    public void setPattern(ZulrahPattern pattern) {
        this.pattern = pattern;
    }

    /**
     * @return the stage
     */
    public int getStage() {
        return stage;
    }

    /**
     * @param stage the stage to set
     */
    public void setStage(int stage) {
        this.stage = stage;
    }

    /**
     * @return the phase
     */
    public ZulrahPhase getPhase() {
        
        ZulrahPhase patternPhase = null;

        if(pattern != null) {
            patternPhase = pattern.get(stage);
        }

        return(patternPhase != null ? patternPhase : phase);

    }

    /**
     * @param phase the phase to set
     */
    public void setPhase(ZulrahPhase phase) {
        this.phase = phase;
    }

    public void nextStage() {
        stage++;
    }

    public void reset() {

        pattern = null;
        stage = 0;

    }

    public ZulrahPhase getNextPhase() {
        if(pattern != null) {
            return(null);
        
        //we can not determine pattern yet
        } else if (phase != null) {
            ZulrahType type = phase.getType();
            StandLocation standLoc = phase.getStandLoc();

            //TODO: can maybe determine future pattern here
            if(type == ZulrahType.MELEE) {
                return(NO_PATTERN_MAGIC_PHASE);
            } else if(type == ZulrahType.RANGE) {
                return(NO_PATTERN_MELEE_PHASE);
            }

        }

        return(null);
    }


}