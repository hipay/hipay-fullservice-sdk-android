package com.hipay.hipayfullservice.core.models;

/**
 * Created by nfillion on 25/01/16.
 */
public class Operation {

    protected OperationType operation;

    public enum OperationType {

        OperationTypeUnknown,
        OperationTypeCapture,
        OperationTypeRefund,
        OperationTypeCancel,
        OperationTypeAcceptChallenge,
        OperationTypeDenyChallenge
    }

    public OperationType getOperation() {
        return operation;
    }

    public void setOperation(OperationType operation) {
        this.operation = operation;
    }
}
