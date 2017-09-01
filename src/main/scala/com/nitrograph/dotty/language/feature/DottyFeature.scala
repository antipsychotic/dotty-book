package com.nitrograph.dotty.language
package feature

import com.nitrograph.dotty.language.DottyLanguage
import com.nitrograph.language.feature._
import scala.collection.immutable.HashMap
import scala.util.Try

class DottyFeature(
    name: String,
    examples: Map[String, () => Unit],
    tests: Map[String, () => Try[Nothing]]
) extends LanguageFeature(
    DottyLanguage.currentReleaseCandidate,
    name,
    examples,
    tests
)
