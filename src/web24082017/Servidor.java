/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web24082017;

/**
 *
 * @author Suporte
 */
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.concurrent.Worker;

public class Servidor implements Runnable {

    private Socket s;
    InputStream input;
    OutputStream output;
    private String headerHttpMethod;
//    String dir = "/home/todos/alunos/cm/a968692/Documentos/webCorreto/web24082017/src/html";
    String dir = "C:\\Users\\Suporte\\Desktop\\PUTAQUEPARIU\\PUTAQUEPARIU\\webCorreto\\web24082017\\src\\html";
    private String headerPath;
    private String headerMessage;
    private Map<String, String> headerParameters = new HashMap<String, String>();
    static int contaCookie = 0;

    public Servidor(Socket socket) {
        this.s = socket;
    }

    @Override
    public void run() {
        try {
            input = s.getInputStream();
            output = s.getOutputStream();
            BasicAuthentication b = new BasicAuthentication(this.s);
            b.requestAuthentication();
            prepareToRun(input);
            s.close();
        } catch (InterruptedException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Worker.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void prepareToRun(InputStream input) throws IOException, InterruptedException {
        BufferedReader buffer = new BufferedReader(new InputStreamReader(input));
        String request = buffer.readLine();
        String ss[] = request.split(" ");

        String headerHttpMethod = ss[0];
        String pathFile = "/html";
        pathFile = pathFile + ss[1];
        String nameFile = ss[1];

        File fileExists = new File(pathFile);
        if (!fileExists.isFile() && !fileExists.canRead()) {
            pathFile = "/html/error404.html";
            nameFile = "error404.html";
        } else if (fileExists.isDirectory()) {
            String directory = "/html";
            if (nameFile.equalsIgnoreCase("/")) {
                directory = "/html";
            } else {
                directory = directory + nameFile;
            }
            //System.out.println(directory);
            if (new Diretorio().filesDirectory(directory)) {
                pathFile = "/html/directory.html";
                nameFile = "directory.html";
            }
        }

        //Pega o path e caso tenha mensagem
        metodohttp(headerHttpMethod, pathFile, nameFile);
    }

//    private void metodohttp(String dir) {
//        switch (this.headerHttpMethod) {
//            case "OPTIONS":
//                break;
//            case "GET":
//                processaGET(dir);
//                break;
//            case "HEAD":
//                break;
//            case "POST":
//                processaPost(dir);
//                break;
//            case "DELETE":
//                break;
//            case "TRANCE":
//                break;
//            case "CONNECT":
//                break;
//            default:
//                System.out.println("Método não existe!");
//                break;
//        }
//    }
    private void processaGET(String dir) {
//        File fileHtml = new File(dir + "/erro404.html");
        File fileHtml = new File(dir + "\\login.html");
        String text = null;
        try {
            text = requestHeader(fileHtml);
            requestBody(text, fileHtml);
//            fileHtml = new File(dir + "/erro404.html");
            fileHtml = new File(dir + "\\login.html");
            text = requestHeader(fileHtml);
            requestBody(text, fileHtml);
        } catch (IOException e) {
            System.out.println("tentando abrir o arquivo e não deu" + e.toString());
        }
    }

    private String requestHeader(File fileHtml) {
        printExtencao(fileHtml);
        String text = "HTTP/1.1 200 OK\r\n"
                + "Content-Length: " + fileHtml.length() + "\r\n"
                + "Set-Cookie: cookieName=cookieValue;\r\n"
                //                +"Content-Disposition: form-data; name=\"erro\"; filename=\"erro.png\" \r\n\r\n"
                //                + "Content-Type:" + printExtencao(fileHtml) + "\r\n\r\n";
                + "Content-Type: text/html \r\n\r\n";
        System.out.println("Cookie: " + contaCookie++);
        return text;
    }

    private void requestBody(String text, File f) throws IOException {
        OutputStream out = s.getOutputStream();
        text.concat("\r\n\r\n");
        FileInputStream fileHtml = new FileInputStream(f);
        int value;
        while ((value = fileHtml.read()) != -1) {
            text += (char) value;
        }
        fileHtml.close();
        text.concat("\r\n\r\n");
        byte[] bytesText = text.getBytes("ISO-8859-1");
        System.out.println("->" + text);
        out.write(bytesText);
        out.flush();
        out.close();
    }

    private String printExtencao(File fileHtml) {
        String path = fileHtml.getAbsolutePath();
        String extencao = path.substring(path.lastIndexOf(".") + 1, path.length());
        String type = "";
        if (extencao.equalsIgnoreCase("html")) {
//            type ="text/html; charset=utf-8";
            type = "image/jpeg";
        }
        System.out.println(type);
        return type;
    }

    private void processaPost(String dir) {
        //        File fileHtml = new File(dir + "/erro404.html");

        File fileHtml = new File(dir + "\\bemvindo.html");
        String text = null;
        //windows
        String[] comando = {"cmd.exe", "/c", "cd \"" + dir + ""};
        //linux
//        String comando = "dir " + dir+"/";
        String linha = " ";
        try {
            ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", "dir \"" + dir + "");
            builder.redirectErrorStream(true);
            Process processo = builder.start();
//            processo = Runtime.getRuntime().exec(comando);
            BufferedReader leitor = new BufferedReader(new InputStreamReader(processo.getInputStream()));
            if (leitor.readLine().isEmpty()) {
                System.out.println("NÃO DEU");
            }
            while ((linha = leitor.readLine()) != null) {
                System.out.println(linha);
                linha = linha + "\n";

            }
        } catch (IOException ex) {
            Logger.getLogger(Servidor.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        try {
            text = requestHeader(fileHtml);
            requestBody(text, fileHtml);
//            fileHtml = new File(dir + "/erro404.html");
            fileHtml = new File(dir + "\\bemvindo.html");
            text = requestHeader(fileHtml);
            requestBody(text, fileHtml);
        } catch (IOException e) {
            System.out.println("tentando abrir o arquivo e não deu" + e.toString());
        }
    }

    public void metodohttp(String headerHttpMethod, String pathFile, String nameFile) throws IOException, InterruptedException {
        switch (headerHttpMethod) {
            case "OPTIONS":
                break;
            case "GET":
                new MetodoGet(this.s).processGet(pathFile, nameFile);
                break;
            case "HEAD":
                break;
            case "POST":
                break;
            case "DELETE":
                break;
            case "TRANCE":
                break;
            case "CONNECT":
                break;
            default:
                System.out.println("Método não existe!");
                break;
        }
    }

}
