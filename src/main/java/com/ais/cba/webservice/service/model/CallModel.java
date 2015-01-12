package com.ais.cba.webservice.service.model;

/**
 * User: son.nguyen
 * Date: 1/4/15
 * Time: 9:00 PM
 */
public class CallModel {

    java.lang.String uui;
    java.lang.String media;
    java.lang.String contact;
    java.lang.String agent;
    java.lang.String alert;
    java.lang.String userinfo;

    public String getUui() {
        return uui;
    }

    public void setUui(String uui) {
        this.uui = uui;
    }

    public String getMedia() {
        return media;
    }

    public void setMedia(String media) {
        this.media = media;
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

    public String getUserinfo() {
        return userinfo;
    }

    public void setUserinfo(String userinfo) {
        this.userinfo = userinfo;
    }
}
