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
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
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

    public String aboutServer() {
        DateFormat df = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        String data = df.format(date);
        File f = new File("info.ss");
        String conteudo = "";
        if (f.isFile() && f.isFile()) {
            FileReader fr = null;
            try {
                fr = new FileReader(f);
                BufferedReader bufferedReader = new BufferedReader(fr);
                conteudo = bufferedReader.readLine();
                String helper = bufferedReader.readLine() + "\n";
                int pos = helper.lastIndexOf(":") + 1;
//                   fr.close();
                String substring = helper.substring(pos, helper.length());
//                int numero = Integer.parseInt(substring.trim());
//                FileWriter fw = new FileWriter(f);
//                helper = helper.replace(substring.trim(), String.valueOf(++numero));
                conteudo += "</br>" + helper;
//                fw.write(conteudo);
//                fw.write(helper);
//                fw.close();
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fr.close();
                } catch (IOException ex) {
                    Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        } else {
            FileWriter fw = null;
            try {
                fw = new FileWriter(f);
                conteudo = "O servidor está funcionando desde : " + data + "\n";
                conteudo += "As resquisições respondidas por este servidor chegam ao número de: ";
                fw.write(conteudo);
            } catch (IOException ex) {
                Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
            } finally {
                try {
                    fw.close();
                } catch (IOException ex) {
                    Logger.getLogger(Arquivo.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
        return conteudo;
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
        int i = 0;
        System.out.println(path);
        String dirPai = dirPai(path);
        String linhasDeInfo = "<tr><td>--</td><td><a href=\"/leArquivo" + dirPai + " \"> ..Voltar</a></td><td></td><td></td><td></td></tr>";
        System.out.println("DirPai: " + dirPai);
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
                    linhasDeInfo += "<tr><td>" + i + "</td><td><a href=\"/leArquivo" + href + " \"> " + listFile.getName() + "</a></td> "
                            + "<td>" + bfa.size() + " bytes</td><td>" + dataCriacao + "</td>"
                            + "<td>" + dataModificacao + "</td><td></td></tr> \n";
                } else {
                    linhasDeInfo += "<tr><td>" + i + "</td><td>" + listFile.getName() + "</td> "
                            + "<td>" + bfa.size() + " bytes</td><td>" + dataCriacao + "</td>"
                            + "<td>" + dataModificacao
                            + "</td><td><button type=\"button\" class=\"btn btn-info\"><a href=\""+
                            listFile.getAbsolutePath()+" \" download><i class=\"fa fa-download\" aria-hidden=\"true\"></i>Download</a></button></td></tr> \n";
                }
                i++;
            }
        } else {
            System.out.println("não");
        }
        return linhasDeInfo;
    }

    private String dirPai(String path) {
        String substring = path.substring(0, path.lastIndexOf("/", path.lastIndexOf("/") - 1));
        return substring + "/";
    }
}
