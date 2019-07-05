package net.runelite.client.plugins.notifier;

import net.runelite.client.config.Config;
import net.runelite.client.config.ConfigGroup;
import net.runelite.client.config.ConfigItem;
import net.runelite.client.config.Range;
import net.runelite.client.plugins.notifier.config.NotifierNotifyOperator;
import net.runelite.client.plugins.notifier.config.NotifierNotifyStat;

@ConfigGroup("notifiers")
public interface NotifierConfig extends Config {
    
    @ConfigItem(
        position = 1,
        keyName = "emailAddress",
        name = "PushBullet Email",
        description = "The email address of the PushBullet account to send push notifications to"
    )
    default String emailAddress() {
        return("test@example.com");
    }

    @ConfigItem(
        position = 2,
        keyName = "notifyCondition",
        name = "Notify When",
        description = "Configures what to watch for to send notifications"
    )
    default NotifierNotifyStat notifyCondition() {
        return(NotifierNotifyStat.HITPOINTS);
    }

    @ConfigItem(
        position = 3,
        keyName = "notifyOperation",
        name = "Notify Operator",
        description = "Configures what comparison to use to determine when to Notify"
    )
    default NotifierNotifyOperator notifyOperation() {
        return(NotifierNotifyOperator.GREATER_THAN_OR_EQUAL);
    }

    @ConfigItem(
        position = 4,
        keyName = "threshold",
        name = "Threshold",
        description = "The threshold to notify on"
    )
    @Range(
        max = 99
    )
    default int threshold() {
        return(5);
    }

    @ConfigItem(
        position = 5,
        keyName = "pause",
        name = "Pause Notifications",
        description = "Use this when changing notification settings to prevent spam"
    )
    default boolean pause() {
        return(false);
    }


}