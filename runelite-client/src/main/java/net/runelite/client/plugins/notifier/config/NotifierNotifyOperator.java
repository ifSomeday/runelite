package net.runelite.client.plugins.notifier.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum NotifierNotifyOperator {
    
    EQUAL("=="),
    GREATER_THAN(">"),
    GREATER_THAN_OR_EQUAL(">="),
    LESS_THAN("<"),
    LESS_THAN_OR_EQUAL("<=");

    private final String name;

    @Override
    public String toString() {
        return(name);
    }
}