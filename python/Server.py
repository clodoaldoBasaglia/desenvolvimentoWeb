from http.server import BaseHTTPRequestHandler, HTTPServer
from datetime import datetime
import os


def doArquivos():
    # dir = "C:\\Users\\Suporte\\Documents"
    dir = "/"
    arquivos = str(os.listdir(dir))
    arquivos = arquivos.split(',')
    files = ''
    for i in range(0, len(arquivos)):
        files = str(i) + " " + files + arquivos[i] + "\n"
    return str(arquivos)


def getServerInfo():
    info = ""
    if os.path.exists("serverInfo.ss"):
        arquivo = tuple(open("serverInfo.ss", "r+"))
        for i in range(0, len(arquivo)):
            info += arquivo[i]
        rwriteFile = open("serverInfo.ss", "w+")
    else:
        arquivo = open("serverInfo.ss", "w+")
        arquivo.write("""O servidor está no ar desde """ + str(datetime.now().time().strftime("%A %d. %B %Y %H:%M:%S")))
        arquivo.write("\n")
        arquivo.write("Número de requisições atendidas:" + str(0))
        arquivo.close()
    return info


def createCorpoHtml():
    serverInfo = getServerInfo()
    html = """Bem vindo ao servidor, você logou às """ + str(datetime.now().time().strftime('%H:%M:%S'))
    return html


class testHTTPServer_RequestHandler(BaseHTTPRequestHandler):
    def do_HEAD(self):
        print("header do login")
        self.send_response(200)
        self.send_header("Content-Type", "text/html")
        self.end_headers

    def do_AUTH(self):
        self.send_response(401)
        self.send_header('WWW-Authenticate', 'Basic realm=\"Desenvolvimento Web\"')
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        print("get")
        if self.headers.get("Authorization") is None:
            self.do_AUTH()
            self.wfile.write(bytes("NÃO TEM AUTORIZAÇÃO", "UTF-8"))
            pass
        elif self.headers.get('Authorization') == 'Basic cm9vdDpyb290':
            self.do_HEAD()
            self.send_response(200)
            self.send_header('Content-type', 'text/html')
            htmlBemVindo = createCorpoHtml()
            # self.send_response(htmlBemVindo)
            self.wfile.write(bytes(htmlBemVindo, "UTF-8"))
            pass
        else:
            self.do_AUTH()
            self.wfile.write(bytes(self.headers.get("Authorization"), "UTF-8"))
            self.wfile.write(bytes("Nao autenticado", "UTF-8"))
            pass


def run():
    print("Iniciando servidor")
    endereco = ("192.168.237.15", 8082)
    httpd = HTTPServer(endereco, testHTTPServer_RequestHandler)
    print("servidor online")
    httpd.serve_forever()


run()
