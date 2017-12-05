/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

/**
 *
 * @author a968692
 */
public class Amigo {
    private String enderco;
    private String portaHttp;

    public Amigo(String enderco, String portaHttp) {
        this.enderco = enderco;
        this.portaHttp = portaHttp;
    }
    public Amigo(){
        
    }
    @Override
    public String toString() {
        return "Amigo{" + "enderco=" + enderco + ", portaHttp=" + portaHttp + '}';
    }

    
    public String getEnderco() {
        return enderco;
    }

    public void setEnderco(String enderco) {
        this.enderco = enderco;
    }

    public String getPortaHttp() {
        return portaHttp;
    }

    public void setPortaHttp(String portaHttp) {
        this.portaHttp = portaHttp;
    }
    
    
    
}
