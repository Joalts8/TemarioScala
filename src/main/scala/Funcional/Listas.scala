enum Lista[+A]:
  case Nula
  case Cons(a:A,r:Lista[A])

  def suma(ints: Lista[Int]): Int =
    ints match
      case Nula => 0
      case Cons(x, resto) => x + suma(resto)

  def sumal(l: Lista[Int]): Int = {
    def bucle(l: Lista[Int], acc: Int): Int = {
      l match
      case Nula => acc
      case Cons (a, r) => bucle (r, a + acc)
    }

    bucle(l, 0)
  }

  def producto(l: Lista[Int]): Int = {
    def bucle(l: Lista[Int], acc: Int): Int = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, a * acc)
    }

    bucle(l, 1)
  }

  def operar[A](l:Lista[A],inic:A,f:(A,A)=>A)= {
    def bucle(l: Lista[A], acc: A): A = {
      l match
        case Nula => acc
        case Cons(a, r) => bucle(r, f(a,acc))
    }

    bucle(l, inic)
  }

  def foldRight[A, B](l: Lista[A], acc: B, f: (A, B) => B): B = l match
    case Nula => acc
    case Cons(a, r) => f(a, foldRight(r, acc, f)) //resuelve de derecha a izda

  def foldLeft[A, B](l: Lista[A], acc: B, f: (A, B) => B): B =
    l match
      case Nula => acc
      case Cons(a, r) => foldLeft(r, f(a, acc), f) //acumula al revés

  def longitud[A](l: Lista[A]) =
    foldRight[A, Int](l, 0, (x, y) => y + 1)


object Lista {
  def apply[A](args:A*): Lista[A] = {
    if (args.isEmpty) Nula
    else Cons(args(0), apply(args.tail*))
  }
}

class Listas extends App {

}
