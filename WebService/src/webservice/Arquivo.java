/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clodoaldo
 */
public class Arquivo {

    String path;

    public Arquivo(String path) {
        this.path = path;
    }

    public String openFile() {
        try {
            String conteudo = "";
            File f = new File(this.path);
            int i = 0;
            String linhas = " ";
            BufferedReader bufferedReader = new BufferedReader(new FileReader(this.path));
            while ((conteudo = bufferedReader.readLine()) != null) {
                linhas += conteudo;
                i++;
            }
            return linhas;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return "Something went wrong";
    }
}
