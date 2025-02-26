class SentenciasControl {
  def ifElse(bool: Any) = {
    if(bool==true) println("verdadero")
    else if(bool==false){
      println("false")
      bucle
    } else println("No es booleano")
  }

  def switch(x: Int) = { // puede usarse como switch normal o para asignar a algo
    val palabra = x match {
      case 1 => "Lunes"
      case 3 => {
        println(x)
        "msi"
      }
      case c if (c > 3 && c < 8) => "aña"
      case 2 | 9 | 8 => "QUE"
      case _ => "mondongo" // caso default
    }
  }

  def bucle = { // while es igual que en java
    val r = (1 to 10)
    for (elem <- r) { // estructura for(elemento<- coleccion/elem) o (bla to n)     foreach(a veces es funcion)
      println(elem)
    }
  }

  def tryCatch()={
    try {
      switch(3)
    } catch{
      case e: RuntimeException => println("errr" + e.getMessage)
    }
  }
}
