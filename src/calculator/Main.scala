package calculator

import scala.swing._
import calculator.view.CalculatorPane
import javax.swing.UIManager
import javax.swing.plaf.nimbus.NimbusLookAndFeel

object Main extends SimpleSwingApplication {  
  UIManager.setLookAndFeel(new NimbusLookAndFeel)
  
  def top = new MainFrame {
    title = "McyCalculator"
    resizable = false
    contents  = new CalculatorPane
    centerOnScreen
  }  
}
