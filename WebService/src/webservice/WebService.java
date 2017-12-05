package webservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clodoaldo http://www.starwars.com/news/40-memorable-star-wars-quotes
 * https://www.youtube.com/watch?v=PJ5s81NRdQE
 */
public class WebService extends Thread {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("I have awoken.");
        List<Amigo> arrayDeAmigo = new ArrayList();
        try {
            ServerSocket ss = new ServerSocket(8082, 50);
            while (true) {
                new Thread(new BroadcastSender()).start();
                new Thread(new BroadcastListener(arrayDeAmigo)).start();
                new Thread(new Unicast(arrayDeAmigo)).start();
                Socket sok = ss.accept();
                new Thread(new OrdoProcessios(sok,arrayDeAmigo)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
