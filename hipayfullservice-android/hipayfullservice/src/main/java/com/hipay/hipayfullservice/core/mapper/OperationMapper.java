package com.hipay.hipayfullservice.core.mapper;

import com.hipay.hipayfullservice.core.mapper.interfaces.MapMapper;
import com.hipay.hipayfullservice.core.models.Operation;

/**
 * Created by nfillion on 08/09/16.
 */
public class OperationMapper extends AbstractMapper {

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
        Operation.OperationType operationType = Operation.OperationType.fromStringValue(operationTypeString);
        if (operationType == null) {
            operationType = Operation.OperationType.OperationTypeUnknown;
        }
        object.setOperation(operationType);

        return object;

    }

    @Override
    protected Operation mappedObjectFromBundle() {
        return null;
    }
}

