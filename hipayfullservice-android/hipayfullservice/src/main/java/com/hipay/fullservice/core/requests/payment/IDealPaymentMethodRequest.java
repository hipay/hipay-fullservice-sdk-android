package com.hipay.fullservice.core.requests.payment;

/**
 * Created by nfillion on 03/02/16.
 */
public class IDealPaymentMethodRequest extends AbstractPaymentMethodRequest {

    public IDealPaymentMethodRequest() {
    }

    public IDealPaymentMethodRequest(String issuerBankId) {
        this.issuerBankId = issuerBankId;
    }

    protected String issuerBankId;

    public String getIssuerBankId() {
        return issuerBankId;
    }

    public void setIssuerBankId(String issuerBankId) {
        this.issuerBankId = issuerBankId;
    }

    /*

    + (NSDictionary *)issuerBanks
{
    return @{
             @"ABNANL2A": @"ABN AMRO",
             @"INGBNL2A": @"ING",
             @"RABONL2U": @"Rabobank",
             @"SNSBNL2A": @"SNS Bank",
             @"ASNBNL21": @"ASN Bank",
             @"FRBKNL2L": @"Friesland Bank",
             @"KNABNL2H": @"Knab",
             @"RBRBNL21": @"SNS Regio Bank",
             @"TRIONL2U": @"Triodos bank",
             @"FVLBNL22": @"Van Lanschot"
             };
}

     */

}
