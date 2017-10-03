from http.server import BaseHTTPRequestHandler, HTTPServer
from datetime import datetime
import os, socket


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
        arquivo = open("serverInfo.ss", "r+")
        arquivo = arquivo.readlines()
        for i in range(0, len(arquivo)):
            info += arquivo[i]
        rwriteFile = open("serverInfo.ss", "r+")
        linhas = rwriteFile.readlines()
        requisicoes = int(linhas[1].split(":")[1]) + 1
        atualiza = open("serverInfo.ss", "w+")
        atualiza.write(linhas[0])
        atualiza.write("Numero de requisicoes atendidas:" + str(requisicoes))
        atualiza.close()
    else:
        arquivo = open("serverInfo.ss", "w+")
        arquivo.write("""O servidor esta no ar desde """ + str(datetime.now().time().strftime("%d - %m - %Y %H:%M:%S")))
        arquivo.write("\n")
        arquivo.write("Numero de requisicoes atendidas:" + str(0))
        arquivo.close()
    return info


def createCorpoHtml():
    html = """Bem vindo ao servidor, voce logou as """ + str(datetime.now().time().strftime('%H:%M:%S') + "\n\n")
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
            self.wfile.write(bytes(htmlBemVindo, "UTF-8"))
            self.wfile.write(bytes(getServerInfo(), "UTF-8"))
            pass
        else:
            self.do_AUTH()
            self.wfile.write(bytes(self.headers.get("Authorization"), "UTF-8"))
            self.wfile.write(bytes("Nao autenticado", "UTF-8"))
            pass


def run():
    print("Iniciando servidor")
    ip = [l for l in ([ip for ip in socket.gethostbyname_ex(socket.gethostname())[2] if not ip.startswith("127.")][:1],
                      [[(s.connect(('8.8.8.8', 53)), s.getsockname()[0], s.close()) for s in
                        [socket.socket(socket.AF_INET, socket.SOCK_DGRAM)]][0][1]]) if l][0][0]
    endereco = (ip, 8082)
    httpd = HTTPServer(endereco, testHTTPServer_RequestHandler)
    print("servidor online no endereço: " + str(endereco))
    httpd.serve_forever()


run()
