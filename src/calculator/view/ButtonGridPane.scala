package calculator.view

import swing._
import java.awt.Font
import scala.swing.Font

class ButtonGridPane extends BorderPanel {
  class ButtonGridPane extends GridBagPanel {
    val c = new Constraints
    c.ipadx = 3
    c.ipady = 5
    c.fill = GridBagPanel.Fill.Horizontal

    def createButton(str: String, x: Int, y: Int, w: Int = 1) = {
      val button = new Button {
        text = str
        preferredSize = new Dimension(55, 30)
        font = new Font("Tahoma", Font.PLAIN, 12)
      }

      c.gridx = x
      c.gridy = y
      c.gridwidth = w
      layout(button) = c
      button
    }
  }
  
  val northPane = new ButtonGridPane {
    val sinButton = createButton("sin", 0, 0)
    val cosButton = createButton("cos", 1, 0)
    val tanButton = createButton("tan", 2, 0)
    val sinhButton = createButton("sinh", 3, 0)
    val coshButton = createButton("cosh", 4, 0)
    val tanhButton = createButton("tanh", 5, 0)
    val logButton = createButton("log", 0, 1)
    val lnButton = createButton("ln", 1, 1)
    val permButton = createButton("nPr", 2, 1, 2)
    val combButton = createButton("nCr", 4, 1, 2)

    border = Swing.EmptyBorder(0, 0, 9, 0)
  }
  
  val centerPane = new ButtonGridPane {
    val sevenButton = createButton("7", 0, 0)
    val eightButton = createButton("8", 1, 0)
    val nineButton = createButton("9", 2, 0)
    val multiplyButton = createButton("x", 3, 0)
    val divideButton = createButton("\u00f7", 4, 0)
    val rootButton = createButton("\u221A", 5, 0)
    val fourButton = createButton("4", 0, 1)
    val fiveButton = createButton("5", 1, 1)
    val sixButton = createButton("6", 2, 1)
    val addButton = createButton("+", 3, 1)
    val subButton = createButton("-", 4, 1)
    val factButton = createButton("n!", 5, 1)
    val oneButton = createButton("1", 0, 2)
    val twoButton = createButton("2", 1, 2)
    val threeButton = createButton("3", 2, 2)
    val expButton = createButton("x\u207f", 3, 2)
    val paren1Button = createButton("(", 4, 2)
    val paren2Button = createButton(")", 5, 2)
    val zeroButton = createButton("0", 0, 3)
    val decButton = createButton(".", 1, 3)
    val equalButton = createButton("=", 2, 3)
    val msButton = createButton("MS", 3, 3)
    val mrButton = createButton("MR", 4, 3)
    val mcButton = createButton("MC", 5, 3)
    val ansButton = createButton("Answer", 0, 4, 2)
    val bsButton = createButton("Backspace", 2, 4, 2)
    val clButton = createButton("Clear", 4, 4, 2)

    border = Swing.EmptyBorder(9, 0, 0, 0)
  }
  
  border = Swing.EmptyBorder(10, 0, 0, 0)
  layout(northPane) = BorderPanel.Position.North
  layout(centerPane) = BorderPanel.Position.Center
}
