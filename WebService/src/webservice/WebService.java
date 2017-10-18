package webservice;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;



/**
 *
 * @author clodoaldo
 * http://www.starwars.com/news/40-memorable-star-wars-quotes
 * https://www.youtube.com/watch?v=PJ5s81NRdQE
 */
public class WebService extends Thread{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        System.out.println("I have awoken.");
        try {
            ServerSocket ss = new ServerSocket(8082,50);
            while(true){
                Socket sok = ss.accept();
                new Thread(new OrdoProcessios(sok)).start();
            }
        } catch (IOException ex) {
            Logger.getLogger(WebService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
