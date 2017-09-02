package com.nitrograph.dotty.language.feature
package implicits

import com.nitrograph.dotty.language.feature._
import scala.util.{Try, Success}

case class ImplicitParameters(
    examples: Map[String, () => Unit],
    tests: Map[String, () => Try[Nothing]]
) extends DottyFeature(
    name = "Implicit parameters",
    List(
        "Basic" -> ImplicitParameters.Example.Basic
    ).toMap,
    tests
)

object ImplicitParameters {
    object Example {
        val Basic = () => {

            trait Comparable {
                def =~=[T <: Comparable](that: Comparable): Try[Boolean]
            }

            trait Parsable[AParam <: Comparable] { self =>
                def =~=(that: Parsable[AParam]): Try[Boolean] = {
                    that.test(self.unapply:_*)
                }
                def test(params: AParam*): Try[Boolean] = {
                    (
                        params.toSeq.zip(self.unapply).map {
                            case (parameterA, parameterB) =>
                                parameterA =~= parameterB
                        }
                    ).foldLeft[Try[Boolean]](Success(true)) {
                        case (testResultTry, partialTestResultTry) =>
                            testResultTry.flatMap[Boolean] {
                                case true =>
                                    partialTestResultTry
                                case false =>
                                    Success(false)
                            }
                    }
                }
                def vary(params: AParam*): Parsable[AParam]
                def apply(params: AParam*): Parsable[AParam]
                def unapply: Seq[AParam]
            }

            trait Parser[AParsed, AParam <: Comparable, AParsable <: Parsable[AParam]] {
                def parse(maybeParsable: AParsable): Try[AParsed]
            }
        }
    }
}