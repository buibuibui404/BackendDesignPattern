package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;
import au.edu.sydney.cpa.erp.ordering.Client;

public class ContactRequestImpl implements ContactRequest{
    private AuthToken token;
    private Client client;
    private ContactMethod contactMethod;
    private String data;

    public ContactRequestImpl(AuthToken token, Client client, ContactMethod contactMethod, String data) {
        this.token = token;
        this.client = client;
        this.contactMethod = contactMethod;
        this.data = data;
    }

    @Override
    public AuthToken getToken() {
        return this.token;
    }

    @Override
    public Client getClient() {
        return this.client;
    }

    @Override
    public ContactMethod getContactMethod() {
        return this.contactMethod;
    }

    @Override
    public String getData() {
        return this.data;
    }
}
