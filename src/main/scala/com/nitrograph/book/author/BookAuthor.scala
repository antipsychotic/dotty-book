package com.nitrograph.book
package author

case class BookAuthor(val forename: String, val lastname: String) {
    override def toString: String = s"$forename $lastname"
}
