package bookStore
import com.rabbitmq.client._
object BookServer {
  private val EXCHANGE_NAME = "logs"

  def main(argv: Array[String]) {

    val factory = new ConnectionFactory()
    factory.setHost("localhost")
    val connection = factory.newConnection()
    val channel = connection.createChannel()
    channel.exchangeDeclare(EXCHANGE_NAME, "fanout")
    val queueName = channel.queueDeclare().getQueue
    channel.queueBind(queueName, EXCHANGE_NAME, "")
    println(" [*] Waiting for messages. To exit press CTRL+C")

    val deliverCallback: DeliverCallback = (_, delivery) => {
      val message = new String(delivery.getBody, "UTF-8")
      val array = message.split(",")
      val function = array(0)
      if (function.equalsIgnoreCase("View All")){
        System.out.println(BookStoreApp.retrieveAll())
      }else if (function.equalsIgnoreCase("findById")){
        System.out.println(BookStoreApp.findById(array(1).toInt))
      }else if (function.equalsIgnoreCase("removeById")){
        BookStoreApp.removeById(array(1).toInt)
        System.out.println("Removed")
      }else if (function.equalsIgnoreCase("save")){
        val b = Book(array(1),array(2),array(3).toDouble,array(4))
        BookStoreApp.save(b)
        System.out.println("Saved " + b)
      }else if (function.equalsIgnoreCase("update")){
        val id = array(1).toInt
        val b = Book(array(2),array(3),array(4).toDouble,array(5))
        BookStoreApp.updateBookData(id,b)
        System.out.println("Updated " + b)
      }
    }
    channel.basicConsume(queueName, true, deliverCallback, _ => {})
  }








}
