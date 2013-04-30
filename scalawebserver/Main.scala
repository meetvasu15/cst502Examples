package scalawebserver

  import java.net._
  import com.sun.net.httpserver._

  class Handler extends HttpHandler {
    import java.io._
    import java.nio._
    import scala.io.Source

    val responseCodes = Map (
      "200" -> 200, "404" -> 404
    )

    val mimeTypes = Map (
        "html" -> "text/html", "png" -> "image/png")

    val Page404 = <html><head><title>Page Not Found</title></head><body><p>Sorry but that resource does not exist</p></body></html>
    val rootDir = new File("htdocs")

    def handle(exchange: HttpExchange) {

        val writer = new BufferedWriter(new OutputStreamWriter(exchange.getResponseBody()))

        val requestedFile = new File(rootDir, exchange.getRequestURI().toString().substring(1)) 
		 println(exchange.getRequestURI().toString().substring(1))
        val requestedExtension = exchange.getRequestURI().toString().split("\\.").reverse(0)
		println(exchange.getRequestURI().toString().split("\\.").reverse(0))
        if(requestedFile.canRead()) {

            val list = new java.util.ArrayList[String]()

            list.add(mimeTypes(requestedExtension))

            exchange.getResponseHeaders.put("Content-Type: ", list)
            exchange.sendResponseHeaders(responseCodes("200"), 0)

            if(requestedExtension.equals("html")) {
                Source.fromFile(requestedFile).getLines.foreach(writer.write)
            } else {
                val input = new DataInputStream(new FileInputStream(requestedFile))
                val output = new DataOutputStream(exchange.getResponseBody())
                val buffer: Array[Byte] = new Array[Byte](1024)

                while((input.read(buffer)) != -1) {
                    output.write(buffer);
                }
            }

        } else {
            exchange.sendResponseHeaders(responseCodes("404"), 0)
            writer.write(Page404.toString());
        }

        writer.flush()

        exchange.close()
    }
  }

object Main {


  /**
   * @param args the command line arguments
   */
  def main(args: Array[String]) = {

    println("Attempting to start server")

    val server = HttpServer.create(new InetSocketAddress(8082), 10)

    server.createContext("/", new Handler())

    server.start
    Console.println("Press return to stop the server")
    while(Console.readLine() == null) {}

    server.stop(0)
  }

}