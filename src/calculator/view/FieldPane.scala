package calculator.view

import swing._
import scala.swing.event.ButtonClicked
import java.awt.Color
import java.awt.Font
import calculator.model._

class FieldPane(calc: CalculatorPane) extends BorderPanel {
  import calc.buttonsPane.northPane._
  import calc.buttonsPane.centerPane._
    
  val inputField = new CalcField {    
    listenTo(
      sinButton, cosButton, tanButton, sinhButton, coshButton,
      tanhButton, logButton, lnButton, permButton, combButton,
      sevenButton, eightButton, nineButton, multiplyButton, divideButton,
      rootButton, fourButton, fiveButton, sixButton, addButton, 
      subButton, factButton, oneButton, twoButton, threeButton,
      expButton, paren1Button, paren2Button, zeroButton, decButton, 
      mrButton, ansButton, bsButton, clButton)
    reactions += {
      case ButtonClicked(`permButton`) => text += "P"
      case ButtonClicked(`combButton`) => text += "C"  
      case ButtonClicked(button) if List(
        sinButton, cosButton, tanButton, sinhButton, coshButton, tanhButton, logButton, lnButton)
        .contains(button) => text += button.text + " "
      case ButtonClicked(`ansButton`) => text += Math.formatNum(calc.answer)
      case ButtonClicked(`mrButton`) => calc.memory match {
        case Some(memory) => text += Math.formatNum(memory)
        case None =>
      }
      case ButtonClicked(`clButton`) => text = ""      
      case ButtonClicked(`bsButton`) => text = if (text.isEmpty) "" else text.init
      case ButtonClicked(button) => text += button.text
    }
  }
  
  val resultField = new CalcField {    
    listenTo(equalButton, clButton)
    reactions += {
      case ButtonClicked(`equalButton`) => InfixToPostfix(inputField.text) match {
        case (true, postfix, _) => Evaluator(postfix) match {
          case (_, Some(msg)) => text = msg
          case (Some(ans), _) => 
            text = Math.formatNum(ans)  
            calc.answer = ans
        }
        case (false, _, Some(msg)) => text = msg
      } 
      case ButtonClicked(`clButton`) => text = ""
    }
  }

  val innerPane = new BoxPanel(Orientation.Vertical) {
    contents += inputField
    contents += resultField
    background = Color.WHITE
  }

  layout(innerPane) = BorderPanel.Position.Center
}

class CalcField extends TextField {  
  horizontalAlignment = Alignment.Right
  font = new Font(font.getFamily, Font.PLAIN, 15)
  border = Swing.EmptyBorder(7, 7, 7, 7)
  editable = false
}
