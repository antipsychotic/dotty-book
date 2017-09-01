package com.nitrograph.language
package feature

import com.nitrograph.language._
import scala.collection.immutable.HashMap
import scala.util.Try

trait LanguageFeature(
    language: Language,
    name: String,
    examples: Map[String, () => Unit],
    tests: Map[String, () => Try[Nothing]]
)
