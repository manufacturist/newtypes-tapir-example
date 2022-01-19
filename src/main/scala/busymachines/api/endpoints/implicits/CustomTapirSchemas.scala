package busymachines.api.endpoints.implicits

import busymachines.validator.ValidationRule
import cats.data.NonEmptyList
import monix.newtypes._
import sttp.tapir._
import sttp.tapir.integ.cats.TapirCodecCats

trait CustomTapirSchemas extends TapirCodecCats {

  implicit def newtypesNonEmptyListSchema[Base, New: HasBuilder.Aux[*, Base]: TypeInfo](
      implicit schema: Schema[New]
  ): Schema[NonEmptyList[New]] =
    schemaForNel[New].copy(description = schema.description)

  implicit def stringNewtypesSchemaWithValidator[
      New <: String: HasBuilder.Aux[*, String]: TypeInfo
  ](implicit schema: Schema[String], rule: ValidationRule[New]): Schema[New] =
    newtypesSchema[String, New].validate(ValidationRuleToValidator.applyForString(rule))

  implicit def intNewtypesSchemaWithValidator[New <: Int: HasBuilder.Aux[*, Int]: TypeInfo](
      implicit schema: Schema[Int],
      rule: ValidationRule[New]
  ): Schema[New] =
    newtypesSchema[Int, New].validate(ValidationRuleToValidator.applyForInt(rule))

  implicit def anyNewtypesSchemaWithValidator[Base, New: HasBuilder.Aux[*, Base]: TypeInfo](
      implicit schema: Schema[Base],
      rule: ValidationRule[New] = ValidationRule.pass[New]
  ): Schema[New] =
    newtypesSchema[Base, New].validate(Validator.pass)

  private def newtypesSchema[Base, New: HasBuilder.Aux[*, Base]: TypeInfo](
      implicit schema: Schema[Base]
  ): Schema[New] = {

    val name = implicitly[TypeInfo[New]].typeLabel

    schema
      .copy(
        format = schema.format match {
          case None => Some(name)
          case Some(original) => Some(s"$original($name)")
        },
        description = Some(
          s"""<a href="https://github.com/busymachines/newtypes-tapir-example/blob/main/TYPES.md#$name" target="_blank">
             |<i>$name docs</i>
             |</a>""".stripMargin
        )
      )
      .asInstanceOf[Schema[New]]
  }

}
