package Concurrente.Introduccion.Practica5

import Concurrente.Introduccion.thread
import Concurrente.Introduccion.log

object EJ1 {
  val N = 20
  val producir = 100
  @volatile var elem = new Array[Int](N)
  @volatile var p = 0
  @volatile var c = 0
  @volatile var nelem = 0

  @volatile var f1 = false
  @volatile var f2 = false
  @volatile var turno = 1

  def escribe(nvalor: Int) = {
    while (nelem == elem.length) Thread.sleep(0)
    f1 = true
    turno = 2
    while (f2 && turno == 2) Thread.sleep(0)
    elem(p) = nvalor
    p += 1
    p = p % N
    nelem += 1
    f1 = false
  }

  def lee = {
    while (nelem == 0) Thread.sleep(0)
    f2 = true
    turno = 1
    while (f1 && turno == 1) Thread.sleep(0)
    val aux = elem(c)
    c += 1
    c = c % N
    nelem -= 1
    f2 = false
    aux
  }
}

object EJ2 {
  @volatile var nvAgua = 0
  val nvMax = 1000
  val aumRio = 1000
  val disPresa = 1000
  val rios = 2
  val presas = 2
  val numProcesses = rios + presas //Se puede borrar sustituyendo por number.length
  @volatile var number = Array.fill(numProcesses)(0) // Número de turno para cada proceso
  @volatile var entering = Array.fill(numProcesses)(false) // Indica si un proceso está intentando entrar
}

@main def mainProductorConsumidorSol() = {
  val n = EJ1.producir
  val prod = thread {
    for (i <- 0 until n) {
      log(s"Productor: $i")
      EJ1.escribe(i)
    }
  }
  val cons = thread {
    for (i <- 0 until n) {
      log(s"Consumidor: ${EJ1.lee}")
    }
  }
}

@main def mainEJ2() = {
  for (i <- 0 until EJ2.numProcesses) {
    if (i < EJ2.rios) {
      val p = thread { //Preprotocolo
        for (k <- 0 until EJ2.aumRio) {
          while (EJ2.nvAgua >= EJ2.nvMax) Thread.sleep(0)
          EJ2.entering(i) = true //intento de entrar
          EJ2.number(i) = (1 to EJ2.numProcesses).max + 1 // Toma el siguiente número disponible
          EJ2.entering(i) = false //ya asignado su turno, espera
          // Paso 2: Esperar el turno según el número asignado-> otro proceso esta asignando su numero/ tiene un num menor
          for (j <- 0 until EJ2.numProcesses) {
            while (EJ2.entering(j)) Thread.sleep(0)
            while (EJ2.number(j) != 0 && (EJ2.number(i) > EJ2.number(j) || (EJ2.number(i) == EJ2.number(j) && i > j))) Thread.sleep(0)
          }
          // Paso 3: Está en la sección crítica
          EJ2.nvAgua += 1
          log(s"Nivel del agua= ${EJ2.nvAgua}")
          // Paso 4: Postprotocolo
          EJ2.number(i) = 0
        }
      }
    } else {
      val p = thread { //Preprotocolo
        for (k <- 0 until EJ2.disPresa) {
          while (EJ2.nvAgua <= 0) Thread.sleep(0)
          EJ2.entering(i) = true //intento de entrar
          EJ2.number(i) = (1 to EJ2.numProcesses).max + 1 // Toma el siguiente número disponible
          EJ2.entering(i) = false //ya asignado su turno, espera
          // Paso 2: Esperar el turno según el número asignado-> otro proceso esta asignando su numero/ tiene un num menor
          for (j <- 0 until EJ2.numProcesses) {
            while (EJ2.entering(j)) Thread.sleep(0)
            while (EJ2.number(j) != 0 && (EJ2.number(i) > EJ2.number(j) || (EJ2.number(i) == EJ2.number(j) && i > j))) Thread.sleep(0)
          }
          // Paso 3: Está en la sección crítica
          EJ2.nvAgua -= 1
          log(s"Nivel del agua= ${EJ2.nvAgua}")
          // Paso 4: Postprotocolo
          EJ2.number(i) = 0
        }
      }
    }
  }
}