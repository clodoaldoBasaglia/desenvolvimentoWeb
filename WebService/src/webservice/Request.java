/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

/**
 *
 * @author clodoaldo
 */
public class Request {

    private String host;
    private String connection;
    private String chacheControl;
    private String userAgent;
    private String upgradeSegureRequest;
    private String[] accept;

    public Request() {
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getConnection() {
        return connection;
    }

    public void setConnection(String connection) {
        this.connection = connection;
    }

    public String getChacheControl() {
        return chacheControl;
    }

    public void setChacheControl(String chacheControl) {
        this.chacheControl = chacheControl;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }

    public String getUpgradeSegureRequest() {
        return upgradeSegureRequest;
    }

    public void setUpgradeSegureRequest(String upgradeSegureRequest) {
        this.upgradeSegureRequest = upgradeSegureRequest;
    }

    public String[] getAccept() {
        return accept;
    }

    public void setAccept(String[] accept) {
        this.accept = accept;
    }

}
