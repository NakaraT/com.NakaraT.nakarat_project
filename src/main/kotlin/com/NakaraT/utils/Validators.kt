package com.NakaraT.utils

fun checkForValid(string: String): Boolean {
    return Regex("""/\A[^@]+@([^@\.]+\.)+[^@\.]+\z/""").matches(string)
}
