package au.edu.sydney.cpa.erp.feaa.ordering.implementor;

import java.time.LocalDateTime;

/**
 * save the basic information for a order
 */
public interface BasicInfo {
    int getId();
    int getClient();
    LocalDateTime getDate();
    BasicInfo copy();
}
