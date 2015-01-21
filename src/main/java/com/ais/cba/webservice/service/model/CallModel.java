package com.ais.cba.webservice.service.model;

/**
 * User: son.nguyen
 * Date: 1/4/15
 * Time: 9:00 PM
 */
public class CallModel {

    java.lang.String uui;
    java.lang.String custseg;
    java.lang.String contact;
    java.lang.String agent;
    java.lang.String alert;
    java.lang.String queuing;



    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public String getCustseg() {
        return custseg;
    }

    public void setCustseg(String custseg) {
        this.custseg = custseg;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getAlert() {
        return alert;
    }

    public void setAlert(String alert) {
        this.alert = alert;
    }

    public String getQueuing() {
        return queuing;
    }

    public void setQueuing(String queuing) {
        this.queuing = queuing;
    }
}
