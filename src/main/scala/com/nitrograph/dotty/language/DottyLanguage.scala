package com.nitrograph.dotty
package language

import com.nitrograph.language._

final case class DottyLanguage(
    val version: String
) extends Language("Dotty", version)

object DottyLanguage {
    val currentReleaseCandidate = DottyLanguage("0.3.0-RC1")
}