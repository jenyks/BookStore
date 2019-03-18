import java.util
import bookStore.{Book, BookStoreApp}
import org.scalatest.FunSuite
import scala.collection.mutable

class BookStoreAppTest extends FunSuite {
  var booklistTest:mutable.LinkedHashMap[Int, Book] = new mutable.LinkedHashMap()
  val b1 = Book("Harry Potter","J K Rowling",275.00,"Fantasy")
  val b2 = Book("Let Us C","Yashavant Kanetkar",580.00,"Programming")
  val b3 = Book("Functional Programming in Scala","Paul Chiusano",200.00,"Programming")
  val b4 = Book("The Lord of the Rings","J. R. R. Tolkien",440.00,"Fantasy")
  val b5 = Book("Sapiens: A Brief History of Humankind","Yuval Noah Harari",550.00,"History")

  booklistTest += (1 -> b1)
  booklistTest += (2 -> b2)
  booklistTest += (3 -> b3)
  booklistTest += (4 -> b4)
  booklistTest += (5 -> b5)

  var list =  new util.ArrayList[Book]
  for((k,v) <- booklistTest){
    list.add(v)
  }

  test("BookStoreApp.retrieveAll"){
    assert(BookStoreApp.retrieveAll() === list)
  }

  test("BookStoreApp.findById"){
    assert(BookStoreApp.findById(1) === b1)
    assert(BookStoreApp.findById(5) === b5)
  }

  test("BookStoreApp.removeById"){
    val oldSize = BookStoreApp.booklist.size
    BookStoreApp.removeById(1)
    val newSize = BookStoreApp.booklist.size
    assert(newSize === oldSize-1)
  }

  test("BookStoreApp.save"){
    val oldSize = BookStoreApp.booklist.size
    val newBook = Book("Test","Test",235,"Test")
    BookStoreApp.save(newBook)
    val newSize = BookStoreApp.booklist.size
    assert(newSize === oldSize+1)
  }

  test("BookStoreApp.updateBookData"){
    val updateBook = Book("Test","Update",235,"Test")
    BookStoreApp.updateBookData(2,updateBook)
    assert(updateBook === BookStoreApp.findById(2))
  }
}
