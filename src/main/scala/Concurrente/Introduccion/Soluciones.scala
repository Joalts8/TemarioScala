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

//Peterson-> EMEA q satisface 4.Justicia-> si ambos procesos quieren entrar simultáneamente, entra uno y despues otro. Es solo para 2 procesos
//f1 y f2 = false (si quieren entrar) y turno =1-> Volatile var       Cada proceso se declara con las var opuestas:
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

//Alg Panaderia-> Para +2 procesos-> se tiene N, un array=0 y otro =false de n long-> mirar abajo
//preprotocolo-> procesos.entering(i) = true; procesos.number(i) = (1 to procesos.numProcesses).max + 1; procesos.entering(i) = false
// comprovacion-> for (j <- 0 until procesos.numProcesses) {while (procesos.entering(j)) Thread.sleep(0);
//          while (procesos.number(j) != 0 && (procesos.number(i) > procesos.number(j) || (procesos.number(i) == procesos.number(j) && i > j))) Thread.sleep(0)}
// postprotocolo->  procesos.number(i) = 0

  //Ejemplo-> explicacion
class BakeryAlgorithm {
  val numProcesses = 3//Se puede borrar sustituyendo por number.length
  @volatile var number = Array.fill(numProcesses)(0) // Número de turno para cada proceso
  @volatile var entering = Array.fill(numProcesses)(false) // Indica si un proceso está intentando entrar
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
    // se crean los 2 procesos de forma opuesta
    val cont = new JardinesContadorSol
    val puerta1 = thread(for (i <- 0 until 100) {
      cont.f1 = true//quiere entrar
      cont.turno = 2//da turno al 2o por si quiere entrer
      //comprueba si alguien esta en zona critica
      while (cont.f2 && cont.turno == 2) Thread.sleep(0)
      cont.inc//zona critica
      cont.f1 = false//Postprotocolo
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

object mainPan extends App {
  val procesos = new BakeryAlgorithm()
  for (i <- 0 until procesos.numProcesses) {
    val p = thread {//Preprotocolo
      procesos.entering(i) = true //intento de entrar
      procesos.number(i) = procesos.number.max + 1 // Toma el siguiente número disponible
      procesos.entering(i) = false //ya asignado su turno, espera
      // Paso 2: Esperar el turno según el número asignado-> otro proceso esta asignando su numero/ tiene un num menor
      for (j <- 0 until procesos.numProcesses) {
        while (procesos.entering(j)) Thread.sleep(0)
        while (procesos.number(j) != 0 && (procesos.number(i) > procesos.number(j) || (procesos.number(i) == procesos.number(j) && i > j))) Thread.sleep(0)
      }
      // Paso 3: Está en la sección crítica
      println(s"Proceso $i está en la sección crítica.")
      Thread.sleep(1000)
      // Paso 4: Postprotocolo
      println(s"Proceso $i ha terminado en la sección crítica.")
      procesos.number(i) = 0
    }
  }
}
