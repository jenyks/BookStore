package bookStore;
import java.io.*;
import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;
import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {

    public static void main(String[] args) throws Exception {
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/viewAll", new ViewAllHandler());
        server.createContext("/findById", new FindHandler());
        server.createContext("/removeById", new RemoveHandler());
        server.createContext("/save", new SaveHandler());
        server.createContext("/update", new UpdateHandler());
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
            String response = "Deleted Successfully";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class SaveHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //System.out.println(t.getRequestBody());
            String body = null;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = t.getRequestBody();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw ex;
                    }
                }
            }

            body = stringBuilder.toString();
            Gson gson = new Gson();
            Book book = gson.fromJson(body,Book.class);
            BookStoreApp.save(book);

            String response = "Saved Successfully";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class UpdateHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            Map<String, String> map = getParams(url);
            int key = Integer.parseInt(map.get("id"));
            //System.out.println(t.getRequestBody());
            String body = null;
            StringBuilder stringBuilder = new StringBuilder();
            BufferedReader bufferedReader = null;
            try {
                InputStream inputStream = t.getRequestBody();
                if (inputStream != null) {
                    bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                    char[] charBuffer = new char[128];
                    int bytesRead = -1;
                    while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                        stringBuilder.append(charBuffer, 0, bytesRead);
                    }
                } else {
                    stringBuilder.append("");
                }
            } catch (IOException ex) {
                throw ex;
            } finally {
                if (bufferedReader != null) {
                    try {
                        bufferedReader.close();
                    } catch (IOException ex) {
                        throw ex;
                    }
                }
            }

            body = stringBuilder.toString();
            Gson gson = new Gson();
            Book book = gson.fromJson(body,Book.class);

            BookStoreApp.updateBookData(key,book);

            String response = "Updated Successfully";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
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
