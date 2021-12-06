package busymachines.api.endpoints.implicits

import busymachines.validator.ValidationRule
import cats.data.NonEmptyList
import sprout.NewType
import sttp.tapir._
import sttp.tapir.integ.cats.TapirCodecCats

trait CustomTapirSchemas extends TapirCodecCats {

  implicit def sproutNonEmptyListSchema[Old, New: NewType[Old, *]](
      implicit schema: Schema[New],
      rule: ValidationRule[New] = ValidationRule.pass[New]): Schema[NonEmptyList[New]] =
    schemaForNel[New].copy(description = schema.description)

  implicit def stringSproutSchemaWithValidator[New <: String: NewType[String, *]](
      implicit schema: Schema[String],
      rule: ValidationRule[New]): Schema[New] =
    sproutSchema[String, New].validate(ValidationRuleToValidator.applyForString(rule))

  implicit def intSproutSchemaWithValidator[New <: Int: NewType[Int, *]](
      implicit schema: Schema[Int],
      rule: ValidationRule[New] = null): Schema[New] =
    sproutSchema[Int, New].validate(ValidationRuleToValidator.applyForInt(rule))

  implicit def anySproutSchemaWithValidator[Old, New: NewType[Old, *]](
      implicit schema: Schema[Old],
      rule: ValidationRule[New] = ValidationRule.pass[New]): Schema[New] =
    sproutSchema[Old, New].validate(ValidationRuleToValidator.applyForAll(rule))

  private def sproutSchema[Old, New: NewType[Old, *]](
      implicit schema: Schema[Old]): Schema[New] = {

    val name = NewType[Old, New].symbolicName

    schema
      .copy(
        format = schema.format match {
          case None => Some(name)
          case Some(original) => Some(s"$original($name)")
        },
        description = Some(
          s"""<a href="https://github.com/busymachines/newtypes-tapir/types.md#$name" target="_blank">
             |<i>$name docs</i>
             |</a>""".stripMargin
        )
      )
      .asInstanceOf[Schema[New]]
  }

}
