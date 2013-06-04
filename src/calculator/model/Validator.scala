package calculator.model

class Validator private(tokens: List[String]) {        
  def validate = {
    def inner(stack: List[String], tokens: List[String]): (Boolean, Option[String]) = 
      tokens match {
        case Nil => (true, None)
        case th :: tt if Math.isOperator(th) && !Math.isPreUnOp(th) => 
          stack match {
            case sh :: _ if Math.isOperator(sh.last) && !th.matches("[+-]") && sh != "!" =>
              (false, Some(s"Invalid sequence: $sh$th"))
            case sh :: _ if (th == "!" || Math.isSpecialBinOp(th)) && sh.contains(".") => 
              (false, Some(s"Factorial of a non-natural number: $sh"))
            case _ => inner(th :: stack, tt)
          } 
        case th :: _ if th.contains(".") && th.exists(_.isDigit) && 
            Math.isSpecialBinOp(stack.head) => 
          (false, Some("Factorial of a non-natural number: " + th))
        case "." :: _ => (false, Some("Invalid token: ."))
        case th :: _ if th.count(_ == '.') > 1 => (false, Some(s"Too many decimal points in $th"))  
        case th :: tt => inner(th :: stack, tt)
      }
    
    def validParens = {
      def inner(parens: List[Char], tokens: List[String]): Boolean = tokens match {
        case Nil => parens.isEmpty
        case "(" :: tt => inner('(' :: parens, tt)
        case ")" :: tt => parens match {
          case Nil => false
          case _ :: pt => inner(pt, tt)
        }
        case _ :: tt => inner(parens, tt)
      }      
 
      inner(Nil, tokens)
    }
    
    if (validParens) inner(Nil, tokens) else (false, Some("Parentheses not balanced"))
  }
}

object Validator {
  def apply(tokens: List[String]) = new Validator(tokens).validate
}