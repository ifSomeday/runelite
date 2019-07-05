package net.runelite.client.plugins.zul;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;

@ConfigGroup("This plugin is broken")
public interface ZulrahConfig extends Config {

    @ConfigItem(
        keyName = "enabled",
        name = "Enabled",
        description = "Display overlays"
    )
    default boolean enabled() {
        return(true);
    }

}