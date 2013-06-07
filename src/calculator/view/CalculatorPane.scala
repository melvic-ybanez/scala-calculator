package calculator.view

import swing._
import java.awt.{Dimension, Font, Color}
import scala.swing.event.ButtonClicked

class CalculatorPane extends BorderPanel {     
  val buttonsPane = new ButtonGridPane
  val fieldPane = new FieldPane(this)
  var answer: Double = 0
  var memory: Option[Double] = None
  
  layout(fieldPane) = BorderPanel.Position.North
  layout(buttonsPane) = BorderPanel.Position.Center
  border = Swing.EmptyBorder(10, 10, 10, 10)
  
  import buttonsPane.centerPane.{msButton, mcButton}
  listenTo(msButton, mcButton)
  reactions += {
    case ButtonClicked(`msButton`) => memory = Some(answer)
    case ButtonClicked(`mcButton`) => memory = None
  }
}

object CalculatorPane {
  def createFrame = new MainFrame {
    title = "McyCalculator"
    resizable = false
    contents  = new CalculatorPane
    centerOnScreen
  }
}
