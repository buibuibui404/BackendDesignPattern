package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

/**
 * save the schedule information for a order
 */
public interface Schedule {
    int getNumberOfQuarters();
    Schedule copy();
}
