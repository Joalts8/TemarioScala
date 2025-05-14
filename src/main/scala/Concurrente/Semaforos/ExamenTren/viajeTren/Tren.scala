package Concurrente.Semaforos.ExamenTren.viajeTren

import java.util.concurrent.Semaphore

class Tren {
  val N = 10
  var nPasajeros = 0
  val mutex = new Semaphore(1)
  val tren = new Semaphore(1)
  val empezarViaje = new Semaphore(0)
  val vagonPrimeroSalir = new Semaphore(0)
  val vagonSegundoSalir = new Semaphore(0)
  def viaje(id: Int): Unit = {
    var vagon = 0
    tren.acquire()
    mutex.acquire()
    if(nPasajeros < N/2) {
      println(s"pasajero $id ha subido al vagón 1")
      vagon = 1
    } else {
      println(s"pasajero $id ha subido al vagón 2")
      vagon = 2
    }
    nPasajeros += 1
    if(nPasajeros < N) {
      tren.release()
    } else {
      empezarViaje.release()
    }
    mutex.release()
    if(vagon == 1) vagonPrimeroSalir.acquire()
    else vagonSegundoSalir.acquire()
    mutex.acquire()
    println(s"pasajero $id ha bajado del vagón $vagon")
    nPasajeros -= 1
    if(nPasajeros <= N/2 && nPasajeros != 0) {
      vagonSegundoSalir.release()
    }else if(nPasajeros <= N && nPasajeros > N/2){
      vagonPrimeroSalir.release()
    }else if(nPasajeros == 0) {
      println("**********************************")
      tren.release()
    }
    mutex.release()
  }

  def empiezaViaje(): Unit = {
    empezarViaje.acquire()
    println("        Maquinista:  empieza el viaje")
  }

  def finViaje(): Unit = {
    println("        Maquinista:  fin del viaje")
    vagonPrimeroSalir.release()
  }
}