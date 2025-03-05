package Funcional

object FuncionesPolimorficas extends App{
  //Pueden existir funciones en funciones
  //funcion polimorfica-> para tipo generico usando [T], y usando funcion A-> bollean
  def busca[A](v: Array[A], p: A => Boolean) = {  // esta condicion se puede definir _condicion(_ es generico) o elem=> condicion
    def bucle(n: Int): Int =
      if (n >= v.length) -1
      else if (p(v(n))) n
      else bucle(n + 1)

    bucle(0)
  }

  // edit de una funcion, en este caso, dado A y f(A,B)=C, devuelve g(B)=C
  def partial1[A,B,C](a:A,f:(A,B)=>C):B => C = {
    y => f(a,y)
  }
  //otros casos
  def curry[A, B, C](f: (A, B) => C): A => (B => C)={
    a => b => f(a, b)
  }
  def uncurry[A, B, C](f: A => B => C): (A, B) => C ={
    (a, b) => f(a)(b)
  }
  def compose[A, B, C](f: B => C, g: A => B): A => C={
    y => f(g(y))
  }
  //metodo para ver si un array esta ordenado
  def isSorted[A](v: Array[A], gt: (A, A) => Boolean): Boolean =
    def loop(n: Int): Boolean =
      if (n >= v.length - 1) true
      else if (!gt(v(n), v(n + 1))) false
      else loop(n + 1)
    loop(0)


  //ejemplo sintasis en funcion polimorfica
  val array= Array[Int](1,2,3,4,5)
  println(busca(array, _%2==0))
  println(busca(array, x=>x%3==0))

  //ejemplo de array ordenado
  val prueba = Array[Int](1, 2, 3, 4)
  if (isSorted(prueba, _ >= _)) println("true")
  else println("false")

  //pruebas de mod de funciones
  def suma (x:Int) =x+5
  def producto (x:Int) =x*2
  val fun = compose(producto, suma)
  println(fun(2))
}
