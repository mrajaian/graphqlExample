package com.mimr.graphqlExample.recordschedule;

public class ProgramInfo {

    private final String id, userId, programId;
    private final boolean isAdultContent;

    public ProgramInfo(String id, String userId, String programId, boolean isAdultContent) {
        this.id = id;
        this.userId = userId;
        this.programId = programId;
        this.isAdultContent = isAdultContent;
    }

    public String getId() {
        return id;
    }

    public String getUserId() {
        return userId;
    }

    public String getProgramId() {
        return programId;
    }

    public boolean isAdultContent() {
        return isAdultContent;
    }
}
