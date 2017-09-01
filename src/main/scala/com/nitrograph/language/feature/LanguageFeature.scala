package com.nitrograph.language
package feature

import com.nitrograph.language._
import scala.collection.immutable.HashMap
import scala.util.Try

trait LanguageFeature(
    language: Language,
    name: String,
    examples: HashMap[String, Unit => Unit],
    tests: HashMap[String, Unit => Try[Boolean]]
)
