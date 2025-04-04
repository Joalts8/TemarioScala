package Funcional.Practica3Felix

import java.util
import scala.annotation.tailrec
import scala.collection.mutable

object Ejercicios extends App {
  //Ejercicio 1
  def sum(l: List[Int]): Int = {
    l.foldRight(0)((x, y) => x + y)
  }

  def product(l: List[Int]): Int = {

    l.foldRight(0)((x, y) => x * y)
  }

  def length[A](l: List[A]): Int = {
    l.foldRight(0)((x, y) => y + 1)
  }
  //println(length(List(1,2,3)))
  //Ejercicio 2

  def reverse[A](l: List[A]): List[A] = {

    l.foldLeft[List[A]](List())((x, y) => y :: x)
  }

  def append[A](l1: List[A], l2: List[A]): List[A] = {
    val a = l2.foldRight[List[A]](List())((x, y) => x :: y)
    l1.foldRight[List[A]](a)((x, y) => x :: y)
  }
  //println(append(List(1,2,3),List(1,2)))

  //Ejercicio 3

  def existe[A](l: List[A], f: A => Boolean): Boolean = {
    l.foldRight(false)((x, y) => y || f(x))
  }
  //println(existe(List("Hola","Mundo"),_.length<3))
//Comeme el culo
  //Ejercicio 4
  /*
    def f(l: List[Int]): List[Int] = {
      @tailrec
      def recTail(acc: List[Int], lista: List[Int]): List[Int] = {
        lista match
          case a :: b => {
            if (a < 0) recTail((a * (-1)) :: acc, b)
            else recTail(acc, b)
          }
          case _ => acc
      }

      recTail(List(), l).reverse
    }
   */
  def f(l: List[Int]): List[Int] = {
    l.filter(_ < 0).map(_ * (-1))
  }

  //println(f(List(1,-2,3,-4,-5,6)))

  //Ejercicio 5

  def unzip[A, B](l: List[(A, B)]): (List[A], List[B]) = {
    l.foldRight((List.empty[A], List.empty[B]))(
      (elem, acc) => {
        (elem._1 :: acc._1, elem._2 :: acc._2)
      }
    )
  }
  //println(unzip(List((1,'a'),(2,'b'),(3,'c'))) )

  //Ejercicio 6

  def compose[A](lf: List[A => A], v: A): A = {
    lf.foldRight(v)(
      (func, acum) => {
        func(acum)
      }
    )
  }
  //println(compose(List[Int => Int](Math.pow(_,2).toInt, _+2), 5))

  //Ejercicio 7


  def remdups[A](lista: List[A]): List[A] = {
    lista.foldRight[List[A]](Nil)(
      (elem, acc) => if (acc.isEmpty || acc.head != elem) elem :: acc
      else acc
    )
  }

  // EJercicio 8
  def fibonacci(n: Int): Int = {
    if (n <= 0) return 0
    if (n == 1) return 1


    val list = List.fill(n - 1)(0)


    list.foldRight((1, 0)) { (_, acc) =>
      (acc._1 + acc._2, acc._1)
    }._1
  }

  //Ejercicio 9
  def inits[A](l: List[A]): List[List[A]] = {
    l.foldRight[List[List[A]]](List(List()))(
      (elem, acum) => {
        List() :: acum.map(elem :: _)
      }
    )
  }
  //println(inits(List(1,2,3)))

  //Ejercicio 10
  /*
    def halfEven(l1:List[Int],l2:List[Int]):List[Int] = {
      @tailrec
      def auxTail(lista1:List[Int],lista2:List[Int], acc:List[Int]): List[Int] = {
        (lista1, lista2) match
          case (Nil, _) | (_,Nil) => acc.reverse
          case(h1::t1,h2::t2) =>
            val sum = h1+h2
            if (sum % 2 == 0) auxTail(t1, t2, (sum / 2) :: acc)
            else auxTail(t1, t2, acc)
      }
      auxTail(l1,l2,Nil)
    }
   */
  def halfEven(l1: List[Int], l2: List[Int]): List[Int] = {
    l1.zip(l2).map(
        (x) => x._1 + x._2
      ).filter(_ % 2 == 0)
      .map(_ / 2)
  }
  //println(halfEven(List(1,2,3,4),List(3,2,4)))

  //Ejercicio 11
  def funcionEjercicio(logs: List[String]): Unit = {
    val map = logs.map(_.split(":")(0))
      .groupBy(x => x)
      .map(pair => pair match
        case (k -> v) => k -> v.size)
    println(map)
    val diferentes = logs.toSet.toList.filter(_.startsWith("ERROR"))

    println(diferentes.toList)
  }

  val logs = List(
    "ERROR: Null pointer exception",
    "INFO: User logged in",
    "ERROR: Out of memory",
    "WARNING: Disk space low",
    "INFO: File uploaded",
    "ERROR: Database connection failed"
  )
  funcionEjercicio(logs)

  //Ejercicio 12

  def ejercicio12(sales: List[(String, Int, Double)]): Unit = {
    println(sales.foldRight(0.0)((x, y) => (x._2 * x._3) + y))
    val cuantias = sales.foldRight[List[(String, Double)]](Nil)(
      (tupla3, listacuantia) => {
        (tupla3._1, tupla3._2 * tupla3._3) :: listacuantia
      }
    ).sortWith((x, y) => x._2 > y._2)
    println(cuantias)
  }

  val sales = List(
    ("Laptop", 2, 1000.0),
    ("Mouse", 10, 15.0),
    ("Keyboard", 5, 50.0),
    ("Monitor", 3, 200.0),
    ("USB Drive", 20, 5.0)
  )

  //Ejercicio 13

  def ejercicio13(sentences: Set[String], stopWords: Set[String]): Set[String] = {
    val listwords = sentences.map(_.split(" ")).flatten.map(_.toLowerCase)
    val wordsWithouthStopWords = listwords -- stopWords
    wordsWithouthStopWords
  }

  //Ejercicio 14
  def ejercicio14(list: List[String]): Map[String, Int] = {
    list.groupBy(x => x).map(
      (x) => {
        x match
          case a -> b => {
            a -> b.size
          }
      }
    )
  }

  //Ejercicio 15
  def ejercicio15(mapa1: Map[String, Int], mapa2: Map[String, Int]): Map[String, Int] = {
    mapa1.foldLeft(mapa2)(
      (x, y) => {
        if (mapa2.contains(y._1)) {
          x + (y._1 -> (y._2 + mapa2(y._1)))
        } else {
          x + y
        }
      }
    )
  }
}

