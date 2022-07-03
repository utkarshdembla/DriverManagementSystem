package com.app.enums;

public enum AvailabilityStatus {
    AVAILABLE(1),
    NOT_AVAILABLE(2);

    public final int value;

     AvailabilityStatus(int value){
        this.value = value;
    }


}
