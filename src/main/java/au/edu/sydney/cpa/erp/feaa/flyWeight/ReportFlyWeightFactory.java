package au.edu.sydney.cpa.erp.feaa.flyWeight;

/**
 * The report flyweight factory
 */
public interface ReportFlyWeightFactory {
    /**
     * check if the factory has target flyweight
     * @param name
     * @param commissionPerEmployee
     * @param legalData
     * @param cashFlowData
     * @param mergesData
     * @param tallyingData
     * @param deductionsData
     * @return
     */
    boolean hasReport(String name,
                      double commissionPerEmployee,
                      double[] legalData,
                      double[] cashFlowData,
                      double[] mergesData,
                      double[] tallyingData,
                      double[] deductionsData);

    /**
     * get target flyweight
     * @param name
     * @param commissionPerEmployee
     * @param legalData
     * @param cashFlowData
     * @param mergesData
     * @param tallyingData
     * @param deductionsData
     * @return
     */
    ReportFlyWeight getFlyWeight(String name,
                                 double commissionPerEmployee,
                                 double[] legalData,
                                 double[] cashFlowData,
                                 double[] mergesData,
                                 double[] tallyingData,
                                 double[] deductionsData);

    /**
     * create a new flyweight
     * @param name
     * @param commissionPerEmployee
     * @param legalData
     * @param cashFlowData
     * @param mergesData
     * @param tallyingData
     * @param deductionsData
     */
    void createFlyWeight(String name,
                         double commissionPerEmployee,
                         double[] legalData,
                         double[] cashFlowData,
                         double[] mergesData,
                         double[] tallyingData,
                         double[] deductionsData);
}
