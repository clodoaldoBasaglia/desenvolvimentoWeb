/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 *
 * @author clodoaldo
 */
public class OrdoProcessios implements Runnable {

    private final Socket sok;
    InputStream input;
    OutputStream output;

    OrdoProcessios(Socket sok) {
        this.sok = sok;
    }

    private void codex(InputStream input) {
        String pathToHtml = new File("").getAbsolutePath();
        try {
            Arquivo arq = null;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String request = bufferedReader.readLine();
            String texto = "";
            Request pedido = new Request();
            System.out.println("primeira linha: "+request);
            ArrayList<String> aux = new ArrayList<String>();
            if (request.split(" ")[1].equalsIgnoreCase("/")) {
                arq = new Arquivo(pathToHtml + "/src/html/bemvindo.html");
                this.output.write(makeHeader().getBytes());
                this.output.write(arq.openFile().getBytes());
            }
            if (request.contains("GET")) {
                pedido.setHost(bufferedReader.readLine().split(" ")[1]);
                pedido.setConnection(bufferedReader.readLine().split(" ")[1]);
                pedido.setChacheControl(bufferedReader.readLine().split(" ")[1]);
                pedido.setUserAgent(bufferedReader.readLine().split(" ")[1]);
                pedido.setUpgradeSegureRequest(bufferedReader.readLine().split(" ")[1]);
                while ((texto = bufferedReader.readLine()) != null) {
                    aux.add(texto);
                }
                pedido.setAccept(aux);
                System.out.println(pedido.toString());
                metodoGet(pedido);
            } else if (request.contains("POST")) {

            }
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void run() {
        try {
            this.input = sok.getInputStream();
            this.output = sok.getOutputStream();
            codex(this.input);
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void metodoGet(Request pedido) {
        System.out.println(pedido.toString());
    }

    private String makeHeader() {
        Date d = new Date();
        long time = d.getTime();
        return "HTTP/1.1 200 OK\r\n"
                + "Date: " + time + "\r\n"
                + "Last-Modified: \r\n"
                + "Content-Length: \r\n"
                + "Set-Cookie: cookieName=cookieValue;\r\n"
                + "Content-Type: text/html \r\n\r\n";
    }

}
