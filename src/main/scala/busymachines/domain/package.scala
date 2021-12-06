package busymachines

import busymachines.validator._
import sprout.SproutSub

import java.util.UUID

package object domain {

  object MovieId extends SproutSub[UUID]
  type MovieId = MovieId.Type

  object ActorId extends SproutSub[UUID]
  type ActorId = ActorId.Type

  type ActorCode = ActorCode.Type
  object ActorCode extends SproutSub[String] {
    implicit val vr: ValidationRule[ActorCode] =
      ValidationRule.RegexMatch("[A-Z]{3}\\d{3}".r)
  }

  type FullName = FullName.Type
  object FullName extends SproutSub[String] {
    implicit val vr: ValidationRule[FullName] =
      ValidationRule.RegexMatch("\\w+[ ]\\w.*".r)
  }

  type Title = Title.Type
  object Title extends SproutSub[String] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MaxLength(250)
  }

  type Age = Age.Type
  object Age extends SproutSub[Int] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MinMax(min = Some(0), max = Some(120))
  }

  type Year = Year.Type
  object Year extends SproutSub[Int] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MinMax(min = Some(1788), max = None)
  }
}
