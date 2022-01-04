package com.hipay.fullservice.core.client.config;

/**
 * Created by HiPay on 21/01/16.
 */
public class ClientConfig {

    private static ClientConfig mInstance = null;

    private Environment environment;
    private String username;
    private String password;
    private String userAgent;

    private boolean paymentCardStorageEnabled;
    private boolean paymentCardScanEnabled;

    public static final String GatewayClientBaseURLStage =      "https://stage-secure-gateway.hipay-tpp.com/rest/v1";
    public static final String GatewayClientBaseURLNewStage =   "https://stage-secure-gateway.hipay-tpp.com/rest/v2";

    public static final String GatewayClientBaseURLProduction =  "https://secure-gateway.hipay-tpp.com/rest/v1";
    public static final String GatewayClientBaseURLNewProduction =  "https://secure-gateway.hipay-tpp.com/rest/v2";

    public static final String SecureVaultClientBaseURLStage = "https://stage-secure2-vault.hipay-tpp.com/rest/v2";
    public static final String SecureVaultClientBaseURLProduction = "https://secure2-vault.hipay-tpp.com/rest/v2";

    private ClientConfig() {
    }

    public static ClientConfig getInstance(){
        if(mInstance == null)
        {
            mInstance = new ClientConfig();
            mInstance.setPaymentCardStorageEnabled(true);
            mInstance.setPaymentCardScanEnabled(true);
        }
        return mInstance;
    }

    public enum Environment {

        Stage (0),
        Production (1);

        protected final Integer status;
        Environment(Integer status) {

            this.status = status;
        }

        public static Environment fromIntegerValue(Integer value) {

            if (value.equals(Stage.getIntegerValue())) {
                return Stage;
            }

            if (value.equals(Production.getIntegerValue())) {
                return Production;
            }

            return null;
        }

        public Integer getIntegerValue() {
            return this.status;
        }
    }

    public Environment getEnvironment() {
        return environment;
    }

    public void setConfig(Environment environment, String username, String password) {

        this.setEnvironment(environment);
        this.setUsername(username);
        this.setPassword(password);

        this.setUserAgent(null);
    }

    @Deprecated
    public void setConfig(Environment environment, String username, String password, boolean paymentCardStorageEnabled) {

        this.setEnvironment(environment);
        this.setUsername(username);
        this.setPassword(password);
        this.setPaymentCardStorageEnabled(paymentCardStorageEnabled);

        this.setUserAgent(null);
    }

    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public boolean isPaymentCardStorageEnabled() {
        return paymentCardStorageEnabled;
    }

    public void setPaymentCardStorageEnabled(boolean paymentCardStorageEnabled) {
        this.paymentCardStorageEnabled = paymentCardStorageEnabled;
    }

    public boolean isPaymentCardScanEnabled() {
        return paymentCardScanEnabled;
    }

    public void setPaymentCardScanEnabled(boolean paymentCardScanEnabled) {
        this.paymentCardScanEnabled = paymentCardScanEnabled;
    }
}
