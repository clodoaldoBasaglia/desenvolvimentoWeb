/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package web24082017;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 *
 * @author Suporte
 */
public class MetodoGet {

    Socket s;
    
    public MetodoGet(){
        
    }
    
    public MetodoGet(Socket s){
        this.s = s;
    }
 
    public String getContentType(String extension){
        extension = extension.toLowerCase();
        String contentType = null;
        switch(extension){
            case "aac":
                contentType = "audio/aac;charset=UTF-8\r\n";
                break;
            case "abw":
                contentType = "application/x-abiword;charset=UTF-8\r\n";
                break;
            case "arc":
                contentType = "application/octet-stream;charset=UTF-8\r\n";
                break;
            case "avi":
                contentType = "video/x-msvideo;charset=UTF-8\r\n";
                break;
            case "azw":
                contentType = "application/vnd.amazon.ebook;charset=UTF-8\r\n";
                break;
            case "bin":
                contentType = "application/octet-stream;charset=UTF-8\r\n";
                break;                
            case "bz":
                contentType = "application/x-bzip;charset=UTF-8\r\n";
                break;
            case "bz2":
                contentType = "application/x-bzip2;charset=UTF-8\r\n";
                break;
            case "csh":
                contentType = "application/x-csh;charset=UTF-8\r\n";
                break;
            case "css":
                contentType = "text/css;charset=UTF-8\r\n";
                break;
            case "csv":
                contentType = "text/csv;charset=UTF-8\r\n";
                break;
            case "doc":
                contentType = "application/msword;charset=UTF-8\r\n";
                break;
            case "eot":
                contentType = "application/vnd.ms-fontobject;charset=UTF-8\r\n";
                break;
            case "epub":
                contentType = "application/epub+zip;charset=UTF-8\r\n";
                break;
            case "gif":
                contentType = "image/gif;charset=UTF-8\r\n";
                break;
            case "htm":
                contentType = "text/html;charset=UTF-8\r\n";
                break;
            case "html":
                contentType = "text/html;charset=UTF-8\r\n";
                break;
            case "ico":
                contentType = "image/x-icon;charset=UTF-8\r\n";
                break;                
            case "ics":
                contentType = "text/calendar;charset=UTF-8\r\n";
                break;
            case "jar":
                contentType = "application/java-archive;charset=UTF-8\r\n";
                break;
            case "jpeg":
                contentType = "image/jpeg;charset=UTF-8\r\n";
                break;
            case "jpg":
                contentType = "image/jpeg;charset=UTF-8\r\n";
                break;
            case "js":
                contentType = "application/javascript;charset=UTF-8\r\n";
                break;
            case "json":
                contentType = "application/json;charset=UTF-8\r\n";
                break;
            case "mid":
                contentType = "audio/midi;charset=UTF-8\r\n";
                break;
            case "midi":
                contentType = "audio/midi;charset=UTF-8\r\n";
                break;
            case "mpeg":
                contentType = "video/mpeg;charset=UTF-8\r\n";
                break;
            case "mpkg":
                contentType = "application/vnd.apple.installer+xml;charset=UTF-8\r\n";
                break;
            case "odp":
                contentType = "application/vnd.oasis.opendocument.presentation;charset=UTF-8\r\n";
                break;
            case "ods":
                contentType = "application/vnd.oasis.opendocument.spreadsheet;charset=UTF-8\r\n";
                break;                
            case "odt":
                contentType = "application/vnd.oasis.opendocument.text;charset=UTF-8\r\n";
                break;
            case "oga":
                contentType = "audio/ogg;charset=UTF-8\r\n";
                break;
            case "ogv":
                contentType = "video/ogg;charset=UTF-8\r\n";
                break;
            case "ogx":
                contentType = "application/ogg;charset=UTF-8\r\n";
                break;
            case "otf":
                contentType = "font/otf;charset=UTF-8\r\n";
                break;
            case "png":
                contentType = "image/png;charset=UTF-8\r\n";
                break;
            case "pdf":
                contentType = "application/pdf;charset=UTF-8\r\n";
                break;
            case "ppt":
                contentType = "application/vnd.ms-powerpoint;charset=UTF-8\r\n";
                break;
            case "rar":
                contentType = "application/x-rar-compressed;charset=UTF-8\r\n";
                break;
            case "rtf":
                contentType = "application/rtf;charset=UTF-8\r\n";
                break;
            case "sh":
                contentType = "application/x-sh;charset=UTF-8\r\n";
                break;
            case "svg":
                contentType = "image/svg+xml;charset=UTF-8\r\n";
                break;
            case "swf":
                contentType = "application/x-shockwave-flash;charset=UTF-8\r\n";
                break;                
            case "tar":
                contentType = "application/x-tar;charset=UTF-8\r\n";
                break;
            case "tif":
                contentType = "image/tiff;charset=UTF-8\r\n";
                break;
            case "tiff":
                contentType = "image/tiff;charset=UTF-8\r\n";
                break;
            case "ts":
                contentType = "application/typescript;charset=UTF-8\r\n";
                break;
            case "ttf":
                contentType = "font/ttf;charset=UTF-8\r\n";
                break;
            case "vsd":
                contentType = "application/vnd.visio;charset=UTF-8\r\n";
                break;
            case "wav":
                contentType = "audio/x-wav;charset=UTF-8\r\n";
                break;
            case "weba":
                contentType = "audio/webm;charset=UTF-8\r\n";
                break;
            case "webm":
                contentType = "video/webm;charset=UTF-8\r\n";
                break;
            case "webp":
                contentType = "image/webp;charset=UTF-8\r\n";
                break;
            case "woff":
                contentType = "font/woff;charset=UTF-8\r\n";
                break;
            case "woff2":
                contentType = "font/woff2;charset=UTF-8\r\n";
                break;
            case "xhtml":
                contentType = "application/x-abiword;charset=UTF-8\r\n";
                break;
            case "xls":
                contentType = "application/vnd.ms-excel;charset=UTF-8\r\n"
                    + "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8\r\n";
                break;
            case "xlsx":
                contentType = "application/vnd.ms-excel;charset=UTF-8\r\n"
                    + "Content-Type: application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=UTF-8\r\n";
                break;
            case "xml":
                contentType = "application/xml;charset=UTF-8\r\n";
                break;
            case "xul":
                contentType = "application/vnd.mozilla.xul+xml;charset=UTF-8\r\n";
                break;
            case "zip":
                contentType = "application/zip;charset=UTF-8\r\n";
                break;
            case "3gp":
                contentType = "video/3gpp;charset=UTF-8\r\n"
                    + "Content-Type: audio/3gpp;charset=UTF-8\r\n";
                break;
            case "3g2":
                contentType = "video/3gpp2;charset=UTF-8\r\n"
                    + "Content-Type: audio/3gpp2;charset=UTF-8\r\n";
                break;
            case "7z":
                contentType = "application/x-7z-compressed;charset=UTF-8\r\n";
                break;
            default:
                break;
        }
        return contentType;
    }   
    
