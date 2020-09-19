package au.edu.sydney.cpa.erp.feaa.flyWeight;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ReportFlyWeightFactoryImpl implements ReportFlyWeightFactory{
    private HashMap<Integer, ReportFlyWeight> map = new HashMap<>();
    private Set<Integer> pool = new HashSet<>();


    @Override
    public boolean hasReport(String name,
                             double commissionPerEmployee,
                             double[] legalData,
                             double[] cashFlowData,
                             double[] mergesData,
                             double[] tallyingData,
                             double[] deductionsData) {
        return pool.contains(hashCodeThem(name,
                commissionPerEmployee,
                legalData,
                cashFlowData,
                mergesData,
                tallyingData,
                deductionsData));
    }

    @Override
    public ReportFlyWeight getFlyWeight(String name,
                                        double commissionPerEmployee,
                                        double[] legalData,
                                        double[] cashFlowData,
                                        double[] mergesData,
                                        double[] tallyingData,
                                        double[] deductionsData) {
        return map.get(hashCodeThem(name,
                commissionPerEmployee,
                legalData,
                cashFlowData,
                mergesData,
                tallyingData,
                deductionsData));
    }

    @Override
    public void createFlyWeight(String name,
                                double commissionPerEmployee,
                                double[] legalData,
                                double[] cashFlowData,
                                double[] mergesData,
                                double[] tallyingData,
                                double[] deductionsData) {

        int uniq_code = hashCodeThem(name,
                commissionPerEmployee,
                legalData,
                cashFlowData,
                mergesData,
                tallyingData,
                deductionsData);
        pool.add(uniq_code);

        ReportFlyWeight reportFlyWeight = new ReportFlyWeightImpl(name,
                commissionPerEmployee,
                legalData,
                cashFlowData,
                mergesData,
                tallyingData,
                deductionsData);
        map.put(uniq_code, reportFlyWeight);

    }

    private int hashCodeThem(String name,
                         double commissionPerEmployee,
                         double[] legalData,
                         double[] cashFlowData,
                         double[] mergesData,
                         double[] tallyingData,
                         double[] deductionsData) {

        int r = 0;
        r += name.hashCode();
        r += Double.hashCode(commissionPerEmployee);
        r += Arrays.hashCode(legalData);
        r += Arrays.hashCode(cashFlowData);
        r += Arrays.hashCode(mergesData);
        r += Arrays.hashCode(tallyingData);
        r += Arrays.hashCode(deductionsData);
        return r;
    }


}
