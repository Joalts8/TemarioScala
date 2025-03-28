package Funcional.Practica3

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
}


/*def foldRight[B](acc:B)(f:(A,B)=>B):B = l match
 case Nil => acc
 case a::r => f(a,r.foldRight(acc)(f))

def foldLeft[B](acc:B)(f:(B,A)=>B):B = l match
 case Nil => acc
 case a::r => r.foldLeft(f(acc,a))(f)*/