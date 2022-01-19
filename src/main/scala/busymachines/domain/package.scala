package busymachines

import busymachines.validator._
import monix.newtypes.{BuildFailure, NewsubtypeValidated, NewsubtypeWrapped}

import java.util.UUID

package object domain {

  object MovieId extends NewsubtypeWrapped[UUID]
  type MovieId = MovieId.Type

  object ActorId extends NewsubtypeWrapped[UUID]
  type ActorId = ActorId.Type

  type ActorCode = ActorCode.Type
  object ActorCode extends NewsubtypeValidated[String] {

    private val actorCodeRegex = "[A-Z]{3}\\d{3}".r

    def apply(value: String): Either[BuildFailure[Type], Type] = {
      println(s"$value validated in newtypes")

      if (actorCodeRegex.matches(value)) Right(unsafeCoerce(value))
      else Left(BuildFailure[Type](s"$value doesn't match $actorCodeRegex pattern"))
    }

    implicit val vr: ValidationRule[Type] =
      ValidationRule.RegexMatch(actorCodeRegex)
  }

  type FullName = FullName.Type
  object FullName extends NewsubtypeWrapped[String] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.RegexMatch("\\w+[ ]\\w.*".r)
  }

  type Title = Title.Type
  object Title extends NewsubtypeWrapped[String] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MaxLength(250)
  }

  type Age = Age.Type
  object Age extends NewsubtypeWrapped[Int] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MinMax(min = Some(0), max = Some(120))
  }

  type Year = Year.Type
  object Year extends NewsubtypeWrapped[Int] {
    implicit val vr: ValidationRule[Type] =
      ValidationRule.MinMax(min = Some(1788), max = None)
  }
}
