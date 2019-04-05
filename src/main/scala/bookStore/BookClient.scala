package bookStore
import com.rabbitmq.client.{ConnectionFactory, DeliverCallback}

object BookClient {

  private val EXCHANGE_NAME = "directExchange"
  private val EXCHANGE_NAME_RES  = "directExchange2"

  private val BINDING_KEY = "request"
  private val BINDING_KEY_RESPONSE = "response"
  val factory = new ConnectionFactory()
  factory.setHost("localhost")
  val connection = factory.newConnection()

  def main(argv: Array[String]) {
    getUserInput()
    getResponse()
  }

  def getUserInput(): Unit ={

    var message = new StringBuilder()
    System.out.print("Enter the function \n\tView All \n\tfindById \n\tremoveById \n\tsave \n\tupdate \n: ")
    val input = scala.io.StdIn.readLine()
    var function = input.toLowerCase()
    message = message.append(function)

    if (function.equalsIgnoreCase("view all")){
        System.out.println("Book List = ")

    }else if (function.equalsIgnoreCase("findById")){
      System.out.print("Enter Id : ")
      val id = scala.io.StdIn.readLine()
      message = message.append(",").append(id)

    }else if (function.equalsIgnoreCase("removeById")){
      System.out.print("Enter Id : ")
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

    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    channel.basicPublish(EXCHANGE_NAME, BINDING_KEY, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")

    //getResponse()
  }

  def getResponse() : Unit = {

    val channel2 = connection.createChannel()
    channel2.exchangeDeclare(EXCHANGE_NAME_RES, "direct")
    val queueName = channel2.queueDeclare().getQueue

    channel2.queueBind(queueName, EXCHANGE_NAME_RES, BINDING_KEY_RESPONSE)

    val deliverCallback: DeliverCallback = (_, delivery) => {
      val message = new String(delivery.getBody, "UTF-8")
      System.out.println(message + "\n")

      getUserInput()
    }

    channel2.basicConsume(queueName, false, deliverCallback, _ => {})
  }


}