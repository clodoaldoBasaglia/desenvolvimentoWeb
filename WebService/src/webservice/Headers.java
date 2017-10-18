/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.util.Date;

/**
 *
 * @author clodoaldo
 */
public class Headers {

    public Headers() {

    }

    public String BasicHeader() {
        Date d = new Date();
        long time = d.getTime();
        return "HTTP/1.1 200 OK\r\n"
                + "Date: " + time + "\r\n"
                + "Last-Modified: \r\n"
                + "Content-Length: \r\n"
                + "Set-Cookie: cookieName=cookieValue;\r\n"
                + "Content-Type: text/html \r\n\r\n";
    }

    public String RequestIdentification() {
        Date d = new Date();
        long time = d.getTime();
        return "HTTP/1.1 401 OK\r\n"
                + "Date: " + time + "\r\n"
                +"Authorization', Basic cm9vdDpyb290"
                + "Last-Modified: \r\n"
                + "Content-Length: \r\n"
                + "Set-Cookie: cookieName=cookieValue;\r\n"
                + "Content-Type: text/html \r\n\r\n";
    }

}
