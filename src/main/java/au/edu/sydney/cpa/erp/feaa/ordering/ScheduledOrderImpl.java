package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.feaa.ordering.implementor.BasicInfo;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.CriticalInfo;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.Schedule;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.TypeInfo;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.ScheduledOrder;

public class ScheduledOrderImpl extends OrderImpl implements ScheduledOrder {
    private Schedule schedule;

    public ScheduledOrderImpl(BasicInfo basicInfo, CriticalInfo criticalInfo, TypeInfo typeInfo, Schedule schedule) {
        super(basicInfo, criticalInfo, typeInfo);
        this.schedule = schedule;
    }

    @Override
    public Order copy() {
        return new ScheduledOrderImpl(this.basicInfo, this.criticalInfo, this.typeInfo, this.schedule);
    }

    @Override
    public double getRecurringCost() {
        return super.getTotalCommission();
    }

    @Override
    public double getTotalCommission() {
        return super.getTotalCommission() * this.schedule.getNumberOfQuarters();
    }

    @Override
    public int getNumberOfQuarters() {
        return this.schedule.getNumberOfQuarters();
    }

    @Override
    public String generateInvoiceData() {
        if (!this.criticalInfo.isCritical()) {
            return this.typeInfo.generateInvoiceDataSchedule(getRecurringCost(), getTotalCommission());
        }
        return this.typeInfo.generateInvoiceDataCriticalSchedule(getRecurringCost(), this.schedule.getNumberOfQuarters(), getTotalCommission());
    }

    @Override
    public String shortDesc() {
        if (this.criticalInfo.isCritical()) {
            return this.typeInfo.shortDescScheduleCritical(this.basicInfo.getId(), getRecurringCost(), getTotalCommission());
        }
        return this.typeInfo.shortDescSchedule(this.basicInfo.getId(), getRecurringCost(), getTotalCommission());
    }

    @Override
    public String longDesc() {
        if (this.criticalInfo.isCritical()) {
            return this.typeInfo.longDescScheduleCritical(this.basicInfo.getId(), this.basicInfo.getDate(), this.schedule.getNumberOfQuarters(), super.getTotalCommission(), this.getTotalCommission());
        }
        return this.typeInfo.longDescSchedule(this.basicInfo.getId(), this.basicInfo.getDate(), this.schedule.getNumberOfQuarters(), super.getTotalCommission(), this.getTotalCommission());
    }
}
