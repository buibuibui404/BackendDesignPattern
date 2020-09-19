package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.database.TestDatabase;
import au.edu.sydney.cpa.erp.ordering.Client;

public class ClientImpl implements Client {

    private final int id;
    private String fName;
    private String lName;
    private String phoneNumber;
    private String emailAddress;
    private String address;
    private String suburb;
    private String state;
    private String postCode;
    private String internalAccounting;
    private String businessName;
    private String pigeonCoopID;
    private AuthToken token;

    public ClientImpl(AuthToken token, int id) {
        this.id = id;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    @Override
    public String getFName() {
        if (fName == null) {
            this.fName = TestDatabase.getInstance().getClientField(token, id, "fName");
        }
        return fName;
    }

    @Override
    public String getLName() {
        if (lName == null) {
            this.lName = TestDatabase.getInstance().getClientField(token, id, "lName");
        }
        return lName;
    }

    @Override
    public String getPhoneNumber() {
        if (phoneNumber == null) {
            this.phoneNumber = TestDatabase.getInstance().getClientField(token, id, "phoneNumber");
        }
        return phoneNumber;
    }

    @Override
    public String getEmailAddress() {
        if (emailAddress == null) {
            this.emailAddress = TestDatabase.getInstance().getClientField(token, id, "emailAddress");
        }
        return emailAddress;
    }

    @Override
    public String getAddress() {
        if (address == null) {
            this.address = TestDatabase.getInstance().getClientField(token, id, "address");
        }
        return address;
    }

    @Override
    public String getSuburb() {
        if (suburb == null) {
            this.suburb = TestDatabase.getInstance().getClientField(token, id, "suburb");
        }
        return suburb;
    }

    @Override
    public String getState() {
        if (state == null) {
            this.state = TestDatabase.getInstance().getClientField(token, id, "state");
        }
        return state;
    }

    @Override
    public String getPostCode() {
        if (postCode == null) {
            this.postCode = TestDatabase.getInstance().getClientField(token, id, "postCode");
        }
        return postCode;
    }

    @Override
    public String getInternalAccounting() {
        if (internalAccounting == null) {
            this.internalAccounting = TestDatabase.getInstance().getClientField(token, id, "internal accounting");
        }
        return internalAccounting;
    }

    @Override
    public String getBusinessName() {
        if (businessName == null) {
            this.businessName = TestDatabase.getInstance().getClientField(token, id, "businessName");
        }
        return businessName;
    }

    @Override
    public String getPigeonCoopID() {
        if (pigeonCoopID == null) {
            this.pigeonCoopID = TestDatabase.getInstance().getClientField(token, id, "pigeonCoopID");
        }
        return pigeonCoopID;
    }
}

