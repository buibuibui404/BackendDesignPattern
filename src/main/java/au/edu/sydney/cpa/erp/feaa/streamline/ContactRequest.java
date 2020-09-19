package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public interface ContactRequest {
    /**
     * @return return the token
     */
    AuthToken getToken();

    /**
     * @return return the client
     */
    Client getClient();

    /**
     * @return return hte contact method
     */
    ContactMethod getContactMethod();

    /**
     * @return return the data need to be sent
     */
    String getData();
}
