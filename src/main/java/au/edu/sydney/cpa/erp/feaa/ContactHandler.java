package au.edu.sydney.cpa.erp.feaa;

import au.edu.sydney.cpa.erp.auth.AuthToken;
import au.edu.sydney.cpa.erp.feaa.streamline.*;
import au.edu.sydney.cpa.erp.ordering.Client;

import java.util.Arrays;
import java.util.List;

public class ContactHandler {
    private static ContactHandlerNode contactHandler;

    static  {
        contactHandler = new SMSHandler();
        ContactHandlerNode mailHandlerNode = new MailHandler();
        ContactHandlerNode emailHandlerNode = new EmailHandler();
        ContactHandlerNode phoneCallHandlerNode = new PhoneCallHandler();
        ContactHandlerNode internal_accountingHandlerNode = new InternalAccountingHandler();
        ContactHandlerNode carrier_PigeonHandlerNode = new CarrierPigeonHandler();

        contactHandler.setSuccessor(mailHandlerNode);
        mailHandlerNode.setSuccessor(emailHandlerNode);
        emailHandlerNode.setSuccessor(phoneCallHandlerNode);
        phoneCallHandlerNode.setSuccessor(internal_accountingHandlerNode);
        internal_accountingHandlerNode.setSuccessor(carrier_PigeonHandlerNode);
    }

    /**
     * send the invoice to target client
     * @param token the token for security
     * @param client the client need to be informed
     * @param priority a list ContactMethod which is ordered
     * @param data the data need to be sents
     * @return true if send successfully, false if not
     */

    public static boolean sendInvoice(AuthToken token, Client client, List<ContactMethod> priority, String data) {
        for (ContactMethod method : priority) {
            ContactRequest request = new ContactRequestImpl(token, client, method, data);

            boolean r = contactHandler.handleRequest(request);
            if (r) {
                return true;
            }
        }
        return false;
    }

    /**
     * get known methods
     * @return a list of string which means contact methods
     */
    public static List<String> getKnownMethods() {
        return Arrays.asList(
                "Carrier Pigeon",
                "Email",
                "Mail",
                "Internal Accounting",
                "Phone call",
                "SMS"
        );
    }
}
