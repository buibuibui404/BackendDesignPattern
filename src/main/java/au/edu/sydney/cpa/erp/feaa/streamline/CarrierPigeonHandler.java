package au.edu.sydney.cpa.erp.feaa.streamline;

import au.edu.sydney.cpa.erp.contact.CarrierPigeon;
import au.edu.sydney.cpa.erp.feaa.ContactMethod;

public class CarrierPigeonHandler implements ContactHandlerNode{
    private ContactHandlerNode successor;

    @Override
    public void setSuccessor(ContactHandlerNode contactHandler) {
        this.successor = contactHandler;
    }

    @Override
    public boolean handleRequest(ContactRequest contactRequest) {
        if (contactRequest.getContactMethod() == ContactMethod.CARRIER_PIGEON) {
            String pigeonCoopID = contactRequest.getClient().getPigeonCoopID();
            if (pigeonCoopID != null) {
                CarrierPigeon.
                        sendInvoice(contactRequest.getToken(),
                                contactRequest.getClient().getFName(),
                                contactRequest.getClient().getLName(),
                                contactRequest.getData(),
                                pigeonCoopID);
                return true;
            }
        }

        if (successor == null) {
            return false;
        }
        return this.successor.handleRequest(contactRequest);

    }
}
