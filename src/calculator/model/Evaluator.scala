package calculator.model

class Evaluator private(postfix: List[String]) {
  def evaluate = {
    def inner(stack: List[Double], postfix: List[String]): (Option[Double], Option[String]) = 
      postfix match {
        case Nil => (Some(stack.head), None)
        case ph :: pt if ph.exists(_.isDigit) => inner(ph.toDouble :: stack, pt) 
        case op :: pt if operatorType(op) == 2 =>
          if (Math.isSpecialBinOp(op) && (stack.head < 0 || stack.tail.head < 0))
            (None, Some("Invalid Permutation or Combination"))
          else inner(compBin(stack.tail.head, stack.head, op) :: stack.tail.tail, pt)
        case op :: pt if operatorType(op) == 1 => 
          if (op == "!" && stack.head < 0) 
            (None, Some("Factorial of a non-natural number: " + stack.head))  
          else inner(compUn(stack.head, op) :: stack.tail, pt)
      }    
  
    try {
      inner(Nil, postfix)
    } catch {
      case ex: Exception => (None, Some(ex.getMessage()))
    }
  }
  
  def compBin(a: Double, b: Double, op: String) = op match {
    case "^" => math.pow(a, b)
    case "*" => a * b
    case "/" => a / b
    case "+" => a + b
    case "-" => a - b
    case Math.perm => Math.permutation(a.toInt, b.toInt)
    case Math.comb => Math.combination(a.toInt, b.toInt)
  }
  
  def compUn(n: Double, op: String) = op match {
    case "!" => Math.factorial(n.toInt) 
    case Math.sqrt => math.sqrt(n)
    case Math.log => math.log10(n)
    case Math.ln => math.log(n)
    case Math.sin => math.sin(math.toRadians(n))
    case Math.cos => math.cos(math.toRadians(n))
    case Math.tan => math.tan(math.toRadians(n))
    case Math.sinh => math.sinh(n)
    case Math.cosh => math.cosh(n)
    case Math.tanh => math.tanh(n)
  }    
    
  def operatorType(op: String) =
    if (Math.binOps.contains(op)) 2 
    else if (Math.unOps.contains(op)) 1 
    else 0
}

object Evaluator {
  def apply(postfix: List[String]) = new Evaluator(postfix).evaluate
}