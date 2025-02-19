object Main {
  // tools-> scala REPL para ejecutar codigo sin escribirlo en fichero, ; no es obligatorio
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

    // array, se puede no poner new, val hace const al array, no a sus valores
    val saludos = new Array[Int](3) // Array[tipo](tam/valores)
    saludos(0) = 0 // da valor a index
    saludos(1) = 1
    saludos(2) = 2
    println(saludos.apply(0)) // muestra valor en index
    saludos.update(0, 8) //mod valor
    println(saludos.apply(0))
    val saludosComa=saludos.mkString(",") //separa los elementos eso
    //lista, siempre inmutable sus elementos
    val l = List(1,2,3)// si ()-> lista vacia; nil= lista nothin
    val  l2= 1::l     // une un entero y lista anterior o varios enteros y nil
    val  l3= l:::l2  // une 2 listas
    //tuplas, 2 elementos
    var par =(99,"burbuja")
    println(par._1) // imprime 1er valor, ._2 segundo, sirve (index)

  //declaracion de funcion, se puede usar en repl con load archivo
  def max(x: Int, y: Int) =
      if (x > y) x else y
  }
}