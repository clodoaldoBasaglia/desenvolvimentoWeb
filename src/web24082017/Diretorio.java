/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web24082017;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Suporte
 */
public class Diretorio {

    public String cabecalho(String directory) {
        return "<!DOCTYPE html>\n"
                + "<html>\n"
                + "<head>\n"
                + "<style>\n"
                + "table{\n"
                + "width: 50%;\n"
                + "}\n"
                + "td, td{\n"
                + "text-align: center;\n"
                + "padding: 8px;\n"
                + "}\n"
                + "</style>\n"
                + "<title>\n"
                + "Index of " + directory
                + "</title>\n"
                + "</head>\n"
                + "<body>\n"
                + "<h1>\n"
                + "Index of " + directory
                + "</h1>\n"
                + "<table>\n"
                + "<tbody>\n"
                + "<tr>\n"
                + "<th>Name</th>\n"
                + "<th>Last modified</th>\n"
                + "<th>Size</th>\n"
                + "</tr>\n"
                + "<hr>\n";
    }

    public String rodape() {
        return "</tbody>\n"
                + "</table>\n"
                + "</body>\n"
                + "</html>";
    }

    public String getParentDirectory(String directory) {
        String parentDirectory = directory;
        parentDirectory = parentDirectory.replace("/html", "");
        parentDirectory = parentDirectory.substring(0, parentDirectory.lastIndexOf("/"));
        if (parentDirectory.length() == 0) {
            return "/";
        }
        return parentDirectory;
    }

    public String filesHtml(String directory, List<Arquivo> listFiles) {
        String files = new String();
        if (!directory.equalsIgnoreCase("/html")) {
            String parentDirectory = getParentDirectory(directory);
            files = "<tr>" + "<td onclick=\"location.href='" + parentDirectory + "'\"> parent directory </td>";
            files = files + "<td> - </td>";
            files = files + "<td> - </td> </tr>";
        }
        for (Arquivo a : listFiles) {
            if (!a.getName().equalsIgnoreCase("directory.html")) {
                String redirect = directory + '/' + a.getName();
                if (redirect.contains("/html")) {
                    redirect = redirect.replace("/html", "");
                }
                files = files + "<tr>" + "<td onclick=\"location.href='" + redirect + "'\">" + a.getName() + "</td>";
                files = files + "<td>" + a.getLastModified() + "</td>";
                files = files + "<td>" + a.getSize() + "</td>" + "</tr>";
            }
        }
        return files;
    }

    public boolean generateHtml(String directory, List<Arquivo> listFiles) throws IOException {
        try (BufferedWriter f = new BufferedWriter(new FileWriter("/html/directory.html"))) {
            f.write(cabecalho(directory));
            f.write(filesHtml(directory, listFiles));
            f.write(rodape());
            f.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean filesDirectory(String directory) throws IOException {
        List<Arquivo> listFiles = new ArrayList<>();
        File folder = new File(directory);
        File[] allOfFiles = folder.listFiles();

        for (int i = 0; i < allOfFiles.length; i++) {
            Arquivo a = new Arquivo();
            String lastModified = directory + "/";
            a.setName(allOfFiles[i].getName());
            a.setSize(String.valueOf(allOfFiles[i].length()) + " bytes");
            lastModified = lastModified + allOfFiles[i].getName();
            a.setLastModified(new MetodoGet().headerLastModified(new File(lastModified)));
            listFiles.add(a);
        }

        if (generateHtml(directory, listFiles)) {
            return true;
        }
        return false;
    }
}
