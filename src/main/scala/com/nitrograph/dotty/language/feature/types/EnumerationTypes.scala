package com.nitrograph.dotty.language.feature
package types

import scala.collection.immutable.HashMap
import scala.util.Try

case class EnumerationTypes(
    examples: Map[String, () => Unit],
    tests: Map[String, () => Try[Nothing]]
) extends DottyFeature(
    name = "Enumeration types",
    List(
        "Basic" -> EnumerationTypes.Example.Basic,
        "Advanced" -> EnumerationTypes.Example.Advanced
    ).toMap,
    tests
)

object EnumerationTypes {
    object Example {
        val Basic = () => {
            enum class BookState(description: String) { state =>
                override def toString: String = state.description
            }

            object BookState {
                case Published extends BookState("Published")
                case InProgress extends BookState("InProgress")
                case Planned extends BookState("Planned")
            }

            println("states of book")
            println(s"${BookState.Planned} ${BookState.InProgress} ${BookState.Published}")
        }

        val Advanced = () => {
            enum ENumber {
                case TheNatural(value: Int) extends ENumber {
                    require(value > 0)
                }
                case TheRational(value: Double) extends ENumber
            }

            object Natural {
                def TheNumber(value: Int): ENumber = ENumber.TheNatural(value)
            }

            object Rational {
                def TheNumber(value: Double): ENumber = ENumber.TheRational(value)
            }

            println(s"various natural numbers: {${Natural.TheNumber(1)}, ${Natural.TheNumber(2)}, ${Natural.TheNumber(3)}}")
            println(s"various rational numbers: {${Rational.TheNumber(1.0)}, ${Rational.TheNumber(3.1415)}, ${Rational.TheNumber(2.7)}}")
        }
    }
}
