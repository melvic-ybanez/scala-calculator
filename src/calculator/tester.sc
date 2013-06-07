package calculator

import model._

object tester {
  Math.isWholeNumber(0.1)                         //> res0: Boolean = false
  val g = List(
      "5*4/3^2-1",
      "536*(72*(1+2)-3)/5",
      "(7+5)*3",
      "7(1+3)",
      "7^-21",
      "7+(-3+6)",
      "8+9.74",
      "9.9-(3+12.345)",
      ".9+0.89",
      "-.7+-9.5*(3-9)",
      "4-4!",
      "-4!",
      "8..9.*7",
      "9-.",
      "(-4)!",
      "8.9!",
      "7+\u221A4-3",
      "\u221A-1",
      "7.+9",
      "2log 3",
      "2-log 3",
      "2*-log 3",
      "2log 3-sin 5",
      "sin 1+cos 2-tan 3+ln 5",
      "7P5.3",
      "9C3",
      "5!P5")
  g.foreach { x => InfixToPostfix(x) match {
    case (true, postfix, _) => println(s"$x ==> $postfix ==> " +
      (Evaluator(postfix) match {
        case (_, Some(msg)) => msg
        case (Some(ans), _) => ans
      }))
    case (false, postfix, Some(msg)) => println(s"$x ==> $postfix ==> $msg")
  }}
  test
}
