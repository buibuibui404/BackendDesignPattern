package au.edu.sydney.cpa.erp.feaa.ordering.implementor;


public class ScheduleImpl implements Schedule {
    private int numQuarters;

    public ScheduleImpl(int numQuarters) {
        this.numQuarters = numQuarters;
    }

    @Override
    public int getNumberOfQuarters() {
        return this.numQuarters;
    }

    @Override
    public Schedule copy() {
        return new ScheduleImpl(numQuarters);
    }
}
