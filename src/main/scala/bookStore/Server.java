package bookStore;
import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
//import com.google.gson.Gson;
//import com.google.gson.JsonObject;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class Server {
    static Map<Object, Book> map = new HashMap<>();
    static  ArrayList<Book> arrayList = new ArrayList<Book>();

    public static void main(String[] args) throws Exception {
        arrayList = BookStoreApp.printAllTheBooks();
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/testPost", new PostHandler());
        server.createContext("/form", new FormHandler());
        server.createContext("/remove", new RemoveHandler());
        server.createContext("/update", new ViewHandler());
        server.createContext("/updateForm", new UpdateFormHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            //Gson gson = new Gson();
            //String response = gson.toJson(bookList.toString());

            String response = "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "</style>" +
                    "<form><table cellpadding=\"10\">\n" +
                    "<caption>Book Store</caption>"+
                    "<a href = \"#\">"+
                    "  <tr>\n" +
                    "    <th>Book Name</th>\n" +
                    "    <th>Author Name</th> \n" +
                    "    <th>Genre</th> \n" +
                    "    <th>Price</th> \n" +
                    "  </tr>\n"+
                    "</a>";
            for(int j = 0;j<arrayList.size();j++){
                response += "<tr>\n" +
                        "    <td>"+arrayList.get(j).bookName() +"</td>\n" +
                        "    <td>"+arrayList.get(j).authorName()+"</td> \n" +
                        "    <td>"+arrayList.get(j).bookGenre()+"</td> \n" +
                        "    <td>"+arrayList.get(j).price()+"</td> \n" +
                        "<td><button  name=\"bt\" formaction=\"/remove\" value = "+j+">Remove</button></td>"+
                        "<td><button  name=\"bt2\" formaction=\"/update\" value = "+j+">View/Update</button></td>"+
                        "  </tr>\n";
            }
            response += "</table></form>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class PostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String response = "<html>\n" +
                    "<body>\n" +
                    "<h3>Insert New Book</h3>\n" +
                    "<div>\n" +
                    "  <form action=\"/form\">\n" +
                    "Book Name: <br><input type=\"text\" name=\"BookName\" value=\"\"><br>\n" +
                    "Author Name: <br><input type=\"text\" name=\"AuthorName\" value=\"\"><br>\n" +
                    "Price: <br><input type=\"text\" name=\"Price\" value=\"\"><br>\n" +
                    "Genre: <br><input type=\"text\" name=\"Genre\" value=\"\"><br>\n" +
                    "<br><input type=\"submit\" value=\"Add\">\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

    }

    static class FormHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            Map<String, String> map = getParams(url);
            BookStoreApp.addNewBooksFromServer(map);
            arrayList = BookStoreApp.list();
            String response = "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "</style>" +
                     "Book has been added successfuly"+
                    "<table cellpadding=\"10\">\n" +
                    "<caption>Book Store</caption>"+
                    "  <tr>\n" +
                    "    <th>Book Name</th>\n" +
                    "    <th>Author Name</th> \n" +
                    "    <th>Genre</th> \n" +
                    "    <th>Price</th> \n" +
                    "  </tr>\n";
            for(int j = 0;j<arrayList.size();j++){
                response += "<tr>\n" +
                        "    <td>"+arrayList.get(j).bookName() +"</td>\n" +
                        "    <td>"+arrayList.get(j).authorName()+"</td> \n" +
                        "    <td>"+arrayList.get(j).bookGenre()+"</td> \n" +
                        "    <td>"+arrayList.get(j).price()+"</td> \n" +
                        "<td><button  name=\"bt\" formaction=\"/remove\" value = "+j+">Remove</button></td>"+
                        "<td><button  name=\"bt2\" formaction=\"/update\" value = "+j+">View/Update</button></td>"+
                        "  </tr>\n";
            }
            response += "</table>";
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
            BookStoreApp.deleteBook(map);
            arrayList = BookStoreApp.list();
            String response = "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "</style>" +
                    "<form><table cellpadding=\"10\">\n" +
                    "<caption>Book Store</caption>"+
                    "<a href = \"#\">"+
                    "  <tr>\n" +
                    "    <th>Book Name</th>\n" +
                    "    <th>Author Name</th> \n" +
                    "    <th>Genre</th> \n" +
                    "    <th>Price</th> \n" +
                    "  </tr>\n"+
                    "</a>";
            for(int j = 0;j<arrayList.size();j++){
                response += "<tr>\n" +
                        "    <td>"+arrayList.get(j).bookName() +"</td>\n" +
                        "    <td>"+arrayList.get(j).authorName()+"</td> \n" +
                        "    <td>"+arrayList.get(j).bookGenre()+"</td> \n" +
                        "    <td>"+arrayList.get(j).price()+"</td> \n" +
                        "<td><button  name=\"bt\" formaction=\"/remove\" value = "+j+">Remove</button></td>"+
                        "<td><button  name=\"bt2\" formaction=\"/update\" value = "+j+">View/Update</button></td>"+
                        "  </tr>\n";
            }
            response += "</table></form>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class ViewHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            Map<String, String> map = getParams(url);
            Book bk = BookStoreApp.showBook(map);
            //String response = bk.bookName();
            String response = "<html>\n" +
                    "<body>\n" +
                    "<h3>Update Book Details</h3>\n" +
                    "<div>\n" +
                    "<form action=\"/updateForm\">\n" +
                    "Book Name: <br><input type=\"text\" size=\"35\" name=\"BookName\" value=\""+bk.bookName()+"\"><br>\n" +
                    "Author Name: <br><input type=\"text\" size=\"35\" name=\"AuthorName\" value=\""+bk.authorName()+"\"><br>\n" +
                    "Price: <br><input type=\"text\" size=\"35\" name=\"Price\" value=\""+bk.price()+"\"><br>\n" +
                    "Genre: <br><input type=\"text\" size=\"35\" name=\"Genre\" value=\""+bk.bookGenre()+"\"><br>\n" +
                    "<br><input type=\"submit\" value=\"Update\">\n" +
                    "</form>\n" +
                    "</div>\n" +
                    "</body>\n" +
                    "</html>";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

    static class UpdateFormHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {
            String url = t.getRequestURI().getQuery();
            Map<String, String> map = getParams(url);
            BookStoreApp.updateBook(map);
            arrayList = BookStoreApp.list();
            String response = "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "</style>" +
                    "<h3>Book has been updated successfuly</h3>\n" +
                    "<form><table cellpadding=\"10\">\n" +
                    "<caption>Book Store</caption>"+
                    "<a href = \"#\">"+
                    "  <tr>\n" +
                    "    <th>Book Name</th>\n" +
                    "    <th>Author Name</th> \n" +
                    "    <th>Genre</th> \n" +
                    "    <th>Price</th> \n" +
                    "  </tr>\n"+
                    "</a>";
            for(int j = 0;j<arrayList.size();j++){
                response += "<tr>\n" +
                        "    <td>"+arrayList.get(j).bookName() +"</td>\n" +
                        "    <td>"+arrayList.get(j).authorName()+"</td> \n" +
                        "    <td>"+arrayList.get(j).bookGenre()+"</td> \n" +
                        "    <td>"+arrayList.get(j).price()+"</td> \n" +
                        "<td><button  name=\"bt\" formaction=\"/remove\" value = "+j+">Remove</button></td>"+
                        "<td><button  name=\"bt2\" formaction=\"/update\" value = "+j+">View/Update</button></td>"+
                        "  </tr>\n";
            }
            response += "</table></form>";
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
