package au.edu.sydney.cpa.erp.feaa.streamline;

public interface ContactHandlerNode {
    /**
     * set the successor for this node
     * @param contactHandler
     */
    void setSuccessor(ContactHandlerNode contactHandler);

    /**
     * try to send invoice using info in the request
     * @param contactRequest the request need to be handle
     * @return true if send successfully, false if not
     */
    boolean handleRequest(ContactRequest contactRequest);
}
