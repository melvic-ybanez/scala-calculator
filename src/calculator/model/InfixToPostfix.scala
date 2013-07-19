package calculator.model

import Math._

class InfixToPostfix private(expression: String) {
  def toPostfix = {        
    def normalized(norm: String, exp: String): String = 
      if (exp.isEmpty) norm
      else if (norm.isEmpty) normalized(norm + exp.head, exp.tail)
      else (norm.last, exp.head) match {
        case (nl, eh) if nl.isDigit && isPreUnOp(eh) => normalized(s"$norm*$eh", exp.tail)
        case ('-', eh) if isPreUnOp(eh) => normalized(s"${norm}1*$eh", exp.tail)
        case ('+', '-') | ('-', '+') => normalized(norm.init + "-", exp.tail)
        case (nl, eh) => normalized(norm + eh, exp.tail)
      }
    
    def tokenized(tokens: List[String], exp: String): List[String] =
      if (exp.isEmpty) tokens
      else if (tokens.isEmpty) tokenized(exp.head.toString :: tokens, exp.tail)
      else (tokens.head, exp.head) match {
        case (th, eh) if (eh.isDigit || eh == '.') && (th.last.isDigit || th.last == '.') => 
          tokenized((th + eh) :: tokens.tail, exp.tail)
        case (th, eh) if isOperator(th.head) && (eh == '+' || eh == '-') =>
          tokenized((eh + "0") :: tokens, exp.tail)
        case (th, eh) => tokenized(eh.toString :: tokens, exp.tail)
      }    
    
    def convert(exp: List[String], postfix: List[String], ops: List[Char]): List[String] = 
      exp match {
        case Nil => postfix.reverse ++ ops.map(_.toString)
        case eh :: et if eh.exists(_.isDigit) => convert(et, eh :: postfix, ops)
        case "(" :: et => convert(et, postfix, '(' :: ops)
        case ")" :: et if (ops.head == '(') => convert(et, postfix, ops.tail)
        case eh :: et => ops match {
          case Nil => convert(et, postfix, List(eh.head))
          case oh :: ot =>
            if (precedence(eh.head) > Math.precedence(oh))
              convert(et, postfix, eh.head :: ops)
            else convert(exp, oh.toString :: postfix, ot)
        }
      }
   
    val newExp = expression.replace("log ", log).replace("ln ", ln)
      .replace("sin ", sin).replace("cos ", cos).replace("tan ", tan)
      .replace("sinh ", sinh).replace("cosh ", cosh).replace("tanh ", tanh)
      .replace("P", perm).replace("C", comb).replace("x", "*")
      .replace("\u00f7", "/").replace("E", "*10^")
    val tokens = tokenized(Nil, normalized("", "0+" + newExp)).reverse
    val (valid, msg) = Validator(tokens)
    (valid, if (valid) convert(tokens, Nil, Nil) else tokens, msg)
  }    
}

object InfixToPostfix {
  def apply(expression: String) = (new InfixToPostfix(expression)).toPostfix
}
