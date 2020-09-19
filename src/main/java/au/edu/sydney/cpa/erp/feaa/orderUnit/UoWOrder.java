package au.edu.sydney.cpa.erp.feaa.orderUnit;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.ordering.Order;

public interface UoWOrder extends Runnable{

    /**
     * commit all changes or additions from local database to remote database and clear local database
     * @param token token for security
     */
    void commit(AuthToken token);

    /**
     * check if the local database has target order
     * @param id the id of target order
     * @return if it is in local database then return true else return false
     */
    boolean hasOrderLocal(int id);

    /**
     * get target order from UoWOder
     * @param token token for security
     * @param id target order ID
     * @return target order
     */
    Order getOrderLocal(AuthToken token, int id);

    /**
     * get the order from remote database and save it to local data base (UoWOder)
     * @param token token for security
     * @param id target order ID
     * @return target order
     */
    Order getOrderRemote(AuthToken token, int id);

    /**
     * save a new order to local database and upload to
     * @param token token for security
     * @param id the order ID
     * @param order the order to be add
     */
    void registerNew(AuthToken token, int id, Order order);

    /**
     * record a change in local database
     * @param id the order ID
     */
    void registerDirty(int id);

    /**
     * delete target target order from local database
     * @param token token for security
     * @param id the order ID
     */
    void delete(AuthToken token, int id);
}
