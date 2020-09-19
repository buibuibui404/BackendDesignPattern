package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

import au.edu.sydney.cpa.erp.feaa.ordering.implementor.BasicInfo;

import java.time.LocalDateTime;

public class BasicInfoImpl implements BasicInfo {
    private final int id;
    private LocalDateTime date;
    private int client;

    public BasicInfoImpl(int id, int client, LocalDateTime date) {
        this.id = id;
        this.date = date;
        this.client = client;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public int getClient() {
        return this.client;
    }

    @Override
    public LocalDateTime getDate() {
        return this.date;
    }

    @Override
    public BasicInfo copy() {
        return new BasicInfoImpl(id, client, date);
    }
}
