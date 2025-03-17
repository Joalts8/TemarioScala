package Funcional.Practica1

import scala.runtime.Nothing$

object Practica1 extends App{
  def primeFactors(n: Int): List[Int]={
    def bucle(n:Int, div:Int, acc:List[Int]):List[Int]= {
      if n<=1
        then return acc
      else if n<div then acc.appended(n)
      else if n%div==0 then {
          bucle(n/div, div,div::acc)
        }
      else bucle(n, div+1, acc)
    }
  bucle(n,2, List[Int]()).reverse
  }

  def binarySearch(arr: Array[Int], elt: Int): Option[Int]={
    def busqueda(arr:Array[Int], elt: Int, min:Option[Int], max:Option[Int]):Option[Int]={
      val mid:Int=min.get+(max.get-min.get)/2
      if(min.get==max.get){
        if(arr(min.get)==elt){
          min
        }else{
          None
        }
      }else if (arr(mid)>=elt) busqueda (arr, elt, min , Some(mid))
      else busqueda(arr,elt,Some(mid+1),max)
    }
    busqueda(arr,elt,Some(0), Some(arr.length-1))
  }

  def unzip(l:List[(Int,Char)]):(List[Int],List[Char])={
    l match
      case Nil=>(Nil,Nil)
      case (a,b)::r=> {val t=unzip(r)
      (a::t._1,b::t._2)
      }
  }

  def zip(t:(List[Int],List[Char])):List[(Int,Char)]={
    t match
      case (Nil, Nil) => Nil
      case (a::r,b::l) => {
        zip(r,l).prepended(a,b)
      }
  }

  def filter[A](l:List[A], f:A=>Boolean):List[A]={
    l match
      case Nil=> Nil
      case (a::r) => if(f(a)==true) then a::filter(r,f)
        else filter(r,f)
  }

  def map[A,B](l:List[A], f: A => B): List[B]={
    l match
      case Nil=>Nil
      case a::t=> f(a)::map(t,f)
  }

  def groupBy[A, B](l: List[A], f: A => B): Map[B, List[A]] = {
    l match
      case Nil=>Map.empty
      case a::r=> val mapa=groupBy(r,f)
        if(mapa.contains(f(a))){
          mapa+(f(a)-> mapa(f(a)).appended(a))
        }else{
          mapa+(f(a)->List(a))
        }
  }

  def reduce[A](l: List[A], f: (A,A) => A):A = {
    l match
      case a::r=> r match
        case Nil=> a
        case _ => f(a,reduce(r,f))
  }

  def subsets[A](s:Set[A]):Set[Set[A]]={
    if s.isEmpty then Set(Set.empty):Set[Set[A]]
    else {
      val t = subsets(s.tail)
      t.map(_+s.head)++t
    }
  }

  def generateParentheses(n: Int): List[String] = {
    @scala.annotation.tailrec
    def generate(remaining: List[(Int, Int, String)], acc: List[String]): List[String] = {
      remaining match {
        case Nil => acc
        case (open, closed, str) :: rest if open == n && closed == n =>
          generate(rest, str :: acc)
        case (open, closed, str) :: rest =>
          var newStack = rest
          if (open < n) {
            newStack = (open + 1, closed, str + "(") :: newStack
          }
          if (closed < open) {
            newStack = (open, closed + 1, str + ")") :: newStack
          }
          generate(newStack, acc)
      }
    }

    generate(List((0, 0, "")), Nil)
  }


  val arr = Array(1, 3, 5, 7, 9, 11)
  println(primeFactors(60)) // Output: List(2, 2, 3, 5)
  println(primeFactors(97)) // Output: List(97)
  println(primeFactors(84)) // Output: List(2, 2, 3, 7)
  println(binarySearch(arr, 5)) // Output: Some(2)
  println(binarySearch(arr, 8)) // Output: None
  println(unzip(List((10, 'a'), (20, 'b'), (10, 'c'))))
  println(zip(List(10, 20, 10),List('a', 'b', 'c')))
  println(filter(List(1,2,3,4,5,6),x=> x%2==0))
  println(map(List(1,2,3,4,5), _ * 2)) // Output: List(2,4,6,8,10)
  println(groupBy(List(1, 2, 3, 4, 5), _ % 2 == 0))  // Output: Map(false -> List(5, 3, 1), true -> List(4, 2))
  println(reduce(List(1,2,3,4,5), _ + _)) // Output: 15
  println(subsets(Set())) // Output: Set(Set())
  println(subsets(Set(1))) // Output: Set(Set(), Set(1))
  println(subsets(Set(1, 2))) // Output: Set(Set(),Set(1),Set(2),Set(1,2))
  println(subsets(Set(1, 2, 3)))  // Output: Set(Set(),Set(1),Set(2),Set(1,2),Set(3),Set(1,3),Set(2,3),Set(1,2,3))

}


