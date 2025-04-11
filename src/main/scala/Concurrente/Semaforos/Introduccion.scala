package Concurrente.Semaforos

//AUX
import Concurrente.Introduccion.thread
val N=3
val m=2
//Clase semaforo de java
import java.util.concurrent.*
// s.acquire()-> suspende/reduce si el semaforo>0
// s.release()-> despierta/incrementa el valor si no suspendidi
// Al definirlo, parametros-> numero de permisos->hebras a la vez y fair-> si fifo 

// Solucion Exclusion mutua 2 y +2 hilos  
def ExclusionMutua2 = {
  val s = new Semaphore(1)
  val p1 = thread {
    while (true) {
      s.acquire()
      // SC1
      s.release()
    }
  }
  val p2 = thread {
    while (true) {
      s.acquire()
      // SC2
      s.release()
    }
  }
}

def ExclusionMutuaN = {//N->Numero procesos  m<N-> Hilos al mismo tiempo-> deberia ser 1
  val s = new Semaphore(m)
  val p = new Array[Thread](N)
  for (i <- 0 until p.length)
    p(i) = thread {
      while (true) {
        s.acquire()
        // SC
        s.release()
      }
    }
}

