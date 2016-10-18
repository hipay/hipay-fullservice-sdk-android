package com.hipay.fullservice.core.models;

/**
 * Created by nfillion on 25/01/16.
 */
public class Operation extends AbstractModel {

    protected OperationType operation;

    public enum OperationType {

        OperationTypeUnknown ("unknown"),
        OperationTypeCapture ("capture"),
        OperationTypeRefund ("refund"),
        OperationTypeCancel ("cancel"),
        OperationTypeAcceptChallenge ("acceptChallenge"),
        OperationTypeDenyChallenge ("denyChallenge");

        protected final String type;
        OperationType(String type) {
            this.type = type;
        }

        public String getStringValue() {
            return this.type;
        }

        public static OperationType fromStringValue(String value) {

            if (value == null) return null;

            if (value.equalsIgnoreCase(OperationTypeCapture.getStringValue())) {
                return OperationTypeCapture;
            }

            if (value.equalsIgnoreCase(OperationTypeRefund.getStringValue())) {
                return OperationTypeRefund;
            }

            if (value.equalsIgnoreCase(OperationTypeCancel.getStringValue())) {
                return OperationTypeCancel;
            }

            if (value.equalsIgnoreCase(OperationTypeAcceptChallenge.getStringValue())) {
                return OperationTypeAcceptChallenge;
            }

            if (value.equalsIgnoreCase(OperationTypeDenyChallenge.getStringValue())) {
                return OperationTypeDenyChallenge;
            }

            return null;
        }
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {

        this.operation = operation;
    }

}
