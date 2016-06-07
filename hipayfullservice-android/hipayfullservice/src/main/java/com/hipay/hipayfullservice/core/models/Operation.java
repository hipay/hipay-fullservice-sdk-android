package com.hipay.hipayfullservice.core.models;

import com.hipay.hipayfullservice.core.mapper.AbstractMapper;
import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;

/**
 * Created by nfillion on 25/01/16.
 */
public class Operation extends AbstractModel {

    //TODO useful for maintenance operations. handle that better
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

    public static class OperationMapper extends AbstractMapper {

        public OperationMapper(Object rawData) {
            super(rawData);
        }

        @Override
        protected boolean isValid() {

            if (this.getBehaviour() instanceof MapMapper) {

                if (this.getStringForKey("operation") != null) {
                    return true;
                }
            }

            return false;
        }

        protected Operation mappedObject() {

            Operation object = new Operation();

            String operationTypeString = this.getLowercaseStringForKey("operation");
            OperationType operationType = OperationType.fromStringValue(operationTypeString);
            if (operationType == null) {
                operationType = OperationType.OperationTypeUnknown;
            }
            object.setOperation(operationType);

            return object;

        }

        @Override
        protected Operation mappedObjectFromBundle() {
            return null;
        }
    }
}
