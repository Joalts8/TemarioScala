import java.util.concurrent.locks.ReentrantLock

class DatoCompartido(nProcesadores: Int) {
  private var dato: Int = 0 // Dato a procesar
 // Numero de procesadores totales
  private var procPend: Int = 0 // Numero de procesadores pendientes de procesar el dato
 private var procesado = Array.fill(nProcesadores)(false)
 private var procesando = false
 private var datoGenerado = false
 private val l = new ReentrantLock(true)
 private val generar = l.newCondition()
 private val leer = l.newCondition()

  /* El Generador utiliza este metodo para almacenar un nuevo dato a procesar.
   * Una vez almacenado el dato se debe avisar a los procesadores de que se ha
   * almacenado un nuevo dato.
   *
   * Por ultimo, el Generador tendra que esperar en este metodo a que todos los
   * procesadores terminen de procesar el dato. Una vez que todos terminen,
   * se devolvera el resultado del procesamiento, permitiendo al Generador
   * la generacion de un nuevo dato.
   *
   * CS_Generador: Una vez generado un dato, el Generador espera a que todos los procesadores
   * terminen antes de generar el siguiente dato
   */
  def generaDato(d: Int): Int = {
    // COMPLETAR y colocar los mensajes en el lugar apropiado dentro del código
    l.lock()
    try{
      procesado = Array.fill(nProcesadores)(false)
      dato = d
      datoGenerado= true
      leer.signal()
      println(s"Dato a procesar: $dato")
      procPend = nProcesadores
      println(s"Numero de procesadores pendientes: $procPend")
      while (procPend != 0) generar.await()
      datoGenerado = false
      dato
    }finally {
      l.unlock()
    }
  }

  /* El Procesador con identificador id utiliza este metodo para leer el
   * dato que debe procesar (el dato se devuelve como valor de retorno del metodo).
   * Debera esperarse si no hay datos nuevos para procesar
   * o si otro procesador esta manipulando el dato.
   *
   * CS1_Procesador: Espera si no hay un nuevo dato que procesar.
   *                 Esto puede ocurrir porque el generador aun no haya almacenado
   *                 ningun dato o porque el Procesador ya haya procesado el dato
   *                 almacenado en ese momento en el recurso compartido.
   * CS2_Procesador: Espera a que el dato este disponible para poder procesarlo
   *                 (es decir, no hay otro Procesador procesando al dato)
   */
  def leeDato(id: Int): Int = {
    // COMPLETAR
    l.lock()
    try{
      while (procesado(id) == true || !datoGenerado || procesando) leer.await()
      procesando = true
      dato
    }finally {
      l.unlock()
    }
  }

  /* El Procesador con identificador id almacena en el recurso compartido el resultado
   * de haber procesado el dato. Una vez hecho esto actuara de una de las dos formas siguientes:
   *
   * (1) Si aun hay procesadores esperando a procesar el dato los avisara,
   * (2) Si el era el ultimo procesador avisara al Generador de que han terminado.
   */
  def actualizaDato(id: Int, datoActualizado: Int): Unit = {
    // COMPLETAR y colocar los mensajes en el lugar apropiado dentro del código
    l.lock()
    try{
      dato = datoActualizado
      procesado(id) = true
      procesando = false
      procPend -= 1
      println(s"	Procesador $id ha procesado el dato. Nuevo dato: $dato")

      println(s"Numero de procesadores pendientes: $procPend")
      if(procPend != 0) {
        leer.signal()
      } else {
        generar.signal()
      }
    }finally {
      l.unlock()
    }
  }
}