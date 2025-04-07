package Concurrente.Introduccion.Practica4

class EJ1(c:Char, t:Int) extends Thread{
  override def run()={
    for(i<-0 until t){
      print(c)
    }
  }
}
class EJ1b(id:Int, c: Char, t: Int) extends Thread {
  //turno e iter?
  override def run() = {
    for (i <- 0 until t) {
      while(turno!= id) Thread.sleep(0)
      print(c)
    }
  }
}

object salida extends App{
  val hilo1=new EJ1('A',3)
  val hilo2=new EJ1('B', 5)
  val hilo3=new EJ1('C',9)
  hilo1.start();hilo2.start();hilo3.start()
}
