package busymachines.validator

import monix.newtypes.NewsubtypeWrapped

import scala.util.matching.Regex

sealed trait ValidationRule[T]

object ValidationRule {

  case class RegexMatch[T <: String](regex: Regex) extends ValidationRule[T]

  case class MaxLength[T <: String](max: Int) extends ValidationRule[T]

  case class MinMax[T <: Int](min: Option[Int], max: Option[Int]) extends ValidationRule[T]

  trait Pass[T] extends ValidationRule[T]
  def pass[T]: Pass[T] = new Pass[T] {}
}
