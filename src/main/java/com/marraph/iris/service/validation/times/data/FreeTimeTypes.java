package com.marraph.iris.service.validation.times.data;

import lombok.Getter;

@Getter
public enum FreeTimeTypes {

    MINUTES_15(15),
    MINUTES_30(30),
    MINUTES_45(45),
    MINUTES_60(60),
    HOURS_1_5(90),
    HOURS_2(120),
    HOURS_2_5(150),
    HOURS_3(180);

    private final int value;

    FreeTimeTypes(int value) {
        this.value = value;
    }

    public static int getCustomTime(int duration, TimeUnits unit) {
        return  (unit == TimeUnits.HOURS) ? duration / 60 : duration;
    }

}