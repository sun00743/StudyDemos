package com.hd123.kds.util

class VerifyUtils {

    companion object {
        const val IP_REGEX = ("^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."
                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$")

        fun verifyIP(string: String): Boolean {
            if (string.isBlank()) {
                return false
            }
            return string.matches(Regex(IP_REGEX))
        }
    }

}