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
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.Socket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author clodoaldo
 */
public class OrdoProcessios implements Runnable {

    List<Amigo> arrayDeAmigo;
    private final Socket sok;
    InputStream input;
    OutputStream output;
    private boolean isLogado = true;
    String pathToHtml = new File("").getAbsolutePath();
    Arquivo arq = null;
    Headers headers = new Headers();
    String ipServer;

    OrdoProcessios(Socket sok, List<Amigo> arrayDeAmigo) {
        this.sok = sok;
        this.arrayDeAmigo = arrayDeAmigo;
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
        boolean flag = false;
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
                            flag = true;
                        } else {
                            flag = false;
                        }
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return flag;
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
        if (request.split(" ")[1].equalsIgnoreCase("clodoaldo") || request.split(" ")[1].equalsIgnoreCase("/clodoaldo.html")) {
            salvarRequisicoes("/clodoaldo");
            arq = new Arquivo(this.pathToHtml + "/src/html/clodoaldo.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
            this.output.close();
        }
        if (request.split(" ")[1].equalsIgnoreCase("/") || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
                || request.split(" ")[1].equalsIgnoreCase("/bemvindo")) {
            salvarRequisicoes("/bemvindo");
            arq = new Arquivo(this.pathToHtml + "/src/html/bemvindo.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
            this.output.close();
            // || request.split(" ")[1].equalsIgnoreCase("/bemvindo.html")
        } else if (request.split(" ")[1].equalsIgnoreCase("/login")) {
            salvarRequisicoes("/login");
            arq = new Arquivo(this.pathToHtml + "/src/html/login.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
            this.output.close();
        } else if (request.split(" ")[1].equalsIgnoreCase("/login2")) {
            salvarRequisicoes("/login2");
            arq = new Arquivo(this.pathToHtml + "/src/html/login2.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());

            this.output.close();
        } else if (request.split(" ")[1].equalsIgnoreCase("/login2?")) {
            salvarRequisicoes("/login2");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/diretorios.html");
                String lerDiretorio = arq.lerDiretorio(this.pathToHtml);
                String diretorio = arq.openFile();
                diretorio = diretorio.replaceAll("panzerkampfwagen", lerDiretorio);
                this.output.write(diretorio.getBytes());
                this.output.close();
            } else {
                acessoNegado();
                this.output.close();
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
                this.output.close();
            } else {
                acessoNegado();
                this.output.close();
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
                this.output.close();
            } else {
                acessoNegado();
            }
        } else if (request.contains("/teste.html") || request.contains("/teste")) {

            salvarRequisicoes("/teste");
            arq = new Arquivo(this.pathToHtml + "/src/html/teste.html");
            this.output.write(headers.BasicHeader().getBytes());
            System.out.println(this.output.toString());
            this.output.write(arq.openFile().getBytes());
            this.output.close();
        } else if (request.contains("/oi.html") || request.contains("/oi")) {

            salvarRequisicoes("/oi");
            arq = new Arquivo(this.pathToHtml + "/src/html/oi.html");
            this.output.write(headers.BasicHeader().getBytes());
            System.out.println(this.output.toString());
            this.output.write(arq.openFile().getBytes());
            this.output.close();

        } else if (request.contains("/telemetria.html") || request.contains("/telemetria")) {
            salvarRequisicoes("/telemetria");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/telemetria.html");
                String openFile = arq.openFile();
                int cont = contLines();
                System.out.println("CONTADOR DE LINHAS >>> " + cont + " >>> " + arq.aboutServer());
                String replace = openFile.replace("panzerkampfwagen", arq.aboutServer());
                replace = replace.replace("konigstiger", String.valueOf(cont));
                this.output.write(replace.getBytes());
                this.output.close();
            } else {
                acessoNegado();
                this.output.close();
            }

        } else if (request.contains("/historico.html") || request.contains("/historico")) {
            salvarRequisicoes("/historico");
            if (this.isLogado) {
                this.output.write(headers.BasicHeader().getBytes());
                arq = new Arquivo(this.pathToHtml + "/src/html/historico.html");
                String openFile = arq.openFile();
                String info = getInfoRequisicoes();
                String replace = openFile.replace("panzerkampfwagen", info);
                this.output.write(replace.getBytes());
                this.output.close();
            } else {
                acessoNegado();
                this.output.close();
            }
        } else if (request.contains("/erro404.html") || request.contains("/erro404")) {
            salvarRequisicoes("/erro404");
            arq = new Arquivo(this.pathToHtml + "/src/html/erro404.html");
            this.output.write(headers.BasicHeader().getBytes());
            this.output.write(arq.openFile().getBytes());
            this.output.close();
        } else {

//            listaAmigos(arrayDeAmigo, request);
//                System.out.println("deu timótio");
            ExecutorService executor = Executors.newCachedThreadPool();
            Callable<Object> task = new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    return listaAmigos(arrayDeAmigo, request);
                }

            };
            Future<Object> future = executor.submit(task);
            try {
                Object ob = future.get(15, TimeUnit.SECONDS);

            } catch (InterruptedException ex) {
                arq = new Arquivo(this.pathToHtml + "/src/html/erro404.html");
                this.output.write(headers.BasicHeader().getBytes());
                this.output.write(arq.openFile().getBytes());
                System.out.println("deu timótio");
                this.output.close();
//                Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ExecutionException ex) {
                System.out.println("entrou aqui no timotio");
//                Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            } catch (TimeoutException ex) {
                System.out.println("entrou aqui no timotio 3");
                arq = new Arquivo(this.pathToHtml + "/src/html/erro404.html");
                this.output.write(headers.BasicHeader().getBytes());
                this.output.write(arq.openFile().getBytes());
                System.out.println("deu timótio");
                this.output.close();
//                Logger.getLogger(OrdoProcessios.class.getName()).log(Level.SEVERE, null, ex);
            }

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
            Logger.getLogger(OrdoProcessios.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    private boolean listaAmigos(List<Amigo> arrayDeAmigo, String request) {
        this.ipServer = getServerIp();
        boolean flag = true;
        System.out.println("Request >>> " + request);
        for (Amigo amigo : arrayDeAmigo) {
            try {
                if (!this.ipServer.equalsIgnoreCase(" ")) {

                    if (this.ipServer.equalsIgnoreCase(amigo.getEnderco())) {

                    } else {
                        System.out.println("Procurando no migo: " + amigo.getEnderco() + " " + amigo.getPortaHttp());
                        Socket socket = new Socket(amigo.getEnderco(), Integer.parseInt(amigo.getPortaHttp()));
                        socket.getKeepAlive();
                        System.out.println(socket.getKeepAlive());
                        OutputStream saida = socket.getOutputStream();
                        InputStream entrada = socket.getInputStream();
                        request.concat("\n FROM SERVER: True");
                        saida.write(request.getBytes());
                        int readEntrada = entrada.read();
                        String texto = getFromInput(entrada);
                        if (request == null || request == null) {
                            socket.close();
                            arrayDeAmigo.remove(amigo);
                            flag = false;
                        } else {
                            OutputStream saida2 = this.sok.getOutputStream();
                            saida2.write(texto.getBytes());
                            flag = true;
                            this.output.close();

                        }
                    }
                }
            } catch (IOException ex) {
                Logger.getLogger(OrdoProcessios.class
                        .getName()).log(Level.SEVERE, null, ex);
            }
        }
        return flag;
    }

    private String getFromInput(InputStream entrada) {
        String t = null;
        try (Scanner scanner = new Scanner(entrada, StandardCharsets.UTF_8.name())) {
            t = scanner.useDelimiter("\\A").next();
        }
        return t;
    }

    private int contLines() {
        int cont = 0;
        try {
            File f = new File("req.ss");
            BufferedReader bufferedReader = new BufferedReader(new FileReader(f));
            String tet = "";
            while ((tet = bufferedReader.readLine()) != null) {
                cont++;

            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OrdoProcessios.class
                    .getName()).log(Level.SEVERE, null, ex);

        } catch (IOException ex) {
            Logger.getLogger(OrdoProcessios.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return cont;
    }

    private String getServerIp() {
        System.out.println("++++ PROCURANDO IP LOCAL +++");
        try {
            Enumeration e = NetworkInterface.getNetworkInterfaces();
            while (e.hasMoreElements()) {
                NetworkInterface n = (NetworkInterface) e.nextElement();
                Enumeration ee = n.getInetAddresses();
                while (ee.hasMoreElements()) {
                    InetAddress i = (InetAddress) ee.nextElement();
                    System.out.println(i.getHostAddress());
                    int compareToIgnoreCase = i.getHostAddress().compareToIgnoreCase(" ");
                    if (!(i.getHostAddress().compareToIgnoreCase("127.0.0.1") == 0)) {
                        return i.getHostAddress();

                    }
                }
            }
        } catch (SocketException ex) {
            Logger.getLogger(OrdoProcessios.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return " ";
    }
}
