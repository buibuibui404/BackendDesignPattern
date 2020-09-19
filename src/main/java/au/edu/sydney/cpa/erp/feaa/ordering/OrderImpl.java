package au.edu.sydney.cpa.erp.feaa.ordering;

import au.edu.sydney.cpa.erp.feaa.ordering.implementor.BasicInfo;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.CriticalInfo;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.TypeInfo;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.util.Set;

public class OrderImpl implements Order {
    protected BasicInfo basicInfo;
    protected CriticalInfo criticalInfo;
    protected TypeInfo typeInfo;

    public OrderImpl(BasicInfo basicInfo, CriticalInfo criticalInfo, TypeInfo typeInfo) {
        this.basicInfo = basicInfo;
        this.criticalInfo = criticalInfo;
        this.typeInfo = typeInfo;
    }

    @Override
    public int getOrderID() {
        return this.basicInfo.getId();
    }

    @Override
    public double getTotalCommission() {
        double temp = this.typeInfo.getTotalCommission();
        if (this.criticalInfo.isCritical()) {
            temp += temp * this.criticalInfo.getCriticalLoading();
        }
        return temp;
    }

    @Override
    public LocalDateTime getOrderDate() {
        return this.basicInfo.getDate();
    }

    @Override
    public void setReport(Report report, int employeeCount) {
        this.typeInfo.setReport(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return this.typeInfo.getAllReports();
    }

    @Override
    public int getReportEmployeeCount(Report report) {
        return this.typeInfo.getReportEmployeeCount(report);
    }

    @Override
    public String generateInvoiceData() {
        if (this.criticalInfo.isCritical()) {
            return this.typeInfo.generateInvoiceDataCritical(getTotalCommission());
        }
        return this.typeInfo.generateInvoiceData();
    }

    @Override
    public int getClient() {
        return this.basicInfo.getClient();
    }

    @Override
    public void finalise() {
        this.typeInfo.finalise();
    }

    @Override
    public Order copy() {
        return new OrderImpl(basicInfo.copy(), criticalInfo.copy(), typeInfo.copy());
    }

    @Override
    public String shortDesc() {
        if (this.criticalInfo.isCritical()) {
            return this.typeInfo.shortDescCritical(this.basicInfo.getId(), getTotalCommission());
        }
        return this.typeInfo.shortDesc(this.basicInfo.getId(), getTotalCommission());
    }

    @Override
    public String longDesc() {
        if (this.criticalInfo.isCritical()) {
            return this.typeInfo.longDescCritical(this.basicInfo.getId(), this.basicInfo.getDate(), getTotalCommission());
        }
        return this.typeInfo.longDesc(this.basicInfo.getId(), this.basicInfo.getDate(), getTotalCommission());
    }
}
