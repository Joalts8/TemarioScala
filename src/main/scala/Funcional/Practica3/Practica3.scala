package Funcional.Practica3

import scala.collection.mutable

object Practica3 extends App{
  def sum(l: List[Int]): Int={
    l.foldRight(0)(_ + _)
  }
  def product(l: List[Int]): Int={
    if (l.length>0){
      l.foldRight(1)(_ * _)
    }else{
      0
    }

  }
  def length[A](l: List[A]): Int={
    l.foldRight(0)((x,y)=>1+y)
  }

  def reverse[A](l: List[A]): List[A]={
    l.foldLeft(Nil)((x,y)=>y::x)
  }
  def append[A](l1: List[A], l2: List[A]): List[A]={
    l1.foldRight(l2)(_::_)
  }

  def existe[A](l:List[A],f:A=>Boolean):Boolean={
    l.foldRight(false)((x,y)=>f(x) || y )
  }

  def f(l:List[Int]):List[Int]={
    def recursion(l: List[Int], acc: List[Int]): List[Int] = {
      l match
        case Nil => acc.reverse
        case a::r =>if(a<=0){
          recursion(r,-a::acc)
        }else{
          recursion(r, acc)
        }
    }
    recursion(l, Nil)
  }
  def f2(l:List[Int]):List[Int]={
    val l2= l.map(-_)
    l2.filter(_>=0)
  }

  def unzip[A,B](l:List[(A,B)]):(List[A],List[B])={
    l.foldRight((Nil,Nil))((x,y)=>(x._1::y._1,x._2::y._2))
  }

  def compose[A](lf:List[A=>A],v:A):A={
    lf.foldRight(v)((x,y)=>x(y))
  }

  def remdups[A](lista:List[A]):List[A]={
    lista.foldRight(Nil)((x,y)=> y match
      case c :: r if (c!=x) => x::y
      case Nil => x::y
      case _ => y )
  }

  def fibonnaci(n: Int): Int={
    require(n >= 0)
    (0 until n).foldRight((0, 1)) ((x,acc) =>(acc._2, acc._1 + acc._2))._1
  }

  def inits[A](l:List[A]):List[List[A]]={
    l.reverse.foldRight(List(List()))((x,y)=> y ::: List(y.last.appended(x)))
  }

  def halfEven(l1: List[Int], l2: List[Int]): List[Int]={
    def helper(l1: List[Int], l2: List[Int], acc: List[Int]): List[Int] = (l1, l2) match {
      case (Nil, _) | (_, Nil) => acc // Caso base: si alguna lista está vacía, devolvemos el acumulador
      case (x :: xs, y :: ys) =>
        val sum = x + y
        if (sum % 2 == 0) helper(xs, ys, acc :+ (sum / 2)) // Si la suma es par, la dividimos por 2 y la añadimos al acumulador
        else helper(xs, ys, acc) // Si no es par, continuamos sin añadir nada
    }
    helper(l1, l2, Nil)
  }
  def halfEven2(l1: List[Int], l2: List[Int]): List[Int] = {
    val l=l1.zip(l2)
    val l3= l.map((x, y) => x + y )
    val l4= l3.filter(_ % 2 == 0)
    l4.map(_ / 2)
  }

  def analisis(logs: List[String]):Map[String,Int]={
    val errors = logs.filter(_.startsWith("ERROR"))
    val warnings = logs.filter(_.startsWith("WARNING"))
    val info = logs.filter(_.startsWith("INFO"))
    val counts = Map("ERROR"->errors.length, "WARNING"->warnings.length, "INFO"-> info.length)
    counts
  }
  def errores(logs: List[String]):List[String] = {
    val errors = logs.filter(_.startsWith("ERROR"))
    errors
  }

  def ingresos(tuples: List[(String, Int, Double)]):Double={
    tuples.foldRight(0.0)((x,y)=>x._2*x._3+y)
  }
  def superior100(tuples: List[(String, Int, Double)]):List[(String,Double)]={
    val lista =tuples.foldRight(Nil)((x,y)=> y match
      case Nil =>if(x._2*x._3>=100){
        y.appended((x._1, x._2 * x._3))
      }else{
        y
      }
      case (z1,z2)::r if(z2<x._2*x._3) => if(x._2*x._3>=100){
        (z1,z2) :: (x._1, x._2 * x._3) :: r
      }else{
        y
      }
      case _ =>if(x._2*x._3>=100){
        (x._1, x._2 * x._3) :: y
      }else{
        y
      } )
    lista.reverse
  }

