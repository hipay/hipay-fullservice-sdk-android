package com.hipay.hipayfullservice.core.client.config;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by nfillion on 21/01/16.
 */
public class ClientConfig {

    private static ClientConfig mInstance = null;

    private Environment environment;
    private String username;
    private String password;
    private String userAgent;
    private URL appRedirectionURL;


    public static final String GatewayClientBaseURLStage = "https://stage-secure-gateway.hipay-tpp.com/rest/v1";
    public static final String GatewayClientBaseURLProduction = "https://secure-gateway.hipay-tpp.com/rest/v1";

    public static final String SecureVaultClientBaseURLStage = "https://stage-secure-vault.hipay-tpp.com/rest/v1";
    public static final String SecureVaultClientBaseURLProduction = "https://secure-vault.hipay-tpp.com/rest/v1";


    private ClientConfig() {
    }

    public static ClientConfig getInstance(){
        if(mInstance == null)
        {
            mInstance = new ClientConfig();
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

            if (value == Stage.getIntegerValue()) {
                return Stage;
            }

            if (value == Production.getIntegerValue()) {
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


    public void setConfigEnvironment(Environment environment, String username, String password) {

        this.setEnvironment(environment);
        this.setUsername(username);
        this.setPassword(password);

        //TODO determine useragent
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
}
