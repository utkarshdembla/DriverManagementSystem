package com.app.enums;

public enum OnboardingStatus {
    DOCUMENT_SUBMITTED(1),
    DOCUMENT_COLLECTED(2),
    BACKGROUND_VERIFICATION_STARTED(3),
    BACKGROUND_VERIFICATION_COMPLETED(4),
    TRACKING_DEVICE_READY(5),
    TRACKING_DEVICE_SHIPPED(6),
    TRACKING_DEVICE_DELIVERED(7),
    COMPLETED(8);

    public final int value;

    OnboardingStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }

    public static OnboardingStatus findByValue(int value) {
        OnboardingStatus result = null;
        for (OnboardingStatus val : values()) {
            if(value==val.getValue())
                result = val;
        }
        return result;
    }

}
