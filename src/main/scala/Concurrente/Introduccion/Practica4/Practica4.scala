package Concurrente.Introduccion.Practica4
import Concurrente.Introduccion.*

import scala.util.Random

@volatile var turno = 0
@volatile var iter=0
@volatile var lista:List[Boolean]=Nil
class EJ1(c:Char, t:Int) extends Thread{
  override def run()={
    for(i<-0 until t){
      print(c)
    }
  }
}
class EJ1b(id:Int, c: Char, t: Int) extends Thread {
  override def run() = {
    for (i <- 0 until t) {
      while(turno!= id) Thread.sleep(0)
      print(c)
      iter=iter+1
      if((id+1)==iter){
        iter=0;turno=(turno+1)%3
      }
    }
  }
}

def periodico(t: Long)(b: => Unit): Thread={
  val th = new Thread {
    override def run() ={
      while (true) {
        b
        Thread.sleep(t)
      }
    }
  }
  th.start()//esto puede NO estar aqui y usarse como hilo normal pero con el bucle
  th
}

def parallel[A, B](a: => A, b: => B): (A, B)={
  var resA: A = null.asInstanceOf[A]
  var resB: B = null.asInstanceOf[B]
  val h1= new Thread{
    override def run(): Unit ={
      resA=a
    }
  }
  val h2=new Thread{
    override def run(): Unit ={
      resB=b
    }
  }
  h1.start();h2.start()
  h1.join();h2.join()
  (resA,resB)
}
def todosTrueIter(inic: Int, fin: Int): Boolean={
  for(i<- inic until fin){
    if(lista(i)==false) then return false
  }
  true
}
def todosTrueRecu(inic: Int, fin: Int): Boolean = {
  if(inic==fin){
    true
  }else if(lista(inic)==false){
    false
  }else{
    todosTrueRecu(inic+1,fin)
  }
}
def todosTrueThread(inic: Int, fin: Int): Boolean = {
  val mid= inic+fin/2
  val par= parallel(todosTrueIter(inic,mid),todosTrueRecu(mid,fin))
  par._1 && par._2
}


@main def EJ1Main = {
  val hilo1=new EJ1('A',3)
  val hilo2=new EJ1('B',5)
  val hilo3=new EJ1('C',9)
  hilo1.start();hilo2.start();hilo3.start()
}
@main def EJ1bMain = {
  val hilo1b = new EJ1b(0,'A', 3)
  val hilo2b = new EJ1b(1,'B', 6)
  val hilo3b = new EJ1b(2,'C', 9)
  hilo1b.start();hilo2b.start();hilo3b.start()
}

@main def EJ2Main = {
  periodico(1000)(println("Hello "))
  periodico(3000)(println("World "))
}

@main def EJ3Main = {
  println(parallel(3+2,"Hello "+"World"))
  //lista=List(true,true,true)
  lista=List.fill(Random.nextInt(10))(Random.nextBoolean())
  val max= lista.length
  println(todosTrueIter(0,max))
  println(todosTrueRecu(0,max))
  println(todosTrueThread(0,max))
}