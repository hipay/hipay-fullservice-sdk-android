package com.hipay.hipayfullservice.core.client;

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

    private static final String ClientConfigCallbackURLHost = "hipay-fullservice";

    public static final String GatewayClientBaseURLStage = "https://stage-secure-gateway.hipay-tpp.com/rest/v1/";
    public static final String GatewayClientBaseURLProduction = "https://secure-gateway.hipay-tpp.com/rest/v1/";
    public static final String GatewayCallbackURLPathName = "gateway";
    public static final String GatewayCallbackURLOrderPathName = "gateway";

    // TODO URLScheme

    private ClientConfig() {

        this.username = "94654727.api.hipay-tpp.com";
        this.password = "3g4zRCgG2EY9RJHFsQ4cIqAI";
    }

    public static ClientConfig getInstance(){
        if(mInstance == null)
        {
            mInstance = new ClientConfig();
        }
        return mInstance;
    }

    private URL urlSchemeWithString(String schemeString) {

        StringBuilder appURLSchemeBuilder = new StringBuilder(schemeString);
        appURLSchemeBuilder.append("://");
        appURLSchemeBuilder.append(ClientConfigCallbackURLHost);

        try {
            return new URL(appURLSchemeBuilder.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();

            return null;
        }
    }

    public enum Environment {

        EnvironmentStage (0),
        EnvironmentProduction (1);

        protected final Integer status;
        Environment(Integer status) {

            this.status = status;
        }

        public static Environment fromIntegerValue(Integer value) {

            if (value == EnvironmentStage.getIntegerValue()) {
                return EnvironmentStage;
            }

            if (value == EnvironmentProduction.getIntegerValue()) {
                return EnvironmentProduction;
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


    public void setConfigEnvironment(Environment environment, String username, String password, String appURLscheme) {

        this.setEnvironment(environment);
        this.setUsername(username);
        this.setPassword(password);
        this.setAppRedirectionURL(this.urlSchemeWithString(appURLscheme));

        this.setUserAgent(username);

        //TODO determine useragent
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

    public URL getAppRedirectionURL() {
        return appRedirectionURL;
    }

    public void setAppRedirectionURL(URL appRedirectionURL) {
        this.appRedirectionURL = appRedirectionURL;
    }
}
