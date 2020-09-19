package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.InternalAccounting;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class InternalAccountingHandler implements ContactHandlerNode{
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.INTERNAL_ACCOUNTING) {
            String internalAccounting = contactRequest.getClient().getInternalAccounting();
            String businessName = contactRequest.getClient().getBusinessName();

            if (internalAccounting != null && businessName != null) {
                InternalAccounting.sendInvoice(contactRequest.getToken(),
                        contactRequest.getClient().getFName(),
                        contactRequest.getClient().getLName(),
                        contactRequest.getData(),
                        internalAccounting,
                        businessName);
            }
        }

        if (successor == null) {
            return false;
        }

        return this.successor.handleRequest(contactRequest);
    }
}
