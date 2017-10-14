/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        System.out.println(input.toString());
        input.toString();
    }
    @Override
    public void run() {
        try {
            input = sok.getInputStream();
            output = sok.getOutputStream();
            codex(input);
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }

    }


}
