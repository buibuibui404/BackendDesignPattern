package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.Mail;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class MailHandler implements ContactHandlerNode {
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.MAIL) {
            String address = contactRequest.getClient().getAddress();
            String suburb = contactRequest.getClient().getSuburb();
            String state = contactRequest.getClient().getState();
            String postcode = contactRequest.getClient().getPostCode();

            if (address != null && suburb != null && state != null && postcode != null) {
                Mail.sendInvoice(contactRequest.getToken(),
                        contactRequest.getClient().getFName(),
                        contactRequest.getClient().getLName(),
                        contactRequest.getData(),
                        address,
                        suburb,
                        state,
                        postcode);
                return true;
            }
        }

        if (successor == null) {
            return false;
        }
        return this.successor.handleRequest(contactRequest);
    }
}
