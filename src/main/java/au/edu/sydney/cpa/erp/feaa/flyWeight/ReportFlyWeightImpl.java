package au.edu.sydney.cpa.erp.feaa.flyWeight;


public class ReportFlyWeightImpl implements ReportFlyWeight{
    private String name;
    private double commissionPerEmployee;
    private double[] legalData;
    private double[] cashFlowData;
    private double[] mergesData;
    private double[] tallyingData;
    private double[] deductionsData;

    public ReportFlyWeightImpl(String name,
                               double commissionPerEmployee,
                               double[] legalData,
                               double[] cashFlowData,
                               double[] mergesData,
                               double[] tallyingData,
                               double[] deductionsData) {
        this.name = name;
        this.commissionPerEmployee = commissionPerEmployee;
        this.legalData = legalData;
        this.cashFlowData = cashFlowData;
        this.mergesData = mergesData;
        this.tallyingData = tallyingData;
        this.deductionsData = deductionsData;
    }

    @Override
    public String getName() {
        return name;
    }


    @Override
    public double commissionPerEmployee() {
        return commissionPerEmployee;
    }

    @Override
    public double[] getLegalData() {
        return this.legalData;
    }

    @Override
    public double[] getCashFlowData() {
        return this.cashFlowData;
    }

    @Override
    public double[] getMergesData() {
        return this.mergesData;
    }

    @Override
    public double[] getTallyingData() {
        return this.tallyingData;
    }

    @Override
    public double[] getDeductionsData() {
        return this.deductionsData;
    }
}
