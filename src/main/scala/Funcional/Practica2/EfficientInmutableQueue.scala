package Funcional.Practica2

class EfficientQueue[T] private (private val front: List[T], private val rear: List[T]) extends ImmutableQueue[T] {
  def this(p: T*) = {
    this(p.toList, List[T]())

  }

  override def enqueue(elem: T): ImmutableQueue[T] = {
    if(front.isEmpty){
      EfficientQueue(elem::front,rear)
    }else{
      EfficientQueue(front,elem::rear)
    }
  }

  override def dequeue(): (T, ImmutableQueue[T]) = {
    front match
      case Nil => throw new RuntimeException("Cola vacía")
      case a :: Nil =>(a, EfficientQueue[T](rear.reverse,List[T]()))
      case a :: r => (a, EfficientQueue[T](r,rear))
  }

  def isEmpty: Boolean = {
    if (front.isEmpty) {
      true
    } else {
      false
    }
  }

  override def toString: String = {
    val aux = front:::rear.reverse
    aux.mkString("Queue(", ", ", ")")
  }

  override def equals(obj: Any): Boolean = {
    obj match
      case cola2: EfficientQueue[T] =>{
        val aux1=this.front:::this.rear.reverse
        val aux2=cola2.front:::cola2.rear.reverse
        aux1==aux2
      }
      case _ => false
  }

  override def hashCode(): Int = {
    (front:::rear.reverse).hashCode()
  }
}

@main def testImmutableQueue(): Unit = {
  val squeue = new EfficientQueue[Int]()
  val q = squeue.enqueue(1).enqueue(2).enqueue(3).enqueue(4)
  assert(q.dequeue() == (1, EfficientQueue(2, 3, 4)), s"${q.dequeue()} should be equal to (1, SimpleQueue(List(2, 3, 4)))")
  assert(squeue.isEmpty, s"{q} should be empty")
  assert(!q.isEmpty, s"{q should not be empty")
  val q2 = EfficientQueue(1, 2, 3, 4)
  assert(q == q2, s"${q} and ${q2} should be equal")
  assert(q.hashCode() == q2.hashCode(), s"The hash codes of ${q} and ${q2} should be equal")
}
