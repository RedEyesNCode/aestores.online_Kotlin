package com.ushatech.aestoreskotlin.domain

import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class BasicAuthInterceptor():Interceptor {

    private var credentials: String? = null

    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        // NEED TO SEND THE BELOW CREDENTIALS IN EVERY ANDROID REQUEST.

        credentials = Credentials.basic(username = "aeuserapid", password = "st@o4eOf43kde*#@dke!d!")

        val authenticatedRequest: Request = request.newBuilder()
            .header("Authorization", credentials!!).build()
        return chain.proceed(authenticatedRequest)
    }
}