package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthModule;
import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.feaa.orderUnit.UoWOrder;
import au.edu.sydney.cpa.erp.feaa.orderUnit.UoWOrderImpl;
import au.edu.sydney.cpa.erp.feaa.ordering.OrderImpl;
import au.edu.sydney.cpa.erp.feaa.ordering.ScheduledOrderImpl;
import au.edu.sydney.cpa.erp.feaa.ordering.implementor.*;
import au.edu.sydney.cpa.erp.ordering.Client;
import au.edu.sydney.cpa.erp.ordering.Order;
import au.edu.sydney.cpa.erp.ordering.Report;
import au.edu.sydney.cpa.erp.feaa.reports.ReportDatabase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@SuppressWarnings("Duplicates")
public class FEAAFacade {
    private AuthToken token;
    private static UoWOrder uowManager;
    private static Thread thread;

    /**
     * login and setUp thread and uowManaegr
     * @param userName username
     * @param password password
     * @return true if success false if not success
     */

    public boolean login(String userName, String password) {
        token = AuthModule.login(userName, password);
        uowManager = new UoWOrderImpl(token);
        thread = new Thread(uowManager);

        return null != token;
    }

    /**
     * get all order from database
     * @return a List of order ID
     */

    public List<Integer> getAllOrders() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();

        List<Order> orders = database.getOrders(token);

        List<Integer> result = new ArrayList<>();

        for (Order order : orders) {
            result.add(order.getOrderID());
        }

