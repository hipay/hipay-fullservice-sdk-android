package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 16/02/16.
 */
public class OperationTest {

    private Operation operation;

    @Before
    public void createInstance() throws Exception {

        operation = new Operation();
    }

    @Test
    public void testGetters() throws Exception {

        operation.getOperation();
    }

    @Test
    public void testSetOperation() throws Exception {

        operation.setOperation(null);
    }
}