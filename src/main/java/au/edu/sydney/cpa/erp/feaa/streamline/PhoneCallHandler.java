package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.PhoneCall;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class PhoneCallHandler implements ContactHandlerNode{
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.PHONECALL) {
            String phone = contactRequest.getClient().getPhoneNumber();

            if (phone != null) {
                PhoneCall.sendInvoice(contactRequest.getToken(),
                        contactRequest.getClient().getFName(),
                        contactRequest.getClient().getLName(),
                        contactRequest.getData(),
                        phone);
            }
        }

        if (successor == null) {
            return false;
        }

        return this.successor.handleRequest(contactRequest);
    }
}
