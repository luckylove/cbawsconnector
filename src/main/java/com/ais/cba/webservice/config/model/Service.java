package com.ais.cba.webservice.config.model;

import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * User: son.nguyen
 * Date: 10/24/13
 * Time: 1:22 PM
 */
public class Service {

    private String name;
    private String endpoint;
    private String username;
    private String password;
    private int timeout;

    private Map<String, Param> params = new HashMap<String, Param>(5);

    public int getTimeout() {
        if (timeout == 0) {
            return 4500;
        }
        return timeout*1000 - 500;
    }

    public void setTimeout(int timeout) {
        this.timeout = timeout;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEndpoint() {
        if (StringUtils.isNotEmpty(endpoint)) {
            return this.endpoint.trim();
        }
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
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

    public Map<String, Param> getParams() {
        return params;
    }

    public void setParams(Map<String, Param> params) {
        this.params = params;
    }

    public void addParam(Param param) {
        this.params.put(param.getName(), param);
    }
}
