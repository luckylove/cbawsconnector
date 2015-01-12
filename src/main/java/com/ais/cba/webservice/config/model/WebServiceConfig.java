package com.ais.cba.webservice.config.model;

import java.util.HashMap;
import java.util.Map;

/**
 * User: son.nguyen
 * Date: 10/23/13
 * Time: 10:22 PM
 */
public class WebServiceConfig {

    private Map<String, Service> serviceMap = new HashMap<String, Service>();


    public Service looupService(String name) {
        return this.serviceMap.get(name);
    }

    public Map<String, Service> getServiceMap() {
        return serviceMap;
    }

    public void setServiceMap(Map<String, Service> serviceMap) {
        this.serviceMap = serviceMap;
    }

    public void addService(Service service) {
        this.serviceMap.put(service.getName(), service);
    }
}
