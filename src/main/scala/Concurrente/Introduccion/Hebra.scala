package Concurrente.Introduccion
//HASTA PG 23; falta sleep y join
class Hebra extends Thread{
 override def run()=println("Hola Mundo")
}

class Escribir(c: Char) extends Runnable {
 override def run =
  for (i <- 0 until 10) print(c)
}

object mainHebra extends App{
 val h1= new Hebra
 val h2 = new Thread(new Escribir('A'))
 h1.start(); h2.start()
}
