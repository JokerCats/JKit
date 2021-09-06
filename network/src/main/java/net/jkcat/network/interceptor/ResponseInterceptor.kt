package net.jkcat.network.interceptor

import okhttp3.Interceptor
import okhttp3.Response

/**
 * 回调拦截器
 *
 * @author JokerCats on 2021.05.05
 */
class ResponseInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestTime = System.currentTimeMillis()
        val response = chain.proceed(chain.request())
//        Log.d(
//            "request",
//            "Current request callback time ==> ${System.currentTimeMillis() - requestTime}ms"
//        )
        return response
    }

}