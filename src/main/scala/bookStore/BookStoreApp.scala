package bookStore

import java.util
import collection.mutable._

object BookStoreApp{
  var booklist:Map[Int,Book] = Map()


  val b1 = Book("Harry Potter","J K Rowling",275.00,"Fantasy")
  val b2 = Book("Let Us C","Yashavant Kanetkar",580.00,"Programming")
  val b3 = Book("Functional Programming in Scala","Paul Chiusano",200.00,"Programming")
  val b4 = Book("The Lord of the Rings","J. R. R. Tolkien",440.00,"Fantasy")
  val b5 = Book("Sapiens: A Brief History of Humankind","Yuval Noah Harari",550.00,"History")

  booklist += (1 -> b1)
  booklist += (2 -> b2)
  booklist += (3 -> b3)
  booklist += (4 -> b4)
  booklist += (5 -> b5)

  def retrieveAll() : util.ArrayList[Book] = {
    var list =  new util.ArrayList[Book]
    for((k,v) <- booklist){
      list.add(v)
    }
    return list
  }

  def findById(key : Int): Book={
    var b = booklist.get(key).get
    return b
  }

  def removeById(key : Int) : Unit = {
    booklist -= key
    System.out.println(booklist)
  }

  def save(book : Book) : Unit = {
    var key : Int = booklist.keysIterator.max+1
    booklist += (key -> book)
    System.out.println(booklist)
  }

  def updateBookData(key : Int, book: Book) : Unit = {
    booklist += (key -> book)
  }

  def main(args: Array[String]): Unit = {
    System.out.println(retrieveAll())
    System.out.println(findById(1))
    removeById(2)
  }
}
