package com.pavlovicaleksandar.tablefinder.service

import java.security.MessageDigest

fun hashPassword(input: String): String {
    val md = MessageDigest.getInstance("MD5")
    val digest = md.digest(input.toByteArray())
    return digest.fold("") { str, byte ->
        str + "%02x".format(byte)
    }
}
