package com.nitrograph.dotty
package book

import com.nitrograph.book.author._
import com.nitrograph.book._
import com.nitrograph.dotty.book.author._

object DottyBook {
    def main(arguments: Array[String]): Unit = {
        val dottyBook = Book(
            author = MaximChepel,
            name = "DottyBook"
        )
        println(dottyBook)
    }
}
