package busymachines.api.endpoints.implicits

import busymachines.validator.ValidationRule
import sttp.tapir._

object ValidationRuleToValidator {

  def applyForString[T <: String](validationRule: ValidationRule[T]): Validator[T] =
    validationRule match {
      case ValidationRule.RegexMatch(regex) =>
        new Validator.Pattern[T](regex.regex) {
          override def apply(t: T): List[ValidationError[_]] = {
            println(s"$t validated in tapir")

            if (t.matches(value)) {
              List.empty
            } else {
              List(ValidationError.Primitive(this, t))
            }
          }
        }
      case ValidationRule.MaxLength(value) => Validator.MaxLength[T](value)

      case _ => Validator.pass
    }

  def applyForInt[T <: Int](validationRule: ValidationRule[T]): Validator[T] =
    validationRule match {
      case ValidationRule.MinMax(min, max) =>
        val minValidator = min.fold(Validator.pass[Int])(Validator.Min(_, exclusive = false))
        val maxValidator = max.fold(Validator.pass[Int])(Validator.Max(_, exclusive = false))
        minValidator.and(maxValidator).asInstanceOf[Validator[T]]

      case _ => Validator.pass
    }
}
