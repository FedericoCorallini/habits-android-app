package com.fcorallini.authentication_domain.matcher

interface EmailMatcher {
    fun isValid(email : String) : Boolean
}