        return result;
    }

    /**
     * create a new order
     * @param clientID the id from client for this ord
     * @param date  the data of order
     * @param isCritical if the order is critical
     * @param isScheduled if the order is scheduled
     * @param orderType the type of the order
     * @param criticalLoadingRaw critical load raw
     * @param maxCountedEmployees max number of employees
     * @param numQuarters number of quarters
     * @return return the new order id
     */

    public Integer createOrder(int clientID, LocalDateTime date, boolean isCritical, boolean isScheduled, int orderType, int criticalLoadingRaw, int maxCountedEmployees, int numQuarters) {
        if (null == token) {
            throw new SecurityException();
        }

        double criticalLoading = criticalLoadingRaw / 100.0;

        Order order;

        if (!TestDatabase.getInstance().getClientIDs(token).contains(clientID)) {
            throw new IllegalArgumentException("Invalid client ID");
        }

        int id = TestDatabase.getInstance().getNextOrderID();

        TypeInfo typeInfo;
        CriticalInfo criticalInfo;
        BasicInfo basicInfo = new BasicInfoImpl(id, clientID, date);

        switch (orderType) {
            case 1:
                typeInfo = new FirstOrderType(maxCountedEmployees);
                break;
            case 2:
                typeInfo = new AuditOrderType();
                break;
            default:
                return null;
        }

        criticalInfo = new CriticalInfoImpl(isCritical, criticalLoading);

        if (isScheduled) {
            Schedule schedule1 = new ScheduleImpl(numQuarters);
            order = new ScheduledOrderImpl(basicInfo, criticalInfo, typeInfo, schedule1);
        } else {
            order = new OrderImpl(basicInfo, criticalInfo, typeInfo);
        }

        uowManager.registerNew(token, id, order);
        return order.getOrderID();
    }

    /**
     * get all client id
     * @return a list of all client id
     */
    public List<Integer> getAllClientIDs() {
        if (null == token) {
            throw new SecurityException();
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.getClientIDs(token);
    }

    /**
     * get target client
     * @param id target client
     * @return the client with target ID
     */
    public Client getClient(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        return new ClientImpl(token, id);
    }

    /**
     * remove target order
     * @param id the order to be removed
     * @return true if removed fasle if not
     */

    public boolean removeOrder(int id) {
        if (null == token) {
            throw new SecurityException();
        }

        if (uowManager.hasOrderLocal(id)) {
            uowManager.delete(token, id);
        }

        TestDatabase database = TestDatabase.getInstance();
        return database.removeOrder(token, id);
    }

    /**
     * get all reports
     * @return a list of Report
     */

    public List<Report> getAllReports() {
        if (null == token) {
            throw new SecurityException();
        }

        return new ArrayList<>(ReportDatabase.getTestReports());
    }

    public boolean finaliseOrder(int orderID, List<String> contactPriority) {
        if (null == token) {
            throw new SecurityException();
        }

        List<ContactMethod> contactPriorityAsMethods = new ArrayList<>();

        if (null != contactPriority) {
            for (String method: contactPriority) {
                switch (method.toLowerCase()) {
                    case "internal accounting":
                        contactPriorityAsMethods.add(ContactMethod.INTERNAL_ACCOUNTING);
                        break;
                    case "email":
                        contactPriorityAsMethods.add(ContactMethod.EMAIL);
                        break;
                    case "carrier pigeon":
                        contactPriorityAsMethods.add(ContactMethod.CARRIER_PIGEON);
                        break;
                    case "mail":
                        contactPriorityAsMethods.add(ContactMethod.MAIL);
                        break;
                    case "phone call":
                        contactPriorityAsMethods.add(ContactMethod.PHONECALL);
                        break;
                    case "sms":
                        contactPriorityAsMethods.add(ContactMethod.SMS);
                        break;
                    default:
                        break;
                }
            }
        }

        if (contactPriorityAsMethods.size() == 0) { // needs setting to default
            contactPriorityAsMethods = Arrays.asList(
                    ContactMethod.INTERNAL_ACCOUNTING,
                    ContactMethod.EMAIL,
                    ContactMethod.CARRIER_PIGEON,
                    ContactMethod.MAIL,
                    ContactMethod.PHONECALL
            );
        }

        Order order;

        if (uowManager.hasOrderLocal(orderID)) {
            order = uowManager.getOrderLocal(token, orderID);
        } else {
            order = uowManager.getOrderRemote(token, orderID);
        }
        order.finalise();
//        uowManager.commit(token);
        thread.start();

        return ContactHandler.sendInvoice(token, getClient(order.getClient()), contactPriorityAsMethods, order.generateInvoiceData());
    }

    /**
     * logout the FEAAFacade
     */

    public void logout() {
        uowManager.commit(token);
        AuthModule.logout(token);
        token = null;
    }

    /**
     * get the total commission for target order
     * @param orderID target order ID
     * @return the total commission for the order
     */

    public double getOrderTotalCommission(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order = TestDatabase.getInstance().getOrder(token, orderID);
        if (null == order) {
            return 0.0;
        }

        return order.getTotalCommission();
    }

    /**
     * set a report for a order
     * @param orderID target order ID
     * @param report the report need to be assigned
     * @param numEmployees the number of employees
     */

    public void orderLineSet(int orderID, Report report, int numEmployees) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order;

        if (uowManager.hasOrderLocal(orderID)) {
            order = uowManager.getOrderLocal(token, orderID);
        } else {
            order = uowManager.getOrderRemote(token, orderID);
        }

        if (null == order) {
            System.out.println("got here");
            return;
        }

        order.setReport(report, numEmployees);
        uowManager.registerDirty(orderID);
    }

    /**
     * return the long desc of a order
     * @param orderID target order id
     * @return long desc
     */

    public String getOrderLongDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order;
        if (uowManager.hasOrderLocal(orderID)) {
            order = uowManager.getOrderLocal(token, orderID);
        } else {
            order = TestDatabase.getInstance().getOrder(token, orderID);
        }

        if (null == order) {
            return null;
        }

        return order.longDesc();
    }

    /**
     * return the short desc of a order
     * @param orderID target order id
     * @return short desc
     */

    public String getOrderShortDesc(int orderID) {
        if (null == token) {
            throw new SecurityException();
        }

        Order order;
        if (uowManager.hasOrderLocal(orderID)) {
            order = uowManager.getOrderLocal(token, orderID);
        } else {
            order = TestDatabase.getInstance().getOrder(token, orderID);
        }

        if (null == order) {
            return null;
        }

        return order.shortDesc();
    }

    /**
     * return a list of known contact methods
     * @return a list of current contact methods
     */
    public List<String> getKnownContactMethods() {
        if (null == token) {
            throw new SecurityException();
        }
        return ContactHandler.getKnownMethods();
    }
}
