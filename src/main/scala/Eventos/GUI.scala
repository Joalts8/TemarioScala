package Eventos

import java.awt.*
import javax.swing.*

/*
Patrón Modelo-Vista-Controlador (MVC)
  Modelo-> Representa los datos y lógica de negocio, es independiente de la GUI
  Vista-> Muestra información gráfica: botones, texto... Un modelo puede tener varias. Registra el controlador
        para reaccionar a eventos.
  Controlador-> Responde a las acciones del usuario. Implementa interfaces y registra eventos con métodos


1. Generar la vista heredando de un contenedor intermedio-> JPanel
  1. Seleccionar un gestor de esquemas(en awt) para dicho contenedor-> cambia con setLayout(AbstractLayout)
  2. Crear los componentes visuales-> val tipo Jtal
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
class PanelVentana extends JPanel {
  private val bSi= new JButton("SÍ");//2 Creaccion de componentes
  private val bNo= new JButton("NO");
  private val l = new JLabel("Es verdad?");
  setLayout(new FlowLayout())//1. Selecciona el gestor, pero viene de base con ese al ser JPanel
  add(l);add(bSi);add(bNo) //3 Se agregan al panel. El orden es importante
}

object EjemploSimple {
  def main(args:Array[String]): Unit = {
    //2
    val ventana = new JFrame("Un Ejemplo")
    ventana.setContentPane(new Panel)//1 Panel anterior=Panel de contenidos
    ventana.pack()
    ventana.setVisible(true)//3 hacer visible/invisible
    ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE)
  }
}
