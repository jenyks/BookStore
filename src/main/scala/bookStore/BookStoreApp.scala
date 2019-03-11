package bookStore

import java.util

import collection.JavaConverters._
import collection.mutable._

object BookStoreApp{
  var test2 : String = "testing connection"
  var booklist:Map[Int,Book] = Map()
  var noOfBooks : Int = 5
  var myList = Array(1.9, 2.9, 3.4, 3.5)
  var list = new util.ArrayList[Book]
  //var booklist = scala.collection.mutable.Map[Int,BookData]()

  for(itr <- 1 to noOfBooks){
    var b : Book = new Book() {
      override var authorName: String = "Author Name " + itr
      override var price: Double = 100
      override var bookGenre: String = "Genre " + itr
      override var bookName: String = "Book Name " + itr
    }
    booklist += (itr -> b)
  }

  //val bk = booklist.asJava

  def printAllTheBooks() : util.ArrayList[Book] = {

    for ((k,v) <- booklist){
      list.add(v)
      println("Book name = "+ v.bookName)
      println("Author name = "+ v.authorName)
      println()
    }
    return list

  }

  def addNewBooks() : Unit = {
    print("Enter the number of books you want to add : ")
    val n = scala.io.StdIn.readInt()
    var count : Int = booklist.size

    for(itr <- 1 to n ){
      count += 1
      println("Details of Book "+itr)
      print("Book Name: ")
      val newBookName = scala.io.StdIn.readLine()
      print("Author Name: ")
      val newAuthorName = scala.io.StdIn.readLine()
      print("Book Price: ")
      val newPrice = scala.io.StdIn.readDouble()
      print("Book Genre: ")
      val newBookGenre = scala.io.StdIn.readLine()
      println()

      var bookObj = new Book {
        override var bookName: String = newBookName
        override var authorName: String = newAuthorName
        override var price: Double = newPrice
        override var bookGenre: String = newBookGenre
      }
      booklist += (count -> bookObj)
    }
  }

  def findABook() : Unit = {
    print("Enter the name of the book : ")
    val bookName = scala.io.StdIn.readLine()
    if(booklist.values.exists(_.bookName ==  bookName)){
      println("******************************************")
      // TODO: print details

    }else{
      println(bookName+" is not available in this Book Store")
    }
  }

  def updateBookData() : Unit = {
    //booklist(2) = b4
  }

  def deleteBook() : Unit = {
    //booklist -= 1
  }

  def main(args: Array[String]): Unit = {
    printAllTheBooks()
    addNewBooks()
  }
}
