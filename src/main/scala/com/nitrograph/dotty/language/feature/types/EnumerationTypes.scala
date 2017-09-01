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
        "Basic" -> EnumerationTypes.Example.Basic
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
    }
}
