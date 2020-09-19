package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.Email;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class EmailHandler implements ContactHandlerNode {
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.EMAIL) {
            String email = contactRequest.getClient().getEmailAddress();
            if (email != null) {
                Email.sendInvoice(contactRequest.getToken(),
                        contactRequest.getClient().getFName(),
                        contactRequest.getClient().getLName(),
                        contactRequest.getData(),
                        email);
                return true;
            }
        }

        if (successor == null) {
            return false;
        }

        return this.successor.handleRequest(contactRequest);
    }
}
