package bookStore;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;
import org.apache.logging.log4j.core.util.IOUtils;
import scala.tools.nsc.doc.html.page.JSONArray;
import scala.tools.nsc.doc.html.page.JSONObject;

public class Server {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/viewAll", new ViewAllHandler());
        server.createContext("/findById", new FindHandler());
        server.createContext("/removeById", new RemoveHandler());
        server.createContext("/save", new SaveHandler());

        //server.createContext("/form", new FormHandler());
        //server.createContext("/update", new ViewHandler());
        //server.createContext("/updateForm", new UpdateFormHandler());

        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class ViewAllHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            Gson gson = new Gson();
            String response = gson.toJson(BookStoreApp.retrieveAll());
            System.out.println(response);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class FindHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            System.out.println(url);
            Map<String, String> map = getParams(url);
            Book b = BookStoreApp.findById(Integer.parseInt(map.get("id")));
            Gson gson = new Gson();
            String response = gson.toJson(b);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class RemoveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            Map<String, String> map = getParams(url);
            BookStoreApp.removeById(Integer.parseInt(map.get("id")));
            String response = "Deleted";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SaveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

        }
    }

    public static Map<String, String> getParams(String query) {
        String[] params = query.split("&");
        Map<String, String> map = new HashMap<String, String>();
        for (String param : params){
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }
}
