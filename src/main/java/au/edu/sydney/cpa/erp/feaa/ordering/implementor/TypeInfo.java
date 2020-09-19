package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.util.Set;

/**
 * save the type info for a order
 */
public interface TypeInfo {
    TypeInfo copy();
    double getTotalCommission();
    void setReport(Report report, int employeeCount);
    Set<Report> getAllReports();
    int getReportEmployeeCount(Report report);
    void finalise();

    // store the invoice data for different type
    String generateInvoiceData();
    String generateInvoiceDataCritical(double totalCommission);
    String generateInvoiceDataSchedule(double recurringCost, double totalCommission);
    String generateInvoiceDataCriticalSchedule(double recurringCost, int numQuarters, double totalCommission);

    // store all the short or long desc info
    String shortDesc(int id, double totalCommission);
    String longDesc(int id, LocalDateTime date, double totalCommission);

    String shortDescCritical(int id, double totalCommission);
    String longDescCritical(int id, LocalDateTime date, double totalCommission);

    String shortDescSchedule(int id, double recurringCost, double totalCommission);
    String longDescSchedule(int id, LocalDateTime date, int numQuarters, double superCommission, double localCommission);

    String shortDescScheduleCritical(int id, double recurringCost, double totalCommission);
    String longDescScheduleCritical(int id, LocalDateTime date, int numQuarters, double superCommission, double localCommission);

}
