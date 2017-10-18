/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package webservice;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.util.ArrayList;
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
    private boolean isLogado = true;
    String pathToHtml = new File("").getAbsolutePath();

    OrdoProcessios(Socket sok) {
        this.sok = sok;
    }

    private void codex(InputStream input) {

        Headers headers = new Headers();

        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        try {
            Arquivo arq = null;
            String request = bufferedReader.readLine();
            String texto = "";
            Request pedido = new Request();
            System.out.println("primeira linha: " + request);
            ArrayList<String> aux = new ArrayList<String>();
//            if (request.contains("POST")) {
//                System.out.println("contais post");
//                this.isLogado = fazLogin(bufferedReader);
//            }
            if (request.split(" ")[1].equalsIgnoreCase("/") || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
                    || request.split(" ")[1].equalsIgnoreCase("/bemvindo")) {
                arq = new Arquivo(this.pathToHtml + "/src/html/bemvindo.html");
                this.output.write(headers.BasicHeader().getBytes());
                this.output.write(arq.openFile().getBytes());
                // || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
            } else if (request.split(" ")[1].equalsIgnoreCase("/login")) {
                arq = new Arquivo(this.pathToHtml + "/src/html/login.html");
                this.output.write(headers.BasicHeader().getBytes());
                this.output.write(arq.openFile().getBytes());
            } else if (request.contains("/diretorios.html") || request.contains("/diretorios")) {
                if (this.isLogado) {
                    this.output.write(headers.BasicHeader().getBytes());
                    arq = new Arquivo(this.pathToHtml + "/src/html/diretorios.html");
                    String lerDiretorio = arq.lerDiretorio(this.pathToHtml);
                    String diretorio = arq.openFile();
                    diretorio = diretorio.replaceAll("panzerkampfwagen", lerDiretorio);
                    this.output.write(diretorio.getBytes());
                } else {
                    acessoNegado();
                }
            } else if (request.contains("/leArquivo/") || request.contains("/leArquivo")) {
                if (this.isLogado) {
                    this.output.write(headers.BasicHeader().getBytes());
                    arq = new Arquivo(this.pathToHtml + "/src/html/diretorios.html");
                    String caminho = request.split(" ")[1];//.substring(request.indexOf("/"), request.split(" ")[1].length());
                    caminho = caminho.substring(1, request.split(" ")[1].length());
                    caminho = caminho.substring(caminho.indexOf("/"), caminho.length());
                    String lerDiretorio = arq.lerDiretorio(caminho + "/");
                    String diretorio = arq.openFile();
                    diretorio = diretorio.replaceAll("panzerkampfwagen", lerDiretorio);
                    this.output.write(diretorio.getBytes());
                } else {
                    acessoNegado();
                }
            } else if (request.contains("/telemetria.html") || request.contains("/telemetria")) {
                if (this.isLogado) {
                    this.output.write(headers.BasicHeader().getBytes());
                    arq = new Arquivo(this.pathToHtml + "/src/html/telemetria.html");
                    System.out.println("Info: " + arq.aboutServer());
                    String openFile = arq.openFile();
                    String replace = openFile.replace("panzerkampfwagen", arq.aboutServer());
                    this.output.write(replace.getBytes());
                } else {
                    acessoNegado();
                }
            } else if (request.contains("POST") || request.contains("/loginFunction")) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/blank.html");
                this.output.write(arq.openFile().getBytes());
            } else {
                arq = new Arquivo(this.pathToHtml + "/src/html/erro404.html");
                this.output.write(headers.BasicHeader().getBytes());
                this.output.write(arq.openFile().getBytes());

            }
//            if (request.contains("POST")) {
//                if (fazLogin(bufferedReader)) {
//                    System.out.println("login");
//                } else {
//                    System.out.println("não");
//                }
//
//            }
            if (request.contains("GET")) {
                String helper = "";
                while ((helper = bufferedReader.readLine()) != null) {
                    if (helper.contains("login=")) {
                        System.out.println("LOGIN get");
                    }
                }
//                pedido.setHost(bufferedReader.readLine().split(" ")[1]);
//                pedido.setConnection(bufferedReader.readLine().split(" ")[1]);
//                pedido.setChacheControl(bufferedReader.readLine().split(" ")[1]);
//                pedido.setUserAgent(bufferedReader.readLine().split(" ")[1]);
//                pedido.setUpgradeSegureRequest(bufferedReader.readLine().split(" ")[1]);
//                while ((texto = bufferedReader.readLine()) != null) {
//                    aux.add(texto);
//                }
//                pedido.setAccept(aux);
//                String printAccept = pedido.printAccept();

            }
//            } else if (request.contains("POST") || request.contains("/loginFunction")) {
//
//            }
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("aqu");
            fazLogin(bufferedReader);
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
//        System.out.println(pedido.toString());
    }

    private void acessoNegado() {
        Arquivo arq;
        Headers headers = new Headers();
        try {
            this.output.write(headers.BasicHeader().getBytes());
            arq = new Arquivo(this.pathToHtml + "/src/html/acessoNegado.html");
            this.output.write(arq.openFile().getBytes());
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean fazLogin(BufferedReader bufferedReader) {
        try {
            String helper = "";
            while ((helper = bufferedReader.readLine()) != null) {
                if (helper.contains("login=")) {
                    String[] split = helper.split("&");
                    if (split.length > 1) {
                        String login = split[0].substring(split[0].indexOf("=") + 1, split[0].length()).trim();
                        String senha = split[1].substring(split[1].indexOf("=") + 1, split[1].length()).trim();
                        System.out.println(login + " " + senha);
                        if (login.equalsIgnoreCase("root") && senha.equalsIgnoreCase("root")) {
                            this.isLogado = true;
                            System.out.println("logou");
                            return true;
                        } else {
                            return false;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

}
