package com.project.eveningshuttle.model.runtime;

public class StudentPassenger implements Passenger {

    private final int suid;
    private final int address;

    public StudentPassenger(int suid, int address) {
        this.suid = suid;
        this.address = address;
    }

    @Override
    public int getSuid() {
        return suid;
    }

    @Override
    public int getAddress() {
        return address;
    }
}
