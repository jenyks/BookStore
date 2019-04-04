package bookStore
import com.rabbitmq.client._
object BookServer {
  private val EXCHANGE_NAME = "directExchange"
  private val BINDING_KEY = "request"
  private val BINDING_KEY_RESPONSE = "response"
  var responseMessage : String = _

  def main(argv: Array[String]) {

    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    val queueName = channel.queueDeclare().getQueue
    channel.queueBind(queueName, EXCHANGE_NAME, BINDING_KEY)

    val deliverCallback: DeliverCallback = (_, delivery) => {
      val message = new String(delivery.getBody, "UTF-8")
      val array = message.split(",")
      val function = array(0)
      if (function.equalsIgnoreCase("View All")){
        responseMessage = BookStoreApp.retrieveAll().toString
        createConnection(responseMessage)
      }else if (function.equalsIgnoreCase("findById")){
        responseMessage = BookStoreApp.findById(array(1).toInt).toString
        createConnection(responseMessage)
      }else if (function.equalsIgnoreCase("removeById")){
        BookStoreApp.removeById(array(1).toInt)
        responseMessage = "REMOVED"
        createConnection(responseMessage)
      }else if (function.equalsIgnoreCase("save")){
        val book = Book(array(1),array(2),array(3).toDouble,array(4))
        BookStoreApp.save(book)
        responseMessage = "Saved " + book + "successfully"
        createConnection(responseMessage)
      }else if (function.equalsIgnoreCase("update")){
        val id = array(1).toInt
        val book = Book(array(2),array(3),array(4).toDouble,array(5))
        BookStoreApp.updateBookData(id,book)
        responseMessage = "Updated " + book
        createConnection(responseMessage)
      }
      //createConnection(responseMessage)
    }
    channel.basicConsume(queueName, true, deliverCallback, _ => {})
  }


  def createConnection (message : String) : Unit = {
    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "direct")
    channel.basicPublish(EXCHANGE_NAME, BINDING_KEY_RESPONSE, null, message.getBytes("UTF-8"))
    println(" [x] Sent '" + message + "'")
    channel.close()
    connection.close()
  }





}