  def palUnicas(strings: Set[String], strings1: Set[String]):Set[String]={
    var set1 = Set(): Set[String]
    for(i<-strings){
      val s=i.split(" ")
      set1= s.foldRight(set1)((x, y)=>y+x.toLowerCase)
    }
    strings1.foldRight(set1)((x,y)=>y-x)
  }

  def cuentaPal(strings: List[String]):Map[String,Int]={
    strings.foldRight(Map():Map[String,Int])((x,y)=> if(y.contains(x)){
      y.updated(x,(y(x)+1))
    }else{
      y+(x->1)
    })
  }

  def mergeMap(map1: Map[String, Int], map2: Map[String, Int]):Map[String, Int]={
    map1.foldRight(map2)((x,y)=> if(y.contains(x._1)){
      y.updated(x._1,(y(x._1)+x._2))
    }else{
      y+x
    })
  }


  println(sum(List(1, 2, 3)) == 6)
  println(product(List(1, 3, 5)) == 15)
  println(length(List("Hola", " ", "Mundo")) == 3)

  println(reverse(List(1,2,3)) == List(3,2,1))
  println(append(List(1,2,3),List(1,2)) == List(1,2,3,1,2))

  println(existe(List(1,2,3),_>2) == true)
  println(existe(List("Hola", " ", "Mundo"),_.length>=5) == true)
  println(existe(List("Hola", "Mundo"),_.length<3) == false)

  println(f(List(1,-2,3,-4,-5,6)) == List(2,4,5))
  println(f2(List(1,-2,3,-4,-5,6)) == List(2,4,5))

  println(unzip(List((1,'a'),(2,'b'),(3,'c'))) == (List(1,2,3), List('a','b','c')))

  println(compose(List[Int => Int](Math.pow(_,2).toInt, _+2), 5) == 49)

  println(remdups(List(1,1,3,3,3,2,1,2,2,1,2)) == List(1, 3, 2, 1, 2, 1, 2))

  println(fibonnaci(5) == 5)
  println(fibonnaci(10) == 55)

  println(inits(List(1,2,3)) == List(List(),List(1),List(1,2),List(1,2,3)))
  println(inits(List(3)) == List(List(),List(3)))
  println(inits(List()) == List(List()))

  println(halfEven(List(1,2,3,4),List(3,2,4)) == List(2,2))
  println(halfEven2(List(1,2,3,4),List(3,2,4)) == List(2,2))

  val logs = List(
    "ERROR: Null pointer exception",
    "INFO: User logged in",
    "ERROR: Out of memory",
    "WARNING: Disk space low",
    "INFO: File uploaded",
    "ERROR: Database connection failed")
  println(analisis(logs)==Map("WARNING" -> 1, "ERROR" -> 3, "INFO" -> 2))
  println(errores(logs)==List("ERROR: Null pointer exception", "ERROR: Out of memory", "ERROR: Database connection failed"))

  val sales = List(
    ("Laptop", 2, 1000.0),
    ("Mouse", 10, 15.0),
    ("Keyboard", 5, 50.0),
    ("Monitor", 3, 200.0),
    ("USB Drive", 20, 5.0))
  println(ingresos(sales)==3100.0)
  println(superior100(sales))//List((Laptop,2000.0), (Monitor,600.0), (Keyboard,250.0), (Mouse,150.0),(USB Drive,100.0))) no ordena puto scala

  val sentences = Set(
    "Scala is a functional language",
    "The power of functional programming is great",
    "Functional programming is elegant")
  val stopWords = Set("a", "the", "is", "of")
  println(palUnicas(sentences, stopWords)==Set("programming", "language", "scala", "power", "elegant", "functional", "great"))

  val words = List("scala", "is", "awesome", "scala", "functional", "scala", "is", "great")
  println(cuentaPal(words)==Map("is" -> 2, "awesome" -> 1, "scala" -> 3, "functional" -> 1, "great" -> 1))

  val warehouse1 = Map("laptop" -> 5, "mouse" -> 20, "keyboard" -> 10)
  val warehouse2 = Map("laptop" -> 3, "mouse" -> 15, "monitor" -> 8)
  println(mergeMap(warehouse1,warehouse2)==Map("laptop" -> 8, "mouse" -> 35, "keyboard" -> 10, "monitor" -> 8))
}