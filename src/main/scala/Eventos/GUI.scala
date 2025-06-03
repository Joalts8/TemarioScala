package Eventos

import java.awt.*
import java.awt.event.ActionListener
import javax.swing.*
import javax.swing.JFrame

/*
Patrón Modelo-Vista-Controlador (MVC)
  Modelo-> Representa los datos y lógica de negocio, es independiente de la GUI
  Vista-> Muestra información gráfica: botones, texto... Un modelo puede tener varias. Registra el controlador
        para reaccionar a eventos.
  Controlador-> Responde a las acciones del usuario. Implementa interfaces y registra eventos con métodos


1. Generar la vista heredando de un contenedor intermedio-> JPanel
  1. Seleccionar un gestor de esquemas(en awt) para dicho contenedor-> cambia con setLayout(AbstractLayout)
  2. Crear los componentes visuales-> val tipo Jtal; tambien puede ser otro Jpanel con su layout y componentes.
  3. Agregarlos al contenedor intermedio-> add(Component)

2. Crear un objeto de una clase contenedora superior-> JFrame -> Aplicaciones-> ventana con borde y título.
– setTitle(String), getTitle(), setIconImage(Image).
JApplet -> Aplicaciones que corren en navegador
JDialog -> Tienen un contenedor superior del que dependen
  1. Hacer el contenedor intermedio su panel de contenidoss-> setContentPane(Container)
  2. Dimensionar el contenedor superior.
  3. Hacer visible(o invisible) el contenedor superior-> ventana.setVisible(boolean)
*/

//1
class Panel extends JPanel {
  private val bSi= new JButton("SÍ");//2. Creaccion de componentes
  private val bNo= new JButton("NO");
  private val l = new JLabel("Es verdad?");
  setLayout(new FlowLayout())//1. Selecciona el gestor, pero viene de base con ese al ser JPanel
  add(l);add(bSi);add(bNo) //3. Se agregan al panel. El orden es importante
  //Panale complejo
  val p = new JPanel()
  val bp1 = new JButton("HOLA")
  val bp2 = new JButton("Mundo")
  p.setLayout(new GridLayout(2, 1))
  p.add(bp1); p.add(bp2); add(p)
  //Controlador
  def controlador(ctr: ActionListener): Unit = {
    bSi.addActionListener(ctr)
    bSi.setActionCommand("SI")
    bNo.addActionListener(ctr)
    bNo.setActionCommand("NO")
  }
  def cambiaTexto(str: String) =
    l.setText(str)
}

object EjemploSimple {
  def main(args:Array[String]): Unit = {
    val panel = new Panel();
    //Controlador
    val bt = new ActionListener(panel);
    panel.controlador(bt);
    //2
    val ventana = new JFrame("Un Ejemplo")
    ventana.setContentPane(panel)//1. Panel anterior=Panel de contenidos
    //ventana.setSize(ancho:Int, alto:Int) diemensionar ventana, no se usa-> pack()
    //setPreferredSize(Dimension); setMinimunSize(Dimension); setMaximumSize(Dimension)
    ventana.pack()//2. Dimensionar según-> Gestor de esquemas, nº y orden de los componentes y su dim preferida
    ventana.setVisible(true)//3. hacer visible/invisible
    //ventana.setJMenuBar(Menu) // Coloca un menú
    ventana.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE)// Para cerrar la ventana
  }
}
/*
* GESTORES
* FlowLayout->componentes de izquierda a derecha y de arriba abajo. Tamaño se ajusta al texto. Al cambiar el tamaño de
* la ventana, puede cambiar la disposición
* BorderLayout-> Contenedor en 5 partes. Los componentes se ajustan para rellenar cada parte. Si algún componente
* falta, se ajusta con el resto. Se usa-> add(bSí, BorderLayout.NORTH) Partes: SOUTH, EAST, WEST y CENTER
* GridLayout-> Divide al componente en una rejilla. Se indica el número de filas y columnas, los componentes mantienen.
* Dde izquierda a derecha y de arriba a abajo.   .setLayout(new GridLayout(fila,columnas))
* */

/*
* EVENTOS
*Un componente/menu puede disparar un evento-> recogido por CONTROLADOR
*
*
* */