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
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
    Arquivo arq = null;
    Headers headers = new Headers();

    OrdoProcessios(Socket sok) {
        this.sok = sok;
    }

    private void codex(InputStream input) {

        Headers headers = new Headers();
        System.out.println(this.isLogado);
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(input));
        try {

            String request = bufferedReader.readLine();
            String texto = "";
            Request pedido = new Request();
            System.out.println("primeira linha: " + request);
            ArrayList<String> aux = new ArrayList<String>();
            if (request.contains("POST")) {
                metodoPOST(request, bufferedReader);
            } else if (request.contains("GET")) {
                System.out.println(this.isLogado);
                metodoGET(request);
            }

        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("aqu");

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

    private void acessoNegado() {
        Arquivo arq;
        Headers headers = new Headers();
        System.out.println(this.isLogado);
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
                            System.out.println(this.isLogado);
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

    private String getInfoRequisicoes() {
        int cont = 0;
        int num = 0;
        String linhas = " ";
        String aux = " ";
        ArrayList<String> ar = new ArrayList<>();
        BufferedReader bufferedReader = null;
        try {
            File f = new File("req.ss");
            bufferedReader = new BufferedReader(new FileReader(f));
            while ((aux = bufferedReader.readLine()) != null) {
//                System.out.println(aux);
                ar.add(aux);
            }
            for (int i = 0; i < ar.size(); i++) {
                linhas += "<tr><td>" + num + "</td><td>" + ar.get(i).split(";")[0] + "</td><td>" + ar.get(i).split(";")[1] + "</td></tr>";
                num++;
            }
            System.out.println(linhas);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                bufferedReader.close();
            } catch (IOException ex) {
                Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return linhas;
    }

    public void salvarRequisicoes(String req) {
        FileWriter fw = null;
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println(sdf.format(cal.getTime()));
        try {
            String filename = "req.ss";
            fw = new FileWriter(filename, true); //the true will append the new data
            fw.write(req + ";" + sdf.format(cal.getTime()) + "\n");//appends the string to the file
            fw.close();
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fw.close();
            } catch (IOException ex) {
                Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void metodoGET(String request) throws IOException {
        System.out.println("Metodo get: " + this.isLogado);
        if (request.split(" ")[1].equalsIgnoreCase("/") || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
                || request.split(" ")[1].equalsIgnoreCase("/bemvindo")) {
            salvarRequisicoes("/bemvindo");
            arq = new Arquivo(this.pathToHtml + "/src/html/bemvindo.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
            // || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
        } else if (request.split(" ")[1].equalsIgnoreCase("/login")) {
            salvarRequisicoes("/login");
            arq = new Arquivo(this.pathToHtml + "/src/html/login.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
        } else if (request.split(" ")[1].equalsIgnoreCase("/login2")) {
            salvarRequisicoes("/login2");
            arq = new Arquivo(this.pathToHtml + "/src/html/login2.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
        } else if (request.split(" ")[1].equalsIgnoreCase("/login2?")) {
            salvarRequisicoes("/login2");
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
        } else if (request.contains("/diretorios.html") || request.contains("/diretorios")) {
            salvarRequisicoes("/diretorios");
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
            salvarRequisicoes("/leArquivo");
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
            salvarRequisicoes("/telemetria");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/telemetria.html");
                String openFile = arq.openFile();
                String replace = openFile.replace("panzerkampfwagen", arq.aboutServer());
                this.output.write(replace.getBytes());
            } else {
                acessoNegado();
            }
        }else if (request.contains("/telemetria2.html") || request.contains("/telemetria2")) {
            salvarRequisicoes("/telemetria2");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/telemetria2.html");
                String openFile = arq.openFile();
                String replace = openFile.replace("panzerkampfwagen", arq.aboutServer());
                this.output.write(replace.getBytes());
            } else {
                acessoNegado();
            }
        }else if (request.contains("/historico.html") || request.contains("/historico")) {
            salvarRequisicoes("/historico");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/historico.html");
                String openFile = arq.openFile();
                String info = getInfoRequisicoes();
                String replace = openFile.replace("panzerkampfwagen", info);
                this.output.write(replace.getBytes());
            } else {
                acessoNegado();
            }
        } else {
            arq = new Arquivo(this.pathToHtml + "/src/html/erro404.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());

        }
    }

    private void metodoPOST(String request, BufferedReader bufferedReader) {
        try {
            if (request.contains("/loginFunction")) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/blank.html");
                this.output.write(arq.openFile().getBytes());
                fazLogin(bufferedReader);
            }
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
