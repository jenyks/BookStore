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
        //map = (Map<Integer, BookData>) BookDetails.printAllTheBooks();
        arrayList = BookStoreApp.printAllTheBooks();
        System.out.println(arrayList.get(1).authorName());


        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        server.createContext("/test", new MyHandler());
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


}
