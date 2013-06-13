package calculator.model

class InfixToPostfix private(expression: String) {
  def toPostfix = {        
    def normalized(norm: String, exp: String): String = 
      if (exp.isEmpty) norm
      else if (norm.isEmpty) normalized(norm + exp.head, exp.tail)
      else (norm.last, exp.head) match {
        case (nl, eh) if nl.isDigit && Math.isPreUnOp(eh) => normalized(s"$norm*$eh", exp.tail)
        case ('-', eh) if Math.isPreUnOp(eh) => normalized(s"${norm}1*$eh", exp.tail)
        case ('+', '-') | ('-', '+') => normalized(norm.init + "-", exp.tail)
        case (nl, eh) => normalized(norm + eh, exp.tail)
      }
    
    def tokenized(tokens: List[String], exp: String): List[String] =
      if (exp.isEmpty) tokens
      else if (tokens.isEmpty) tokenized(exp.head.toString :: tokens, exp.tail)
      else (tokens.head, exp.head) match {
        case (th, eh) if (eh.isDigit || eh == '.') && (th.last.isDigit || th.last == '.') => 
          tokenized((th + eh) :: tokens.tail, exp.tail)
        case (th, eh) if Math.isOperator(th.head) && (eh == '+' || eh == '-') =>
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
            if (Math.precedence(eh.head) > Math.precedence(oh))
              convert(et, postfix, eh.head :: ops)
            else convert(exp, oh.toString :: postfix, ot)
        }
      }
   
    val newExp = expression.replace("log ", Math.log).replace("ln ", Math.ln)
      .replace("sin ", Math.sin).replace("cos ", Math.cos)
      .replace("tan ", Math.tan).replace("sinh ", Math.sinh)
      .replace("cosh ", Math.cosh).replace("tanh ", Math.tanh)
      .replace("P", Math.perm).replace("C", Math.comb)
      .replace("x", "*").replace("E", "*10^")
    val tokens = tokenized(Nil, normalized("", "0+" + newExp)).reverse
    val (valid, msg) = Validator(tokens)
    (valid, if (valid) convert(tokens, Nil, Nil) else tokens, msg)
  }    
}

object InfixToPostfix {
  def apply(expression: String) = (new InfixToPostfix(expression)).toPostfix
}
