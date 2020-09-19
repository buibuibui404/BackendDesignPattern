package au.edu.sydney.cpa.erp.feaa.reports;

import au.edu.sydney.cpa.erp.feaa.flyWeight.ReportFlyWeight;
import au.edu.sydney.cpa.erp.feaa.flyWeight.ReportFlyWeightFactory;
import au.edu.sydney.cpa.erp.feaa.flyWeight.ReportFlyWeightFactoryImpl;
import au.edu.sydney.cpa.erp.ordering.Report;

import java.util.Arrays;

public class ReportImpl implements Report {
    /**
     * create a flyWeightFactory to reduce the total memory
     */

    private static ReportFlyWeightFactory factory = new ReportFlyWeightFactoryImpl();

    private String name;
    private double commissionPerEmployee;
    private double[] legalData;
    private double[] cashFlowData;
    private double[] mergesData;
    private double[] tallyingData;
    private double[] deductionsData;

    public ReportImpl(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData) {
        this.name = name;

        if (factory.hasReport(name,
                commissionPerEmployee,
                legalData,
                cashFlowData,
                mergesData,
                tallyingData,
                deductionsData)) {
            ReportFlyWeight reportFlyWeight = factory.getFlyWeight(name,
                    commissionPerEmployee,
                    legalData,
                    cashFlowData,
                    mergesData,
                    tallyingData,
                    deductionsData);

            this.commissionPerEmployee = reportFlyWeight.commissionPerEmployee();
            this.legalData = reportFlyWeight.getLegalData();
            this.cashFlowData = reportFlyWeight.getCashFlowData();
            this.mergesData = reportFlyWeight.getMergesData();
            this.tallyingData = reportFlyWeight.getTallyingData();
            this.deductionsData = reportFlyWeight.getDeductionsData();
        } else {
            factory.createFlyWeight(name,
                    commissionPerEmployee,
                    legalData,
                    cashFlowData,
                    mergesData,
                    tallyingData,
                    deductionsData);
            this.commissionPerEmployee = commissionPerEmployee;
            this.legalData = legalData;
            this.cashFlowData = cashFlowData;
            this.mergesData = mergesData;
            this.tallyingData = tallyingData;
            this.deductionsData = deductionsData;
        }
    }

    @Override
    public String getReportName() {
        return name;
    }

    @Override
    public double getCommission() {
        return commissionPerEmployee;
    }

    @Override
    public double[] getLegalData() {
        return legalData;
    }

    @Override
    public double[] getCashFlowData() {
        return cashFlowData;
    }

    @Override
    public double[] getMergesData() {
        return mergesData;
    }

    @Override
    public double[] getTallyingData() {
        return tallyingData;
    }

    @Override
    public double[] getDeductionsData() {
        return deductionsData;
    }

    @Override
    public String toString() {

        return String.format("%s", name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ReportImpl report = (ReportImpl) o;
        return report.hashCode() == this.hashCode();
    }

    @Override
    public int hashCode() {
        int result = 0;
        result += name.hashCode();
        result += Double.hashCode(getCommission());
        result += Arrays.hashCode(getCashFlowData());
        result += Arrays.hashCode(getCashFlowData());
        result += Arrays.hashCode(getMergesData());
        result += Arrays.hashCode(getTallyingData());
        result += Arrays.hashCode(getDeductionsData());

        return result;
    }
}
