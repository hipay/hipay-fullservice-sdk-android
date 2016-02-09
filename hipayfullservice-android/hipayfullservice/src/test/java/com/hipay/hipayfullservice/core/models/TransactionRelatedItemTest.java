package com.hipay.hipayfullservice.core.models;

import org.junit.Before;
import org.junit.Test;

/**
 * Created by nfillion on 16/02/16.
 */
public class TransactionRelatedItemTest {

    private TransactionRelatedItem transactionRelatedItem;

    @Before
    public void createInstance() throws Exception {

        transactionRelatedItem = new TransactionRelatedItem();
    }

    @Test
    public void testGetters() throws Exception {

        transactionRelatedItem.getTest();
        transactionRelatedItem.getMid();
        transactionRelatedItem.getAuthorizationCode();
        transactionRelatedItem.getTransactionReference();
        transactionRelatedItem.getDateCreated();
        transactionRelatedItem.getDateUpdated();
        transactionRelatedItem.getDateAuthorized();
        transactionRelatedItem.getStatus();
        transactionRelatedItem.getMessage();
        transactionRelatedItem.getAuthorizedAmount();
        transactionRelatedItem.getCapturedAmount();
        transactionRelatedItem.getRefundedAmount();
        transactionRelatedItem.getDecimals();
        transactionRelatedItem.getCurrency();

    }

    @Test
    public void testSetters() throws Exception {

        transactionRelatedItem.setTest(null);
        transactionRelatedItem.setMid(null);
        transactionRelatedItem.setAuthorizationCode(null);
        transactionRelatedItem.setTransactionReference(null);
        transactionRelatedItem.setDateCreated(null);
        transactionRelatedItem.setDateUpdated(null);
        transactionRelatedItem.setDateAuthorized(null);
        transactionRelatedItem.setStatus(null);
        transactionRelatedItem.setMessage(null);
        transactionRelatedItem.setAuthorizedAmount(null);
        transactionRelatedItem.setCapturedAmount(null);
        transactionRelatedItem.setRefundedAmount(null);
        transactionRelatedItem.setDecimals(null);
        transactionRelatedItem.setCurrency(null);

    }
}
