package au.edu.sydney.cpa.erp.feaa.orderUnit;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Order;

import java.util.HashMap;
import java.util.concurrent.locks.ReentrantLock;

public class UoWOrderImpl implements UoWOrder{
    private HashMap<Integer, Order> newOrders = new HashMap<>();
    private HashMap<Integer, Order> cleanOrders = new HashMap<>();
    private HashMap<Integer, Order> dirtyOrders = new HashMap<>();
    private AuthToken token;
    private ReentrantLock lock;

    public UoWOrderImpl(AuthToken token) {
        this.token = token;
        this.lock = new ReentrantLock();
    }

    @Override
    public void commit(AuthToken token) {
        this.lock.lock();
        cleanOrders.clear();

        TestDatabase testDatabase = TestDatabase.getInstance();
        for (Order order: newOrders.values()) {
            testDatabase.saveOrder(token, order);
        }

        for (Order order: dirtyOrders.values()) {
            testDatabase.saveOrder(token, order);
        }

        newOrders.clear();
        dirtyOrders.clear();
        this.lock.lock();
    }

    @Override
    public boolean hasOrderLocal(int id) {
        this.lock.lock();
        boolean b = newOrders.containsKey(id) || cleanOrders.containsKey(id) ||
                dirtyOrders.containsKey(id);
        this.lock.unlock();
        return b;
    }

    @Override
    public Order getOrderLocal(AuthToken token, int id) {
        this.lock.lock();
        if (newOrders.containsKey(id)) {
            this.lock.unlock();
            return newOrders.get(id);
        } else if (cleanOrders.containsKey(id)) {
            this.lock.unlock();
            return cleanOrders.get(id);
        } else if (dirtyOrders.containsKey(id)) {
            this.lock.unlock();
            return dirtyOrders.get(id);
        }
        this.lock.unlock();
        return null;
    }

    @Override
    public Order getOrderRemote(AuthToken token, int id) {
        this.lock.lock();
        if (!this.hasOrderLocal(id)) {
            Order temp = TestDatabase.getInstance().getOrder(token, id);
            cleanOrders.put(id, temp);
            this.lock.unlock();
            return temp;
        }
        this.lock.unlock();
        return null;
    }

    @Override
    public void registerNew(AuthToken token, int id, Order order) {
        this.lock.lock();
        newOrders.put(id, order);
        this.lock.unlock();
    }

    @Override
    public void registerDirty(int id) {
        this.lock.lock();
        if (cleanOrders.containsKey(id)) {
            cleanOrders.remove(id);
            dirtyOrders.put(id, cleanOrders.get(id));
        }
        this.lock.unlock();
    }

    @Override
    public void delete(AuthToken token, int id) {
        this.lock.lock();
        if (newOrders.containsKey(id)) {
            newOrders.remove(id);
        } else if (cleanOrders.containsKey(id)) {
            cleanOrders.remove(id);
        } else if (dirtyOrders.containsKey(id)) {
            dirtyOrders.remove(id);
        }
        this.lock.unlock();
    }

    @Override
    public void run() {
        commit(this.token);
    }
}
