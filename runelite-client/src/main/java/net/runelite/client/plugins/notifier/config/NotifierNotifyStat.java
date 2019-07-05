package net.runelite.client.plugins.notifier.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotifierNotifyStat {
    NONE("None"),
    HITPOINTS("HP"),
    PRAYER("Prayer");

    private final String name;

    @Override
    public String toString() {
        return(name);
    }
}