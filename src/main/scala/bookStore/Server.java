package bookStore;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
        System.out.println(arrayList.get(1).authorName());


        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
        server.createContext("/testPost", new PostHandler());
        server.createContext("/form", new FormHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
    }

    static class MyHandler implements HttpHandler {
        @Override
        public void handle(HttpExchange t) throws IOException {

            //JsonObject bookList = new JsonObject();
/*
            for(int i = 1; i< arrayList.size();i++){
                JsonObject book = new JsonObject();
                book.addProperty("Book Name",arrayList.get(i).bookName());
                book.addProperty("Author Name",arrayList.get(i).authorName());
                bookList.add("Book" + i,book);
            }
*/
            //Gson gson = new Gson();
            //String response = gson.toJson(bookList.toString());


            String response = "<style>\n" +
                    "table, th, td {\n" +
                    "  border: 1px solid black;\n" +
                    "  border-collapse: collapse;\n" +
                    "}\n" +
                    "</style>" +
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
                        "  </tr>\n";
            }
            response += "</table>";

            System.out.println(response);

            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }



    }

    static class PostHandler implements HttpHandler {

        @Override
        public void handle(HttpExchange t) throws IOException {
            /*String response = "<html>\n" +
                    "<body>\n" +
                    "<form action=\"/form\">\n" +
                    "Book Name: <input type=\"text\" name=\"BookName\" value=\"\"><br>\n" +
                    "Author Name: <input type=\"text\" name=\"AuthorName\" value=\"\"><br>\n" +
                    "Price: <input type=\"text\" name=\"Price\" value=\"\"><br>\n" +
                    "Genre: <input type=\"text\" name=\"Genre\" value=\"\"><br>\n" +
                    "<input type=\"submit\" value=\"Add\">\n" +
                    "</form>\n" +
                    "</body>\n" +
                    "</html>";
*/
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
            System.out.println(response);

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
            System.out.println(url);
            System.out.println(map);
            String response = BookStoreApp.addNewBooksFromServer(map);
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }

        public static Map<String, String> getParams(String query)
        {
            String[] params = query.split("&");
            Map<String, String> map = new HashMap<String, String>();
            for (String param : params)
            {
                String name = param.split("=")[0];
                String value = param.split("=")[1];
                map.put(name, value);
            }
            return map;
        }


    }



}
