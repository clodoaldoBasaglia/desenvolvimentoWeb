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
        if self.headers.get("Authorization") is None:
            self.do_AUTH()
            self.wfile.write(bytes("NÃO TEM AUTORIZAÇÃO","UTF-8"))
            pass
        elif self.headers.get('Authorization') == 'Basic cm9vdDpyb290':
            self.do_HEAD()
            self.wfile.write(bytes(self.headers.get("Authorization"),"UTF-8"))
            self.wfile.write(bytes('Autenticado!',"UTF-8"))
            pass
        else:
            self.do_AUTH()
            self.wfile.write(bytes(self.headers.get("Authorization"),"UTF-8"))
            self.wfile.write(bytes("Nao autenticado","UTF-8"))
            pass


def run():
    print("Iniciando servidor")
    endereco = ("127.0.0.1", 8082)
    httpd = HTTPServer(endereco, testHTTPServer_RequestHandler)

    print("servidor online")
    httpd.serve_forever()


run()
