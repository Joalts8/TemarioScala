package Concurrente.Introduccion.Practica4

@volatile var turno = 0
@volatile var iter=0
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

@main def EJ2bMain = {
  val hilo1 = periodico(1000)(println("Hello "))
  val hilo2= periodico(3000)(println("World "))
}