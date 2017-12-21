package com.huajie.educomponent.portal.constants;

public enum ResourceType {

    COURSE(1),
    NOTICE(2);

    private int value;

    ResourceType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
