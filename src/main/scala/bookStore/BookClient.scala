package bookStore
import com.rabbitmq.client.ConnectionFactory

object BookClient {

  private val EXCHANGE_NAME = "logs"
  var function : String = _
  var message = new StringBuilder()

  def main(argv: Array[String]) {
    System.out.println("Enter the function \n\tView All \n\tfindById \n\tremoveById \n\tsave \n\tupdate \n: ")
    val input = scala.io.StdIn.readLine()
    function = input.toLowerCase()
    message = message.append(function)

    if (function.equalsIgnoreCase("view all")){

    }else if (function.equalsIgnoreCase("findById")){
      System.out.println("Enter Id : ")
      val id = scala.io.StdIn.readLine()
      message = message.append(",").append(id)
    }else if (function.equalsIgnoreCase("removeById")){
      System.out.println("Enter Id : ")
      val id = scala.io.StdIn.readLine()
      message = message.append(",").append(id)
    }else if (function.equalsIgnoreCase("save")){
      System.out.print("Enter Book Name : ")
      val bookName = scala.io.StdIn.readLine()
      System.out.print("Enter Author Name : ")
      val authorName = scala.io.StdIn.readLine()
      System.out.print("Enter price : ")
      val price = scala.io.StdIn.readLine()
      System.out.print("Enter Book Genre : ")
      val bookGenre = scala.io.StdIn.readLine()
      message = message.append(",").append(bookName).append(",").append(authorName).append(",").append(price).append(",").append(bookGenre)
    }else if (function.equalsIgnoreCase("update")){
      System.out.print("Enter Book Id : ")
      val bookId = scala.io.StdIn.readLine()
      System.out.print("Enter Book Name : ")
      val bookName = scala.io.StdIn.readLine()
      System.out.print("Enter Author Name : ")
      val authorName = scala.io.StdIn.readLine()
      System.out.print("Enter price : ")
      val price = scala.io.StdIn.readLine()
      System.out.print("Enter Book Genre : ")
      val bookGenre = scala.io.StdIn.readLine()
      message = message.append(",").append(bookId).append(",").append(bookName).append(",").append(authorName).append(",").append(price).append(",").append(bookGenre)
    }
    createConnection(message.toString())


  }

  def createConnection (message : String) : Unit = {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
    channel.basicPublish(EXCHANGE_NAME, "", null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
    channel.close()
    connection.close()
  }


}
