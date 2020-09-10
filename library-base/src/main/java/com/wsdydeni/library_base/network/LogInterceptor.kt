package com.wsdydeni.library_base.network

import android.text.TextUtils
import android.util.Log
import okhttp3.*
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import java.net.URLDecoder
import kotlin.Exception

class LogInterceptor(private val message : String, private val showLog : Boolean) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        if(showLog) logRequest(request)
        val response = chain.proceed(request)
        val maxStale = 60 * 60 * 24
        response.newBuilder().header("Cache-Control","public,only-if-cached,max-stale=$maxStale").removeHeader("Pragma").build()
        return logResponse(response)
    }

    private fun logResponse(response: Response) : Response {
        try {
            Log.e(message,"------------Response Begin-------------")
            Log.e(message,"request url: ${response.newBuilder().build().request.url}")
            Log.e(message,"response code: ${response.newBuilder().build().code}")
            if(!TextUtils.isEmpty(response.newBuilder().build().message)) Log.e(message,"response message: ${response.message}")
            var body = response.newBuilder().build().body
            body?.let { it ->
                it.contentType()?.let { mediaType ->
                    if (isText(mediaType)) {
                        Log.e(message,"response body: $body")
                        Log.e(message,"------------Response End-------------")
                        body = body.toString().toResponseBody(mediaType)
                        return if(body?.contentLength() != 0L) {
                            response.newBuilder().body(body).build()
                        } else {
                            response
                        }
                    } else {
                        Log.e(message,"An error occurred in the response content")
                    }
                }
            }

        }catch (e : Exception) {

        }
        return response
    }

    private fun logRequest(request: Request) {
        try {
            Log.e(message,"------------Request Begin-------------")
            Log.e(message,"request method: ${request.method}")
            Log.e(message,"request path: ${request.url}")
            request.body?.contentType()?.let {
                Log.e(message,"request type: $it")
                if(isText(it)) Log.e(message,bodyToString(request))
                else Log.e(message,"unable to identify")
            }
            Log.e(message,"------------Request End-------------")
        }catch (e : Exception) {
            e.printStackTrace()
        }
    }

    private fun isText(mediaType : MediaType) : Boolean {
        if(mediaType.type == "text") return true
        return mediaType.subtype == "json" || mediaType.subtype == "xml" || mediaType.subtype == "html"
                || mediaType.subtype == "webviewhtml" || mediaType.subtype == "x-www-form-urlencoded"
    }

    private fun bodyToString(request : Request): String {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body?.writeTo(buffer)
            val message = buffer.readUtf8()
            val sub = message.substring("message=".length)
            URLDecoder.decode(sub,"utf-8")
        }catch (e : Exception) {
            "An error occurred during parsing"
        }
    }
}