package bookStore
import com.rabbitmq.client.{ConnectionFactory, DeliverCallback}

object BookClient {

  private val EXCHANGE_NAME = "directExchange"
  private val BINDING_KEY = "request"
  private val BINDING_KEY_RESPONSE = "response"

  def main(argv: Array[String]) {
    System.out.println("inside main ----------------------------------------------------------------------------")
    getUserInput()
  }

  def getUserInput(): Unit ={
    System.out.println("inside getUserInput ########################################################################")

    var message = new StringBuilder()
    //var function : String = ""
    System.out.print("Enter the function \n\tView All \n\tfindById \n\tremoveById \n\tsave \n\tupdate \n: ")
    val input = scala.io.StdIn.readLine()
    var function = input.toLowerCase()
    message = message.append(function)

    if (function.equalsIgnoreCase("view all")){
      createConnection(message.toString())
      getResponse()
    }else if (function.equalsIgnoreCase("findById")){
      System.out.print("Enter Id : ")
      val id = scala.io.StdIn.readLine()
      message = message.append(",").append(id)
      createConnection(message.toString())
      getResponse()
    }else if (function.equalsIgnoreCase("removeById")){
      System.out.print("Enter Id : ")
      val id = scala.io.StdIn.readLine()
      message = message.append(",").append(id)
      createConnection(message.toString())
      getResponse()
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
      createConnection(message.toString())
      getResponse()
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
      createConnection(message.toString())
      getResponse()
    }
    //createConnection(message.toString())

  }

  def createConnection (message : String) : Unit = {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    channel.basicPublish(EXCHANGE_NAME, BINDING_KEY, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
    channel.close()
    connection.close()
  }

  def getResponse() : Unit = {
    System.out.println("inside getResponse*******************************************************************")

    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    val queueName = channel.queueDeclare().getQueue
    channel.queueBind(queueName, EXCHANGE_NAME, BINDING_KEY_RESPONSE)

    val deliverCallback: DeliverCallback = (_, delivery) => {
      val message = new String(delivery.getBody, "UTF-8")
      System.out.println(message + "\n")
      getUserInput()

    }
    channel.basicConsume(queueName, true, deliverCallback, _ => {})
    //getUserInput()
  }
}
