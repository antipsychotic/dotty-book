package com.nitrograph.book

import com.nitrograph.language._
import com.nitrograph.book.author._

case class Book(author: BookAuthor, name: String) {
    override def toString: String = s"$name written by $author\n"
}
