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
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

    public String lerDiretorio(String path) throws IOException {
        File f = new File(path);
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String linhasDeInfo = " ";
        int i = 0;
        if (f.isDirectory()) {
            File[] listFiles = f.listFiles();
            String href = "";
            for (File listFile : listFiles) {
                Path pathTo = Paths.get(listFile.getAbsolutePath());
                BasicFileAttributes bfa = Files.readAttributes(pathTo, BasicFileAttributes.class);
                FileTime ft = bfa.creationTime();
                FileTime ftModificado = bfa.lastModifiedTime();
                String dataCriacao = df.format(ft.toMillis());
                String dataModificacao = df.format(ftModificado.toMillis());
                if (listFile.isDirectory()) {
                    href = listFile.getAbsolutePath();
                    linhasDeInfo += "<tr><td>" + i + "</td><td><a href=\"" + href + " \"> " + listFile.getName() + "</a></td> "
                            + "<td>" + bfa.size() + " bytes</td><td>" + dataCriacao + "</td>"
                            + "<td>" + dataModificacao + "</td></tr> \n";
                } else {
                    linhasDeInfo += "<tr><td>" + i + "</td><td>" + listFile.getName() + "</td> "
                            + "<td>" + bfa.size() + " bytes</td><td>" + dataCriacao + "</td>"
                            + "<td>" + dataModificacao + "</td></tr> \n";
                }
                i++;
            }
        } else {
            System.out.println("não");
        }
        return linhasDeInfo;
    }
}