    public String headerDate(){
        return java.time.format.DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneId.of("GMT")));
    }
    
    public String headerLastModified(File f){
        SimpleDateFormat sdf = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT"));
        return sdf.format(new Date(f.lastModified()));
    }
    
    public String requestHeader(File f, String nameFile) throws IOException{
        String extension = nameFile.substring(nameFile.lastIndexOf(".") + 1, nameFile.length());
        String text = "HTTP/1.1 200 OK\r\n"
                + "Date: " +  headerDate() + "\r\n"
                + "Last-Modified: " + headerLastModified(f) + "\r\n"
                + "Content-Length: " + f.length() + "\r\n"
                + "Set-Cookie: cookieName=cookieValue;\r\n"
                + "Content-Type: " + getContentType(extension) + "\r\n\r\n";
        return text;
    }
    
    public void requestBody(String text, File f) throws IOException{
        OutputStream out = s.getOutputStream();
        text = text + "\r\n\r\n";
        FileInputStream fileHtml = new FileInputStream(f);
	int value;			
	while ((value = fileHtml.read()) != -1){
            text += (char)value;
        }
        fileHtml.close();
        text = text + "\r\n\r\n";
        byte[] bytesText = text.getBytes("ISO-8859-1");
        out.write(bytesText);
        out.flush();
        out.close();
    }
    
    
    public void processGet(String filePath, String nameFile) throws IOException, InterruptedException {
        //compressFile();
        File fileHtml = new File(filePath);      
        String text = requestHeader(fileHtml, nameFile);
        requestBody(text, fileHtml);
    }
}