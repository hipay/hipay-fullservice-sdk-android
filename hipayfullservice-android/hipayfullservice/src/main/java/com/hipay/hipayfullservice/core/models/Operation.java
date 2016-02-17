package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapBehaviour;

import org.json.JSONObject;

/**
 * Created by nfillion on 25/01/16.
 */
public class Operation {

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


    //TODO don't forget it extends TransactionRelatedItem
    public static class HostedPaymentPageMapper extends TransactionRelatedItem.TransactionRelatedItemMapper {
        public HostedPaymentPageMapper(JSONObject jsonObject) {
            super(jsonObject);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapBehaviour) {

                // it extends from TransactionRelated.
                if (super.isValid()) {
                    if (this.getStringForKey("operation") != null) {
                        return true;
                    }
                }
            }

            return false;
        }

        protected Object mappedObject() {

            //TODO build operation object from transactionRelatedItem
            Operation object = new Operation();

            String operationTypeString = this.getLowercaseStringForKey("operation");
            OperationType operationType = OperationType.fromStringValue(operationTypeString);
            if (operationType == null) {
                operationType = OperationType.OperationTypeUnknown;
            }
            object.setOperation(operationType);

            return object;

        }
    }
}
