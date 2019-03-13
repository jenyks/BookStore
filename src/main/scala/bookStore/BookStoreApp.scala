package bookStore

import java.util
import collection.mutable._

object BookStoreApp{
  var booklist:Map[Int,Book] = Map()
  //var noOfBooks : Int = 5
  //var myList = Array
  var list = new util.ArrayList[Book]
  var b : Book = new Book {
    override var bookName: String = _
    override var authorName: String = _
    override var price: Double = _
    override var bookGenre: String = _
  }

  var b1 : Book = new Book {
    override var bookName: String = "Harry Potter"
    override var authorName: String = "J K Rowling"
    override var price: Double = 275.00
    override var bookGenre: String = "Fantasy"
  }
  var b2 : Book = new Book {
    override var bookName: String = "Let Us C"
    override var authorName: String = "Yashavant Kanetkar"
    override var price: Double = 580.00
    override var bookGenre: String = "Programming"
  }
  var b3 : Book = new Book {
    override var bookName: String = "Functional Programming in Scala"
    override var authorName: String = "Paul Chiusano"
    override var price: Double = 200.00
    override var bookGenre: String = "Programming"
  }
  var b4 : Book = new Book {
    override var bookName: String = "The Lord of the Rings"
    override var authorName: String = "J. R. R. Tolkien"
    override var price: Double = 440.00
    override var bookGenre: String = "Fantasy"
  }
  var b5 : Book = new Book {
    override var bookName: String = "Sapiens: A Brief History of Humankind"
    override var authorName: String = "Yuval Noah Harari"
    override var price: Double = 550.00
    override var bookGenre: String = "History"
  }

  booklist += (1 -> b1)
  booklist += (2 -> b2)
  booklist += (3 -> b3)
  booklist += (4 -> b4)
  booklist += (5 -> b5)

  /*
  for(itr <- 1 to noOfBooks){
    var b : Book = new Book() {
      override var authorName: String = "Author Name " + itr
      override var price: Double = 100
      override var bookGenre: String = "Genre " + itr
      override var bookName: String = "Book Name " + itr
    }
    booklist += (itr -> b)
  }*/

  def printAllTheBooks() : util.ArrayList[Book] = {
    for ((k,v) <- booklist){
      list.add(v)
      println("Book name = "+ v.bookName + "\tAuthor name = "+ v.authorName +"\n")
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
    print("Enter the key of the book : ")
    val key = scala.io.StdIn.readInt()
    if(booklist.keySet.exists(_==key)){
      var book = booklist.get(key)
      System.out.println(book.get.bookName)
      System.out.println(book.get.authorName)
      System.out.println(book.get.price)
      System.out.println(book.get.bookGenre)
    }else{
      println("No book available for the key you provided")
    }
  }

  def updateBookData(key : Int) : Unit = {
    val obj : Option[Book] = booklist.get(key)
    print("Enter new Book Name : ")
    val newbookname = scala.io.StdIn.readLine()
    print("Enter new Book Name : ")
    val newauthorname = scala.io.StdIn.readLine()
    print("Enter new Book Name : ")
    val newprice = scala.io.StdIn.readDouble()
    print("Enter new Book Name : ")
    val newbookGenre = scala.io.StdIn.readLine()

    var newObj = new Book {
      override var bookName: String = newbookname
      override var authorName: String = newauthorname
      override var price: Double = newprice
      override var bookGenre: String = newbookGenre
    }
    booklist(key) = newObj
  }

  def deleteBook(key : Int) : Unit = {
    booklist -= key
  }

  def addNewBooksFromServer(map: java.util.Map[String, String]) : Unit = {
      var newBook = new Book {
        override var bookName: String = map.get("BookName").replace("+"," ")
        override var authorName: String = map.get("AuthorName").replace("+"," ")
        override var price: Double = map.get("Price").toDouble
        override var bookGenre: String = map.get("Genre").replace("+"," ")
      }
      var count : Int = booklist.size+1
      booklist += (count -> newBook)

      for ((k,v) <- booklist){
        println("Book name = "+ v.bookName + "\tAuthor name = "+ v.authorName)
        println()
      }
      list.add(newBook)
      //list.add(booklist.get(count).get)
  }


  def deleteBook(map: java.util.Map[String, String]) : Unit = {
      var key : String = map.get("bt")
      list.remove(key.toInt)
  }

  def showBook(map: java.util.Map[String, String]) : Book = {
    var key : String = map.get("bt2")
    var book : Book = list.get(key.toInt)
    var index2 = Integer.parseInt(key)
    b = book
    return book
  }

  def updateBook(map: java.util.Map[String, String]) : Unit = {
    var index = list.indexOf(b)
    b.bookName = map.get("BookName").replace("+"," ")
    b.authorName= map.get("AuthorName").replace("+"," ")
    b.price= map.get("Price").toDouble
    b.bookGenre= map.get("Genre").replace("+"," ")
    list.set(index,b)
  }

  def main(args: Array[String]): Unit = {
    printAllTheBooks()
    addNewBooks()
    updateBookData(1)
  }


}
