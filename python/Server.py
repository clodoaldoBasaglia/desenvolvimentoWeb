from http.server import BaseHTTPRequestHandler, HTTPServer


class testHTTPServer_RequestHandler(BaseHTTPRequestHandler):
    def do_HEAD(self):
        print("header do login")
        self.send_response(200)
        self.send_header("Content-Type", "text/html")
        self.end_headers

    def do_AUTH(self):
        self.send_response(401)
        self.send_header('WWW-Authenticate', 'Basic realm=\"Test\"')
        self.send_header('Content-type', 'text/html')
        self.end_headers()

    def do_GET(self):
        print("get")
        teste = self.headers
        if self.headers.getheader("Authorization"):
            print("teste")
            pass
        if self.headers.getheader('Authorization') == None:
            self.do_HEAD()
            self.wfile.write('NÃO TEM AUTORIZAÇÃO')
            pass
        elif self.headers.getheader('Authorization') == 'Basic dGVzdDp0ZXN0':
            self.do_HEAD()
            self.wfile.write(self.headers.getheader("Authorization"))
            self.wfile.write('Autenticado!')
            pass
        else:
            self.do_AUTH()
            self.wfile.write(self.headers.getheader("Authorization"))
            self.wfile.write("Não autenticado")
            pass


def run():
    print("Iniciando servidor")
    endereco = ("127.0.0.1", 8000)
    httpd = HTTPServer(endereco, testHTTPServer_RequestHandler)

    print("servidor online")
    httpd.serve_forever()


run()
