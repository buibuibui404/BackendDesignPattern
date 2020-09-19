package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

/**
 * save the critical information for a order
 */
public interface CriticalInfo {
    double getCriticalLoading();
    boolean isCritical();
    CriticalInfo copy();
}
