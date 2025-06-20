package Eventos.Practica8

import java.awt.event.{ActionEvent, ActionListener}
import java.awt.{BorderLayout, GridLayout}
import java.beans.{PropertyChangeEvent, PropertyChangeListener}
import javax.swing.*
import scala.concurrent.CancellationException


class Primo(val pos: Int, n1: Int, n2: Int) {
  val tupla = (n1, n2)

  override def toString: String = s"$pos:$tupla"
}



class WorkerTwin(n: Int, panel: Panel) extends SwingWorker[Unit, Primo] {
  def esPrimo(n: Int): Boolean = {
    def loop(div: Int): Boolean = {
      if (div * div > n) true
      else if (n % div == 0) false
      else loop(div + 1)
    }
    if (n == 1 || n == 2) true
    else loop(2)
  }

  def listaPrimos(n: Int): Unit =
    def loop(i: Int, pprimo: Int): Unit = {
      if (i < n && !this.isCancelled)
        if (esPrimo(pprimo))
          if (esPrimo(pprimo + 2))
            val primo = new Primo(i, pprimo, pprimo + 2)
            publish(primo)
            this.setProgress((i + 1) * 100 / n)
            loop(i + 1, pprimo + 1)
          else {
            loop(i, pprimo + 1)
          }
        else
          loop(i, pprimo + 1)
    }
    panel.nuevoMensaje("Calculando primos Twin",0)
    loop(0, 1)

  override def doInBackground(): Unit = {
    this.setProgress(0)
    listaPrimos(n)
  }

  override def done(): Unit = {
    try {
      get()
      panel.nuevoMensaje("Tarea finalizada",0)
    } catch {
      case e: CancellationException => panel.nuevoMensaje("Tarea cancelada",3)
    }
  }

  override def process(chunks: java.util.List[Primo]): Unit =
    panel.listaPrimos(chunks,0)

}


class WorkerCousin(n: Int, panel: Panel) extends SwingWorker[Unit, Primo] {
  def esPrimo(n: Int): Boolean = {
    def loop(div: Int): Boolean = {
      if (div * div > n) true
      else if (n % div == 0) false
      else loop(div + 1)
    }
    if (n == 1 || n == 2) true
    else loop(2)
  }

  def listaPrimos(n: Int): Unit =
    def loop(i: Int, pprimo: Int): Unit = {
      if (i < n && !this.isCancelled)
        if (esPrimo(pprimo))
          if (esPrimo(pprimo + 4))
            val primo = new Primo(i, pprimo, pprimo + 4)
            publish(primo)
            this.setProgress((i + 1) * 100 / n)
            loop(i + 1, pprimo + 1)
          else {
            loop(i, pprimo + 1)
          }
        else
          loop(i, pprimo + 1)
    }
    panel.nuevoMensaje("Calculando primos Cousin",1)
    loop(0, 1)

  override def doInBackground(): Unit = {
    this.setProgress(0)
    listaPrimos(n)
  }

  override def done(): Unit = {
    try {
      get()
      panel.nuevoMensaje("Tarea finalizada",1)
    } catch {
      case e: CancellationException => panel.nuevoMensaje("Tarea cancelada",3)
    }
  }

  override def process(chunks: java.util.List[Primo]): Unit =
    panel.listaPrimos(chunks,1)
}


class WorkerSexy (n: Int, panel: Panel) extends SwingWorker[Unit, Primo]{
  def esPrimo(n: Int): Boolean = {
    def loop(div: Int): Boolean = {
      if (div * div > n) true
      else if (n % div == 0) false
      else loop(div + 1)
    }

    if (n == 1 || n == 2) true
    else loop(2)
  }

  def listaPrimos(n: Int): Unit =
    def loop(i: Int, pprimo: Int): Unit = {
      if (i < n && !this.isCancelled)
        if (esPrimo(pprimo))
          if (esPrimo(pprimo + 6))
            val primo = new Primo(i, pprimo, pprimo + 6)
            publish(primo)
            this.setProgress((i + 1) * 100 / n)
            loop(i + 1, pprimo + 1)
          else {
            loop(i, pprimo + 1)
          }
        else
          loop(i, pprimo + 1)
    }
    panel.nuevoMensaje("Calculando primos Sexy",2)
    loop(0, 1)

  override def doInBackground(): Unit = {
    this.setProgress(0)
    listaPrimos(n)
  }

