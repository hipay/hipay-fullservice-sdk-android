package com.hipay.fullservice.core.models;

import org.junit.Before;
import org.junit.Test;

public class SepaDirectDebitTest {

    private SepaDirectDebit sepaDirectDebit;

    @Before
    public void createInstance() throws Exception {

        sepaDirectDebit = new SepaDirectDebit();
    }

    @Test
    public void testGetters() throws Exception {

        sepaDirectDebit.getFirstname();
        sepaDirectDebit.getLastname();
        sepaDirectDebit.getIban();
        sepaDirectDebit.getRecurringPayment();
        sepaDirectDebit.getGender();
        sepaDirectDebit.getBankName();
        sepaDirectDebit.getIssuerBankId();
        sepaDirectDebit.getDebitAgreementId();
    }

    @Test
    public void testSetters() throws Exception {

        sepaDirectDebit.setFirstname(null);
        sepaDirectDebit.setLastname(null);
        sepaDirectDebit.setIban(null);
        sepaDirectDebit.setRecurringPayment(null);
        sepaDirectDebit.setGender(null);
        sepaDirectDebit.setBankName(null);
        sepaDirectDebit.setIssuerBankId(null);
        sepaDirectDebit.setDebitAgreementId(null);
    }
}
