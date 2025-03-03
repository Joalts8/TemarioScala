package Funcional

//definicion de lista, que puede ser nula o valor, sublista(cola)
enum EDPolimorfica[+A]:
  case Nula
  case Cons(h: A, t: EDPolimorfica[A])

object EDPolimorfica:
  //metodo apply(visto en clase) dado tantos elems tipo A, coge el primero y hace la sublista con la cola de args(1 a n)
  def apply[A](args: A*): EDPolimorfica[A] =
    if (args.isEmpty) Nula
    else Cons(args(0), apply(args.tail *))


