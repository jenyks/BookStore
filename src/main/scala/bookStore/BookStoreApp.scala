package bookStore

import java.util
import scala.collection.mutable

object BookStoreApp{
  var booklist:mutable.LinkedHashMap[Int, Book] = new mutable.LinkedHashMap()
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
  }

  def save(book : Book) : Unit = {
    var key : Int = booklist.keysIterator.max+1
    booklist += (key -> book)
  }

  def updateBookData(key : Int, book: Book) : Unit = {
    booklist += (key -> book)
  }

  def main(args: Array[String]): Unit = {
    System.out.println("Retrive all books : \n"+retrieveAll())
    System.out.println("\nfind by id 2 : \n"+findById(2))
    removeById(1)
    System.out.println("\nPrint after removing by id 1 : \n"+booklist)
    val newBook = Book("New Book Name","New Author",275.00,"New Genre")
    save(newBook)
    System.out.println("\nPrint after adding new book : \n"+booklist)
    val updateBook = Book("New Book Name","Updated Author",2750.00,"Updated Genre")
    updateBookData(6,updateBook)
    System.out.println("\nPrint after updating by id 6 : \n"+booklist)
  }
}
