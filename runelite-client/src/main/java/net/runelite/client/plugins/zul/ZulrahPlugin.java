package net.runelite.client.plugins.zul;

import com.google.inject.Inject;
import com.google.inject.Provides;

import java.time.temporal.ChronoUnit;

import com.google.inject.Binder;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.GameState;
import net.runelite.api.NPC;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.game.SpriteManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.zul.overlays.ZulrahCurrentPhaseOverlay;
import net.runelite.client.plugins.zul.overlays.ZulrahOverlay;
import net.runelite.client.plugins.zul.overlays.ZulrahPrayerOverlay;
import net.runelite.client.plugins.zul.patterns.Pattern1;
import net.runelite.client.plugins.zul.patterns.Pattern2;
import net.runelite.client.plugins.zul.patterns.Pattern3;
import net.runelite.client.plugins.zul.patterns.Pattern4;
import net.runelite.client.plugins.zul.patterns.ZulrahPattern;
import net.runelite.client.plugins.zul.phase.ZulrahPhase;
import net.runelite.client.task.Schedule;
import net.runelite.client.ui.overlay.OverlayManager;


@PluginDescriptor(
    name = "This plugin is broken",
    description = "s",
	tags = {"boss"}
)

@Slf4j
public class ZulrahPlugin extends Plugin {

    @Inject
    Client client;

    @Inject
    OverlayManager overlayManager;

    @Inject
    SpriteManager spriteManager;

    @Inject
    ZulrahConfig config;

    @Inject
    ZulrahOverlay overlay;

    @Inject
    ZulrahCurrentPhaseOverlay currentPhaseOverlay;

    @Inject
    ZulrahPrayerOverlay zulrahPrayerOverlay;

    private final ZulrahPattern[] patterns = new ZulrahPattern[] {
        new Pattern1(),
        new Pattern2(),
        new Pattern3(),
        new Pattern4()
    };

    private ZulrahInstance instance;

    @Override
    public void configure(Binder binder) {

        binder.bind(ZulrahOverlay.class);

    }

    @Provides
    ZulrahConfig getConfig(ConfigManager configManager) {

        return(configManager.getConfig(ZulrahConfig.class));

    }

    @Override
    protected void startUp() {
        overlayManager.add(overlay);
        overlayManager.add(currentPhaseOverlay);
        overlayManager.add(zulrahPrayerOverlay);
    }

    @Override
    protected void shutDown() {
        overlayManager.remove(overlay);
        overlayManager.remove(currentPhaseOverlay);
        overlayManager.remove(zulrahPrayerOverlay);
    }


    @Schedule(
        period = 600,
        unit = ChronoUnit.MILLIS
    )
    public void update() {

        if(!config.enabled() || client.getGameState() != GameState.LOGGED_IN) {
            //log.info("Not logged in");
            return;
        }

        NPC zulrah = findZulrah();

        if(zulrah == null) {

            //log.info("Did not find zulrah");

            if(instance != null) {

                log.info("Zulrah fight over");
                instance = null;

            }

            return;

        }

        if(instance == null) {
            instance = new ZulrahInstance(zulrah);
            log.info("Starting Zulrah Fight");
        }

        ZulrahPhase currentPhase = ZulrahPhase.valueOf(zulrah, instance.getStartLocation());

        if(instance.getPhase() == null) {

            log.info("phase is null, setting to currentPhase");
            instance.setPhase(currentPhase);

        } else if (!instance.getPhase().equals(currentPhase)) {

            ZulrahPhase previousPhase = instance.getPhase();
            instance.setPhase(currentPhase);
            instance.nextStage();

            log.info("Zulrah phase has moved from {} -> {}, stage: {}", previousPhase, currentPhase, instance.getStage());

        }

        ZulrahPattern pattern = instance.getPattern();

        if(pattern == null)  {

            //log.info("Pattern is null");
            
            int potential = 0;
            ZulrahPattern potentialPattern = null;

            for(ZulrahPattern p : patterns) {

                if(p.stageMatches(instance.getStage(), instance.getPhase())) {
                    potential++;
                    potentialPattern = p;
                    log.info("potential pattern found {}", p.toString());
                }

            }

            //found the correct pattern
            if(potential == 1) {
                instance.setPattern(potentialPattern);
                log.info("Pattern identified {}", potentialPattern);
            }

        } else if (pattern.canReset(instance.getStage()) && (instance.getPhase() == null || instance.getPhase().equals(pattern.get(0)))) {
            
            log.info("Reset Zulrah pattern");

            instance.reset();

        }

   }

    private NPC findZulrah() {
        //log.info("entering find zulrah");
        for(NPC n : client.getNpcs()) {
            if(n.getName() != null) {
                //log.info("Zulrah: NPC '{}''", n.getName());
                if(n.getName().equals("Zulrah")) {
                    //log.info("Found Zulrah");
                    return(n);
                }
            }

        };

        return(null);

    }

    public ZulrahInstance getInstance() {
        return(instance);
    }


}