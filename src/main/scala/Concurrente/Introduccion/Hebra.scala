package Concurrente.Introduccion

//Proceso ligero-> Hebra; run es lo que se ejecuta al invocarla(Hilo de to la vida)
class Hebra extends Thread{
 override def run()=println("Hola Mundo")
}
//Hebra implementando la interfaz Runnable-> da metodo run
class Escribir(c: Char) extends Runnable {
 override def run =
  for (i <- 0 until 10) print(c)
}
// creacion de hebra que se ejecuta al llemarla dandole un cuerpo-> body:=>Unit
def log(msg: String): Unit = {
 println(s"${Thread.currentThread().getName}: $msg")
}
def thread(body: => Unit): Thread = {
 val t = new Thread {
  override def run() = body
 }
 t.start()
 t
}

object mainHebra extends App{
 //Creacion de Hebras heredando Thread e implmentando Runnable
 val h1= new Hebra
 val h2 = new Thread(new Escribir('A')) //se pasa su referencia a un Thread
 val h4 = new Thread(new Escribir('B'))
 val h3 = new Thread(new Escribir('C'))
 //Metodos de las Hebras
 h1.start(); h2.start();h4.start();h3.start()    //Ejecuta asíncrona, NUNCA RUN=> secuencial
 h4.join()    //Metodo de sincronización, espera a que h4 acabe
 Thread.sleep(5000)   //suspender durante 5 seg
 println(Thread.currentThread().getName)   //Devuelve el nombre(puede h.getName) la hebra que se está ejecutando(existe h.setName)
 //creacion de clase thead
 val t =  thread{
  Thread.sleep(1000)
  log("Una hebra en ejecución")
  Thread.sleep(1000)
  log("Aún en ejecución")
  Thread.sleep(1000)
  log("Terminado")
 }
}
