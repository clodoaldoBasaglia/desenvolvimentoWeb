from http.server import BaseHTTPRequestHandler, HTTPServer


def doLogin():
    pass


class testHTTPServer_RequestHandler(BaseHTTPRequestHandler):
    def do_GET(self):
        self.send_response(200)
        self.send_header('Access-Control-Allow-Origin', '*')
        self.send_header('Access-Control-Allow-Methods', 'GET, OPTIONS')
        self.send_header('Content-Type', 'text/html')
        self.end_headers()
        mensagem = "Ola palhaço"
        doLogin()
        self.wfile.write(bytes(mensagem, "UTF-8"))
        return


def run():
    print("Iniciando servidor")
    endereco = ("127.0.0.1", 8000)
    httpd = HTTPServer(endereco, testHTTPServer_RequestHandler)
    print("servidor online")
    httpd.serve_forever()


run()
