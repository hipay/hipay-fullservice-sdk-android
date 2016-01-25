package com.hipay.hipayfullservice.core.client;

/**
 * Created by nfillion on 21/01/16.
 */
public class ClientConfig {

    private static ClientConfig mInstance = null;

    private String environment;
    private String username;
    private String password;
    private String userAgent;

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

    public String getEnvironment() {
        return environment;
    }

    public void setEnvironment(String environment, String username, String password, String appURLscheme) {

        this.setEnvironment(environment);
        this.setUserAgent(username);
        this.setPassword(password);

        // TODO URLSCHEME

    }

    public void setEnvironment(String environment) {
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
