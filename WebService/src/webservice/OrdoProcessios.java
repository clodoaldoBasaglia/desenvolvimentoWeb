/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
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
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
            String request = bufferedReader.readLine();
            String texto = "";
            Request pedido = new Request();
            if (request.contains("GET")) {
                pedido.setHost(bufferedReader.readLine().split(" ")[1]);
                pedido.setConnection(bufferedReader.readLine().split(" ")[1]);
                pedido.setChacheControl(bufferedReader.readLine().split(" ")[1]);
                pedido.setUserAgent(bufferedReader.readLine().split(" ")[1]);
                pedido.setUpgradeSegureRequest(bufferedReader.readLine().split(" ")[1]);
                String[] aux = {bufferedReader.readLine().split(" ")[1], bufferedReader.readLine().split(" ")[1], bufferedReader.readLine().split(" ")[1]};
                pedido.setAccept(aux);
            }
            metodoGet(pedido);
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
    }

}
