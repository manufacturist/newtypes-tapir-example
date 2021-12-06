package busymachines.api.endpoints.implicits

import busymachines.validator.ValidationRule
import sttp.tapir._

object ValidationRuleToValidator {

  def applyForAll[T](validationRule: ValidationRule[T]): Validator[T] =
    validationRule match {
      case _ => Validator.pass
    }

  def applyForString[T <: String](validationRule: ValidationRule[T]): Validator[T] =
    validationRule match {
      case ValidationRule.RegexMatch(regex) =>
        Validator.Pattern[T](regex.regex)
      case ValidationRule.MaxLength(value) => Validator.MaxLength[T](value)
      case rule =>
        applyForAll(rule)
    }

  def applyForInt[T <: Int](validationRule: ValidationRule[T]): Validator[T] =
    validationRule match {
      case ValidationRule.MinMax(min, max) =>
        val minValidator = min.fold(Validator.pass[Int])(Validator.Min(_, exclusive = false))
        val maxValidator = max.fold(Validator.pass[Int])(Validator.Max(_, exclusive = false))
        minValidator.and(maxValidator).asInstanceOf[Validator[T]]

      case rule => applyForAll(rule)
    }
}
