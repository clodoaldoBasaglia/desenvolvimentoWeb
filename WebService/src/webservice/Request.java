/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.util.ArrayList;

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
    private ArrayList<String> accept;

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

    @Override
    public String toString() {
        return "Request{" + "host=" + host + ", connection=" + connection + ", chacheControl=" + chacheControl + ", userAgent=" + userAgent + ", upgradeSegureRequest=" + upgradeSegureRequest + ", accept=" + accept + '}';
    }

    public ArrayList<String> getAccept() {
        return accept;
    }

    public void setAccept(ArrayList<String> accept) {
        this.accept = accept;
    }

    public String printAccept() {
        String aux = "";
        for (String string : accept) {
            aux += string + "\n";
        }
        return aux;
    }
}
