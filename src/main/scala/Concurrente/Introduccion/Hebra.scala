package Concurrente.Introduccion

//Proceso ligero-> Hebra; run es lo que se ejecuta al invocarla(Hilo de to la vida)
class Hebra extends Thread {
  override def run() = println("Hola Mundo")
}
//Hebra implementando la interfaz Runnable-> da metodo run
class Escribir(c: Char) extends Runnable {
  override def run =
    for (i <- 0 until 10) print(c)
}

// creacion de hebra que se ejecuta al llemarla dandole un cuerpo-> body:=>Unit   **IMPORTANTE**
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
//hilo q se ejecuta en bucle cada t tiempo(si t=0, se puede eliminar sleep), se puede implementar sobre el anterior(crear val;start;dev val)
def periodico(t: Long)(b: => Unit): Thread = {
 new Thread {                 //sirve thread(while (true) { b;Thread.sleep(t)}); pero haria el start
  override def run() = {
   while (true) {
    b
    Thread.sleep(t)
   }
  }
 }
}
//hilo qhe debuelve un valor dada una funcion, editable para + fun-> tupla; o ninguna y que devuelva
def devolver[A](a: => A): A = {
  var resA: A = null.asInstanceOf[A]
  val h1 = new Thread {           //usable el anterior  thread(resA=a);h1.join
    override def run(): Unit = {
      resA = a
    }
  }
  h1.start(); h1.join()
    resA
}


@main def MainHebra = {
 //Creacion de Hebras heredando Thread e implmentando Runnable
 val h1 = new Hebra
 val h2 = new Thread(new Escribir('A')) //se pasa su referencia a un Thread
 val h4 = new Thread(new Escribir('B'))
 val h3 = new Thread(new Escribir('C'))
 //Metodos de las Hebras
 h1.start(); h2.start(); h4.start(); h3.start() //Ejecuta asíncrona, NUNCA RUN=> secuencial
 h4.join() //Metodo de sincronización, espera a que h4 acabe
 Thread.sleep(5000) //suspender durante 5 seg
 println(Thread.currentThread().getName) //Devuelve el nombre(puede h.getName) la hebra que se está ejecutando(existe h.setName)
 
 //creacion de clase thead la hace start directo
 val t = thread {
  Thread.sleep(1000)
  log("Una hebra en ejecución")
  Thread.sleep(1000)
  log("Aún en ejecución")
  Thread.sleep(1000)
  log("Terminado")
 }
 val hilo1 = periodico(1000)(println("Hello "))
 val hilo2 = periodico(3000)(println("World "))
 hilo1.start();hilo2.start()
  println(devolver("Hello " + "World"))
}