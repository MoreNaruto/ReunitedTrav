package com.tmorris.reunitedtrav.models.enums;

public enum Status {
    PREPARATION(false, false),
    PENDING_CONFIRMATION(false, false),
    CONFIRMED(true, false),
    IN_PROGRESS(true, false),
    COMPLETED(true, true),
    CANCELLED(false, true),
    UNPROCESSABLE(false, true);

    private final Boolean needEventTimeSet;
    private final Boolean finalState;

    Status(Boolean needEventTimeSet, Boolean finalState) {
        this.needEventTimeSet = needEventTimeSet;
        this.finalState = finalState;
    }

    public Boolean eventTimeRequired() {
        return needEventTimeSet;
    }

    public Boolean cantUpdateStatus() {
        return finalState;
    }
}
