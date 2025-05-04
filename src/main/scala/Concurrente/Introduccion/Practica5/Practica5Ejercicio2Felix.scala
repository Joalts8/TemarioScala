package Concurrente.Introduccion.Practica5

import Concurrente.Introduccion.thread

class Ejercicio2() {
  @volatile var turno = Array.fill(4)(0)

  def meToca(i: Int, j: Int): Boolean = {
    //Esta funcion refleja cuando le toca a i antes que a j
    if (turno(j) == 0) return true
    if (turno(i) < turno(j)) return true
    if (turno(i) == turno(j) && i < j) return true
    if (i == j) return true
    else return false
  }
}



object Ejercicio2 {



  @main def Ej2(): Unit = {
    @volatile var nivelDelAgua = 0
    @volatile var entrando = Array.fill(4)(false)

    @volatile val fnc = new Ejercicio2()


    val rio1 = thread {
      for (i <- 0 until 2000) {
        entrando(0) = true
        fnc.turno(0) = fnc.turno.max + 1
        entrando(0) = false
        for (j <- 0 until 4) {
          while (entrando(j)) Thread.sleep(0)
          while (!fnc.meToca(0, j))  {
            Thread.sleep(0)
          }
        }
        println(s"Rio1 ${fnc.turno.mkString(",")}")
        nivelDelAgua += 1

        fnc.turno(0) = 0
      }
    }
    val rio2 = thread {
      for (i <- 0 until 2000) {
        entrando(1) = true
        fnc.turno(1) = fnc.turno.max + 1
        entrando(1) = false
        for (j <- 0 until 4) {
          while (entrando(j)) Thread.sleep(0)
          while (!fnc.meToca(1, j)) Thread.sleep(0)
        }
        println(s"Rio2 ${fnc.turno.mkString(",")}")
        nivelDelAgua += 1

        fnc.turno(1) = 0
      }
    }

    val presa1 = thread {
      for (i <- 0 until 2000) {
        var repetir = true
        while (repetir) {
          repetir = false
          entrando(2) = true
          fnc.turno(2) = fnc.turno.max + 1
          entrando(2) = false
          for (j <- 0 until 4) {
            while (entrando(j)) Thread.sleep(0)
            while (!fnc.meToca(2, j)) Thread.sleep(0)
          }
          if (nivelDelAgua == 0) {
            repetir = true
          }
        }
        println(s"Presa1 ${fnc.turno.mkString(",")}")
        nivelDelAgua -= 1

        fnc.turno(2) = 0
      }
    }

    val presa2 = thread {
      for (i <- 0 until 2000) {
        var repetir = true
        while (repetir) {
          repetir = false
          entrando(3) = true
          fnc.turno(3) = fnc.turno.max + 1
          entrando(3) = false
          for (j <- 0 until 4) {
            while (entrando(j)) Thread.sleep(0)
            while (!fnc.meToca(3, j)) Thread.sleep(0)
          }
          if (nivelDelAgua == 0) {
            repetir = true
          }
        }

        println(s"Presa2 ${fnc.turno.mkString(",")}")
        nivelDelAgua -= 1

        fnc.turno(3) = 0
      }
    }


    rio1.join()
    rio2.join()
    presa1.join()
    presa2.join()

    println(s"Nivel del lago: ${nivelDelAgua}")
  }
}

