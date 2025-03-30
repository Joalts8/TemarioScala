package Concurrente.Introduccion

//Solucion mediante Espera activa-> variable bool para saber si hay nuevo dato y bucles en escribe para q sino hay, añada; y si lo hay lee
object ProdConsVariableSol {
  @volatile private var hayDato = false   //volatile-> escribe en MP=> visible por todas las hebras
  private var valor: Int = 0;
  def escribe(nvalor: Int) = {
    while (hayDato) Thread.sleep(0)
    valor = nvalor
    hayDato = true
  }
  def lee = {
    while (!hayDato) Thread.sleep(0)
    val aux = valor
    hayDato = false
    aux
  }
}

//Exclusion mutua con EA
//1. Solo 1 hebra ejecuta su sección crítica a la vez-> Usar EA, 1 var para ver  si esta ejecutando f1,f2...
//2. Ausencia de livelock-> bloqueo en sus instrucciones de EA-> uso de variable turno para saber a quien le toca
//3. Progreso en la ejecución-> Si sólo una de las hebras quiere entrar en su SC, en algún momento debería poder hacerlo // Se mezclan ambas
/*run(){
 while (true){         //bucle en el run (normalmente for i<-m until n)
    preProtocolo0
    SC0               //seccion critica
    postProtocolo0
    SNC0              //seccion no critica
 }
}*/
//Peterson-> EMEA q satisface 4.Justicia-> si ambos procesos quieren entrar simultáneamente, entra uno y despues otro. Es solo para 2 procesos y:
//preprotocolo-> cont.f1 = true; cont.turno = 2;  while (cont.f2 && cont.turno == 2) Thread.sleep(0)      postprotocolo->  cont.f1 = false

//Solucion de Jardines por Peterson-> mirar abajo
class JardinesContadorSol {
  @volatile private var n = 0;
  @volatile var f1=false
  @volatile var f2=false
  @volatile var turno=1
  def inc = n += 1
  def num: Int = n;
}


object mainProductorConsumidorSol extends App {
  val prod = thread {
    for (i <- 0 until 10) {
      ProdConsVariableSol.escribe(i)
      log(s"Productor: $i")
    }
  }
  val cons = thread {
    for (i <- 0 until 10)
      log(s"Consumidor: ${ProdConsVariableSol.lee}")
  }
}

object MainSol {
  def main(args:Array[String]) = {
    val cont = new JardinesContadorSol
    val puerta1 = thread(for (i <- 0 until 100) {
      cont.f1 = true
      cont.turno = 2
      while (cont.f2 && cont.turno == 2) Thread.sleep(0)
      cont.inc
      cont.f1 = false
    })
    val puerta2 = thread(for (i <- 0 until 100) {
      cont.f2 = true
      cont.turno = 1
      while (cont.f1 && cont.turno == 1) Thread.sleep(0)
      cont.inc
      cont.f2 = false
    })
    puerta1.join();
    puerta2.join();
    log(s"valor num = ${cont.num}")
  }
}
