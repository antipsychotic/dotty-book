package com.nitrograph.dotty.language
package feature

import com.nitrograph.dotty.language.DottyLanguage
import com.nitrograph.language.feature._
import scala.collection.immutable.HashMap

sealed abstract class DottyFeature(name: String)
    extends LanguageFeature(
        DottyLanguage.currentReleaseCandidate,
        name,
        HashMap.empty,
        HashMap.empty
    )
