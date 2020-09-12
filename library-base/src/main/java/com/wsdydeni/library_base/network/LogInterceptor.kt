package com.wsdydeni.library_base.network

import com.blankj.utilcode.util.LogUtils
import okhttp3.Interceptor
import okhttp3.Response
import okio.Buffer
import java.io.IOException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException

class LogInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val utf8 = Charset.forName("UTF-8")
        // 打印请求报文
        val request = chain.request()
        val requestBody = request.body
        var reqBody: String? = null
        if (requestBody != null) {
            val buffer = Buffer()
            requestBody.writeTo(buffer)
            var charset: Charset? = utf8
            val contentType = requestBody.contentType()
            if (contentType != null) {
                charset = contentType.charset(utf8)
            }
            charset?.let {
                reqBody = buffer.readString(it)
            }
        }
        LogUtils.eTag("发起请求", request.method, request.url, request.headers, reqBody)
        // 打印返回报文
        // 先执行请求，才能够获取报文
        val response = chain.proceed(request)
        val responseBody = response.body
        var respBody: String? = null
        if (responseBody != null) {
            val source = responseBody.source()
            source.request(java.lang.Long.MAX_VALUE)
            val buffer = source.buffer()

            var charset: Charset? = utf8
            val contentType = responseBody.contentType()
            if (contentType != null) {
                try {
                    charset = contentType.charset(utf8)
                } catch (e: UnsupportedCharsetException) {
                    e.printStackTrace()
                }
            }
            respBody = buffer.clone().readString(charset!!)
        }

        LogUtils.eTag("请求响应", response.code.toString()+ response.message+response.request.url, reqBody, respBody)

        return response
    }

}


