package calculator.model

object Math {  
  def isOperator(op: Any) = {
    def operator(c: Char) = (binOps + unOps).contains(c)
    op match {
      case c: Char => operator(c)
      case s: String => operator(s.head) && !s.exists(_.isDigit)
      case _ => false
    }
  }      
  
  def isPreUnOp(op: Any) = op match {
    case c: Char => preUnOps.contains(c)
    case s: String => preUnOps.contains(s)
    case _ => false
  }
    
  def precedence(operator: Char) = operator match {      
    case '^' | '!' | 'P' | 'C' =>  5
    case op if specialUnOps.contains(op) => 5
    case op if specialBinOps.contains(op) => 5
    case '*' | '/' => 4
    case '+' | '-' => 3
    case '(' | ')' => 2
  }
  
  def factorial(n: Int): Int = if (n < 1) 1 else n * factorial(n - 1)  
  def permutation(n: Int, r: Int) = factorial(n) / factorial(n - r)  
  def combination(n: Int, r: Int) = factorial(n) / (factorial(r) * factorial(n - r))  
  def isWholeNumber(n: Double) = n - n.toInt == 0  
  def formatNum(n: Double) = if (isWholeNumber(n)) n.toInt.toString else n.toString  
  def binOps = "^*/+-" + specialBinOps
  def unOps = preUnOps + "!"  
  def preUnOps = specialUnOps + "("  
  def isSpecialUnOp(s: String) = specialUnOps.contains(s)  
  def isSpecialBinOp(s: String) = specialBinOps.contains(s)  
  private def specialUnOps = s"$sqrt$log$ln$sin$cos$tan$sinh$cosh$tanh"
  private def specialBinOps = s"$perm$comb"
  
  val sqrt = "\u221A"
  val log = "l"
  val ln = "n"
  val sin = "s"
  val cos = "c"
  val tan = "t"
  val sinh = "a"
  val cosh = "y"
  val tanh = "z"
  val perm = "P"
  val comb = "C"
}
