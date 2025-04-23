package Concurrente.Semaforos

import java.util.concurrent.*

//Definicion de semaforo general-> mas de 1 elemto. EJ-> PC donde se producen varios y el consumidor va cogiendo. USA EA pero sin buffer
class GeneralBinario (v:Int){
  private var valor = v //cantidad de-> producido o gente esperando...
  private val espera = if (v == 0) new Semaphore(0) else new Semaphore(1) // Semaforo para saber cuentos procesos/consumidores esperan
  private val mutex = new Semaphore(1) //Exclusion mutua

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

