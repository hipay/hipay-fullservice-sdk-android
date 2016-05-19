package com.hipay.hipayfullservice.core.models;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * Created by nfillion on 16/02/16.
 */
public class OrderTest {

    private Order order;

    @Before
    public void createInstance() throws Exception {

        order = new Order();
    }

    @Test
    public void testGetters() throws Exception {

        assertThat(null, is(order.getOrderId()));
        assertThat(null, is(order.getDateCreated()));
        assertThat(null, is(order.getAttemps()));
        assertThat(null, is(order.getAmount()));
        assertThat(null, is(order.getShipping()));
        assertThat(null, is(order.getTax()));
        assertThat(null, is(order.getDecimals()));
        assertThat(null, is(order.getCurrency()));
        assertThat(null, is(order.getCustomerId()));
        assertThat(null, is(order.getLanguage()));
        assertThat(null, is(order.getGender()));
        assertThat(null, is(order.getShippingAddress()));
    }

    @Test
    public void testSetters() throws Exception {

        order.setOrderId(null);
        order.setDateCreated(null);
        order.setAttempts(null);
        order.setAmount(null);
        order.setShipping(null);
        order.setTax(null);
        order.setDecimals(null);
        order.setCurrency(null);
        order.setCustomerId(null);
        order.setLanguage(null);
        order.setGender(null);
        order.setShippingAddress(null);

    }

    public void tearDown() throws Exception {
    }

}