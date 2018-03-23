package com.hipay.fullservice.core.mapper;

import com.hipay.fullservice.core.mapper.interfaces.MapMapper;
import com.hipay.fullservice.core.models.Operation;

/**
 * Created by nfillion on 08/09/16.
 */
public class OperationMapper extends AbstractMapper {

    public OperationMapper(Object rawData) {
        super(rawData);
    }

    @Override
    public boolean isValid() {
        if (this.getBehaviour() instanceof MapMapper) {
            if (this.getStringForKey("operation") != null) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Operation mappedObject() {
        Operation object = new Operation();

        String operationTypeString = this.getLowercaseStringForKey("operation");
        Operation.OperationType operationType = Operation.OperationType.fromStringValue(operationTypeString);
        if (operationType == null) {
            operationType = Operation.OperationType.OperationTypeUnknown;
        }
        object.setOperation(operationType);

        return object;

    }
}

