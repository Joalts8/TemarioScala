object Main {
  // tools-> scala REPL para ejecutar codigo sin escribirlo en fichero
  def main(args: Array[String]): Unit = {
    println("Hello world")
    // declaracion de constante y variables(se puede indicar o no el tipo)
    val entero=34
    var palabra="Hello world"
    var entero2: Int = 10

    entero2=entero+entero2

    //Interpolacion de String-> En vez palabra= "bla + palabra+ ", " + entero2; si se opera-> ${val+bla}
    palabra= s"bla $palabra, $entero2" //s= string; f= variar formato ; raw= quita \n
    //mostrar en pantalla
    println(palabra)
    //declaracion de funcion, se puede usar en repl con load archivo
    def max(x: Int, y: Int) =
      if (x > y) x else y
  }
}
//prueba