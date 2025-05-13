package Concurrente.Semaforos

import java.util.concurrent.*
import Concurrente.Introduccion.*

//Definicion de semaforo general-> mas de 1 elemto. EJ-> PC donde se producen varios y el consumidor va cogiendo. USA EA pero sin buffer
class GeneralBinario (v:Int){
  private var valor = v //cantidad de-> producido o gente esperando...
  private val espera = if (v == 0) new Semaphore(0) else new Semaphore(1) // Semaforo para saber cuentos procesos/consumidores esperan
  private val mutex = new Semaphore(1) //Exclusion mutua

  var varias= Array.fill(3)(new Semaphore(1)) //en ocasiones hay que usar un array de semaforos

  //Metodo que añade a espera
  def releaseGen() = {
    //Preprotocolo
    mutex.acquire()  //EM
    //Seccion crítica
    valor += 1
    if (valor == 1) espera.release()
    //Postprotocolo
    mutex.release()
  }
  //Metodo q consume unos de los esperando
  def acquireGen() = {
    //Preprotocolo
    espera.acquire()
    mutex.acquire()
    //SC
    valor -= 1
    if (valor > 0) espera.release()
    //Postprotocolo
    mutex.release()
  }
}

//Caso donde hay 2 colas-> Cuando se produce el tipo 1, hasta que no se consume, no se produce tipo 2(solo 1 a la vez)
object doblecola {
  private var nLectores = 0
  private val mutex = new Semaphore(1)  // En tipo 2 se puede usar otro al variar distintos parametos
  private val escribiendo = new Semaphore(1)

  private var nEscritores = 0
  private val leyendo = new Semaphore(1)
  private val mutex3 = new Semaphore(1) // Uso para que tipo2 (solo 1) tenga prioridad si está esperando

  def entraTipo2(id: Int) = {// si es salida, cambia q decrementa, if==0 y que leyendo y escriviedo hacen lo opuesto
    mutex.acquire()
    nEscritores += 1      //Si solo existe 1, esta y el if se pueden evitar
    if (nEscritores == 1) leyendo.acquire()
    mutex.release()
    //base, pero tipo 1 tomaria control
    escribiendo.acquire()
    log(s"Escritor $id entra en la BD. Hay $nLectores")
  }

  def entraTipo1(id: Int) = {  // Salida NO mutex3 ni leyendo(porq puede haber 1+); lo demás cambia como tipo 2
    mutex3.acquire()
    leyendo.acquire()
    mutex.acquire()
    nLectores += 1
    if (nLectores == 1) escribiendo.acquire()
    log(s"Lector $id entra en la BD. Hay $nLectores")
    mutex.release()
    leyendo.release()
    mutex3.release()
  }
}