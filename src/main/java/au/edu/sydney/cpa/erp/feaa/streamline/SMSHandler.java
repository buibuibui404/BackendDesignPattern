package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.SMS;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class SMSHandler implements ContactHandlerNode{
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.SMS) {
            String smsPhone = contactRequest.getClient().getPhoneNumber();
            if (smsPhone != null) {
                SMS.sendInvoice(contactRequest.getToken(),
                        contactRequest.getClient().getFName(),
                        contactRequest.getClient().getLName(),
                        contactRequest.getData(),
                        smsPhone);
                return true;
            }
        }

        if (successor == null) {
            return false;
        }

        return this.successor.handleRequest(contactRequest);
    }
}