  override def done(): Unit = {
    try {
      get()
      panel.nuevoMensaje("Tarea finalizada",2)
    } catch {
      case e: CancellationException => panel.nuevoMensaje("Tarea cancelada",3)
    }
  }

  override def process(chunks: java.util.List[Primo]): Unit =
    panel.listaPrimos(chunks, 2)
}



class Controlador(panel: Panel) extends ActionListener, PropertyChangeListener {
  private var workerT: WorkerTwin = null
  private var workerC: WorkerCousin = null
  private var workerS: WorkerSexy = null

  override def actionPerformed(e: ActionEvent): Unit = {
    if (e.getActionCommand.equals("TWIN")) {
      try {
        val n = panel.numero(0)
        panel.limpiarArea(0)
        panel.nuevoProgreso(0, 0)
        workerT = new WorkerTwin(n, panel)
        workerT.addPropertyChangeListener(this)
        workerT.execute()
      } catch {
        case e: NumberFormatException => panel.nuevoMensaje("Número incorrecto",0)
      }
    } else if (e.getActionCommand.equals("COUSIN")) {
      try {
        val n = panel.numero(1)
        panel.limpiarArea(1)
        panel.nuevoProgreso(0, 1)
        workerC = new WorkerCousin(n, panel)
        workerC.addPropertyChangeListener(this)
        workerC.execute()
      } catch {
        case e: NumberFormatException => panel.nuevoMensaje("Número incorrecto",1)
      }
    } else if (e.getActionCommand.equals("SEXY")) {
      try {
        val n = panel.numero(2)
        panel.limpiarArea(2)
        panel.nuevoProgreso(0, 2)
        workerS = new WorkerSexy(n, panel)
        workerS.addPropertyChangeListener(this)
        workerS.execute()
      } catch {
        case e: NumberFormatException => panel.nuevoMensaje("Número incorrecto",2)
      }
    } else if (e.getActionCommand.equals("CANCELAR")) {
      if (workerT != null) workerT.cancel(true)
      if (workerC != null) workerC.cancel(true)
      if (workerS != null) workerS.cancel(true)
    }
  }

