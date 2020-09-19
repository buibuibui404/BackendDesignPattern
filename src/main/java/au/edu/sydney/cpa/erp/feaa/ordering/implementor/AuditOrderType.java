package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

import au.edu.sydney.cpa.erp.ordering.Report;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class AuditOrderType implements TypeInfo {
    private Map<Report, Integer> reports = new HashMap<>();
    private boolean finalised = false;

    public AuditOrderType() {
    }

    @Override
    public TypeInfo copy() {
        TypeInfo copy = new AuditOrderType();
        for (Report report : reports.keySet()) {
            copy.setReport(report, reports.get(report));
        }
        return copy;
    }

    @Override
    public double getTotalCommission() {
        double cost = 0.0;
        for (Report report : reports.keySet()) {
            cost += reports.get(report) * report.getCommission();
        }
        return cost;
    }

    @Override
    public void setReport(Report report, int employeeCount) {
        if (finalised) throw new IllegalStateException("Order was already finalised.");

        // We can't rely on equal reports having the same object identity since they get
        // rebuilt over the network, so we have to check for presence and same values

        for (Report contained: reports.keySet()) {
            if (report.equals(contained)) {
                report = contained;
                break;
            }
        }

        reports.put(report, employeeCount);
    }

    @Override
    public Set<Report> getAllReports() {
        return reports.keySet();
    }

    @Override
    public int getReportEmployeeCount(Report report) {
        for (Report contained: reports.keySet()) {
            if (report.equals(contained)) {
                report = contained;
                break;
            }
        }
        Integer result = reports.get(report);
        return null == result ? 0 : result;
    }

    @Override
    public void finalise() {
        this.finalised = true;
    }

    @Override
    public String generateInvoiceData() {
        double baseCommission = 0.0;
        double loadedCommission = getTotalCommission();

        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", getTotalCommission()));
        sb.append("\nPlease see below for details:\n");
        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * reports.get(report);
            baseCommission += subtotal;

            sb.append("\tReport name: ");
            sb.append(report.getReportName());
            sb.append("\tEmployee Count: ");
            sb.append(reports.get(report));
            sb.append("\tCost per employee: ");
            sb.append(String.format("$%,.2f", report.getCommission()));
            sb.append("\tSubtotal: ");
            sb.append(String.format("$%,.2f\n", subtotal));
        }
        return sb.toString();
    }

    @Override
    public String generateInvoiceDataCritical(double totalCommission) {
        return String.format("Your priority business account has been charged: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", totalCommission);
    }

    @Override
    public String generateInvoiceDataSchedule(double recurringCost, double totalCommission) {
        StringBuilder sb = new StringBuilder();

        sb.append("Thank you for your Crimson Permanent Assurance accounting order!\n");
        sb.append("The cost to provide these services: $");
        sb.append(String.format("%,.2f", recurringCost));
        sb.append(" each quarter, with a total overall cost of: $");
        sb.append(String.format("%,.2f", totalCommission));
        sb.append("\nPlease see below for details:\n");

        Map<Report, Integer> reports = this.reports;
        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            sb.append("\tReport name: ");
            sb.append(report.getReportName());
            sb.append("\tEmployee Count: ");
            sb.append(reports.get(report));
            sb.append("\tCost per employee: ");
            sb.append(String.format("$%,.2f", report.getCommission()));
            sb.append("\tSubtotal: ");
            sb.append(String.format("$%,.2f\n", report.getCommission() * reports.get(report)));
        }
        return sb.toString();
    }

    @Override
    public String generateInvoiceDataCriticalSchedule(double recurringCost, int numQuarters, double totalCommission) {
        return String.format("Your priority business account will be charged: $%,.2f each quarter for %d quarters, with a total overall cost of: $%,.2f" +
                "\nPlease see your internal accounting department for itemised details.", recurringCost, numQuarters, totalCommission);
    }


    @Override
    public String shortDesc(int id, double totalCommission) {
        return String.format("ID:%s $%,.2f", id, totalCommission);
    }

    @Override
    public String longDesc(int id, LocalDateTime date, double totalCommission) {
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * reports.get(report);

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f\n",
                    report.getReportName(),
                    reports.get(report),
                    report.getCommission(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString(),
                getTotalCommission()
        );
    }

    @Override
    public String shortDescCritical(int id, double totalCommission) {
        return String.format("ID:%s $%,.2f", id, totalCommission);
    }

    @Override
    public String longDescCritical(int id, LocalDateTime date, double totalCommission) {
        double baseCommission = 0.0;
        double loadedCommission = totalCommission;
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * reports.get(report);
            baseCommission += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f\n",
                    report.getReportName(),
                    reports.get(report),
                    report.getCommission(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Reports:\n" +
                        "%s" +
                        "Critical Loading: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                reportSB.toString(),
                loadedCommission - baseCommission,
                loadedCommission
        );
    }

    @Override
    public String shortDescSchedule(int id, double recurringCost, double totalCommission) {
        return String.format("ID:%s $%,.2f per quarter, $%,.2f total", id, recurringCost, totalCommission);
    }

    @Override
    public String longDescSchedule(int id, LocalDateTime date, int numQuarters, double superCommission, double localCommission) {
        double totalLoadedCost = localCommission;
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(this.reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * this.reports.get(report);

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f\n",
                    report.getReportName(),
                    this.reports.get(report),
                    report.getCommission(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of quarters: %d\n" +
                        "Reports:\n" +
                        "%s" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                numQuarters,
                reportSB.toString(),
                superCommission,
                totalLoadedCost
        );
    }

    @Override
    public String shortDescScheduleCritical(int id, double recurringCost, double totalCommission) {
        return String.format("ID:%s $%,.2f per quarter, $%,.2f total", id, recurringCost, totalCommission);
    }

    @Override
    public String longDescScheduleCritical(int id, LocalDateTime date, int numQuarters, double superCommission, double localCommission) {
        double totalBaseCost = 0.0;
        double loadedCostPerQuarter = superCommission;
        double totalLoadedCost = localCommission;
        StringBuilder reportSB = new StringBuilder();

        List<Report> keyList = new ArrayList<>(this.reports.keySet());
        keyList.sort(Comparator.comparing(Report::getReportName).thenComparing(Report::getCommission));

        for (Report report : keyList) {
            double subtotal = report.getCommission() * this.reports.get(report);
            totalBaseCost += subtotal;

            reportSB.append(String.format("\tReport name: %s\tEmployee Count: %d\tCommission per employee: $%,.2f\tSubtotal: $%,.2f\n",
                    report.getReportName(),
                    this.reports.get(report),
                    report.getCommission(),
                    subtotal));
        }

        return String.format(finalised ? "" : "*NOT FINALISED*\n" +
                        "Order details (id #%d)\n" +
                        "Date: %s\n" +
                        "Number of quarters: %d\n" +
                        "Reports:\n" +
                        "%s" +
                        "Critical Loading: $%,.2f\n" +
                        "Recurring cost: $%,.2f\n" +
                        "Total cost: $%,.2f\n",
                id,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE),
                numQuarters,
                reportSB.toString(),
                totalLoadedCost - (totalBaseCost * numQuarters),
                loadedCostPerQuarter,
                totalLoadedCost
        );
    }

}
