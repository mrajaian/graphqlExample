package com.mimr.graphqlExample.recordschedule;

public class User {

    private final String id, name, country;
    private final ProgramInfo[] favProgramInfo;

    public User(String id, String name, String country, ProgramInfo[] favProgramInfo) {
        this.id = id;
        this.name = name;
        this.country = country;
        this.favProgramInfo = favProgramInfo;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public ProgramInfo[] getRecordSchedules() {
        return favProgramInfo;
    }
}
