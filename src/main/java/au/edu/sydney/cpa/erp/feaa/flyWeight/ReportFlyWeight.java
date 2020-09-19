package au.edu.sydney.cpa.erp.feaa.flyWeight;

/**
 * the flyweight for a report which can be shared through different report to reduce the memory cost
 */
public interface ReportFlyWeight {
    /**
     * @return get the report name
     */
    String getName();

    /**
     * @return get the total commission
     */
    double commissionPerEmployee();

    /**
     * @return get the legal data
     */
    double[] getLegalData();

    /**
     * @return get the cash flow data
     */
    double[] getCashFlowData();

    /**
     * @return get the merge data
     */
    double[] getMergesData();

    /**
     * @return get the tallying data
     */
    double[] getTallyingData();

    /**
     * @return get the deductions data
     */
    double[] getDeductionsData();
}
