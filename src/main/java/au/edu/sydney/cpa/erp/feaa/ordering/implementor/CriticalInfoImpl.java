package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

import au.edu.sydney.cpa.erp.feaa.ordering.implementor.CriticalInfo;

public class CriticalInfoImpl implements CriticalInfo {
    private double criticalLoading;
    private boolean isCritical;

    public CriticalInfoImpl(boolean isCritical, double criticalLoading) {
        this.criticalLoading = criticalLoading;
        this.isCritical =  isCritical;
    }

    @Override
    public double getCriticalLoading() {
        if (getIsCritical()) {
            return this.criticalLoading;
        }
        return 1;
    }

    @Override
    public boolean isCritical() {
        return isCritical;
    }

    @Override
    public CriticalInfo copy() {
        return new CriticalInfoImpl(isCritical, criticalLoading);
    }

    private boolean getIsCritical() {
        return this.isCritical;
    }
}