  override def propertyChange(evt: PropertyChangeEvent): Unit = {
    if (evt.getPropertyName.equals("progress")) {
      val progreso = evt.getNewValue.asInstanceOf[Int]
      evt.getSource match {
        case src if src == workerT => panel.nuevoProgreso(progreso, 0)
        case src if src == workerC => panel.nuevoProgreso(progreso, 1)
        case src if src == workerS => panel.nuevoProgreso(progreso, 2)
      }
    }
  }
}



  class Panel extends JPanel {
    val TWIN_FIELD = "NUMBER1"
    val COUSIN_FIELD = "NUMBER2"
    val SEXY_FIELD = "NUMBER3"
    val CANCEL_BUTTON = "CANCEL"

    // Labels and fields
    private val twinLabel = JLabel("cuántos primos twin quieres?")
    private val twinField = JTextField(3)

    private val cousinLabel = JLabel("cuántos primos cousin quieres?")
    private val cousinField = JTextField(3)

    private val sexyLabel = JLabel("cuantos primos sexy quires?")
    private val sexyField = JTextField(3)

    private val statusMessage = JLabel("GUI Creada")

    private val twinArea = JTextArea(10, 40)
    private val cousinArea = JTextArea(10, 40)
    private val sexyArea = JTextArea(10, 40)

    private val twinScroll = JScrollPane(twinArea)
    private val cousinScroll = JScrollPane(cousinArea)
    private val sexyScroll = JScrollPane(sexyArea)

    private val twinMsg = JLabel("Área Twin creada")
    private val cousinMsg = JLabel("Área Cousin creada")
    private val sexyMsg = JLabel("Área Sexy creada")

    private val cancelButton = JButton("Cancelar")

    private val progressTwin = JProgressBar(0, 100)
    private val progressCousin = JProgressBar(0, 100)
    private val progressSexy = JProgressBar(0, 100)

    init()

    private def init(): Unit = {
      this.setLayout(BorderLayout())

      val northPanel = JPanel()
      northPanel.add(cancelButton)

      val centerPanel = JPanel(GridLayout(1, 3))

      progressTwin.setValue(0)
      progressTwin.setStringPainted(true)
      progressCousin.setValue(0)
      progressCousin.setStringPainted(true)
      progressSexy.setValue(0)
      progressSexy.setStringPainted(true)

      val twinTop = JPanel()
      twinTop.add(twinLabel)
      twinTop.add(twinField)

      val cousinTop = JPanel()
      cousinTop.add(cousinLabel)
      cousinTop.add(cousinField)

      val sexyTop = JPanel()
      sexyTop.add(sexyLabel)
      sexyTop.add(sexyField)

      val twinPanel = JPanel(BorderLayout())
      twinPanel.add(BorderLayout.NORTH, twinTop)
      twinPanel.add(BorderLayout.CENTER, twinScroll)
      val twinBottom = JPanel()
      twinBottom.add(twinMsg)
      twinBottom.add(progressTwin)
      twinPanel.add(BorderLayout.SOUTH, twinBottom)

      val cousinPanel = JPanel(BorderLayout())
      cousinPanel.add(BorderLayout.NORTH, cousinTop)
      cousinPanel.add(BorderLayout.CENTER, cousinScroll)
      val cousinBottom = JPanel()
      cousinBottom.add(cousinMsg)
      cousinBottom.add(progressCousin)
      cousinPanel.add(BorderLayout.SOUTH, cousinBottom)

      val sexyPanel = JPanel(BorderLayout())
      sexyPanel.add(BorderLayout.NORTH, sexyTop)
      sexyPanel.add(BorderLayout.CENTER, sexyScroll)
      val sexyBottom = JPanel()
      sexyBottom.add(sexyMsg)
      sexyBottom.add(progressSexy)
      sexyPanel.add(BorderLayout.SOUTH, sexyBottom)

      centerPanel.add(twinPanel)
      centerPanel.add(cousinPanel)
      centerPanel.add(sexyPanel)

      this.add(BorderLayout.NORTH, northPanel)
      this.add(BorderLayout.CENTER, centerPanel)
      this.add(BorderLayout.SOUTH, statusMessage)
    }

    def controlador(ctr: ActionListener): Unit = {
      twinField.addActionListener(ctr)
      twinField.setActionCommand("TWIN")
      cousinField.addActionListener(ctr)
      cousinField.setActionCommand("COUSIN")
      sexyField.addActionListener(ctr)
      sexyField.setActionCommand("SEXY")
      cancelButton.addActionListener(ctr)
      cancelButton.setActionCommand("CANCELAR")
    }

    def nuevoMensaje(str: String, p:Int) = {
      p match {
        case 0 => twinMsg.setText(str)
        case 1 => cousinMsg.setText(str)
        case 2 => sexyMsg.setText(str)
        case 3 => statusMessage.setText(str)
      }
    }

    def limpiarArea(area: Int) = {
      area match {
        case 0 => twinArea.setText("")
        case 1 => cousinArea.setText("")
        case 2 => sexyArea.setText("")
      }
    }

    def nuevoProgreso(n: Int, p: Int) = {
      p match {
        case 0 => progressTwin.setValue(n)
        case 1 => progressCousin.setValue(n)
        case 2 => progressSexy.setValue(n)
      }
    }

    def numero(p: Int): Int = {
      p match {
        case 0 => Integer.parseInt(twinField.getText)
        case 1 => Integer.parseInt(cousinField.getText)
        case 2 => Integer.parseInt(sexyField.getText)
      }
    }

    def listaPrimos(lista: java.util.List[Primo],p:Int) = {
      p match {
        case 0 => {
          for (i <- 0 until lista.size())
            twinArea.append(s"${lista.get(i)} ")
            if ((i + 1) % 10 == 0) twinArea.append("\n")
        }
        case 1 => {
          for (i <- 0 until lista.size())
            cousinArea.append(s"${lista.get(i)} ")
            if ((i + 1) % 10 == 0) cousinArea.append("\n")
        }
        case 2 => {
          for (i <- 0 until lista.size())
            sexyArea.append(s"${lista.get(i)} ")
            if ((i + 1) % 10 == 0) sexyArea.append("\n")
        }
      }

    }
  }



  object Main {
    def crearGUI(ventana: JFrame): Unit = {
      val panel = new Panel
      val ctr = new Controlador(panel)
      panel.controlador(ctr)
      ventana.setContentPane(panel)
      ventana.pack()
      ventana.setVisible(true)
      ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)
    }

    def main(args: Array[String]): Unit = {
      val ventana = new JFrame("Un Ejemplo")
      crearGUI(ventana)
    }
  }