package com.ais.cba.webservice.config.model;

public class Env {

    private String name;
    private String keyStore;

    private WebServiceConfig webServiceConfig;



    public WebServiceConfig getWebServiceConfig() {
        return webServiceConfig;
    }

    public void setWebServiceConfig(WebServiceConfig webServiceConfig) {
        this.webServiceConfig = webServiceConfig;
    }

    public String getKeyStore() {
        return keyStore;
    }

    public void setKeyStore(String keyStore) {
        this.keyStore = keyStore;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


}
