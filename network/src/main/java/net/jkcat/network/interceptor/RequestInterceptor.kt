package net.jkcat.network.interceptor

import net.jkcat.network.base.NetworkRequiredInfo
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 请求拦截器
 *
 * @author JokerCats on 2021.05.05
 */
class RequestInterceptor(requiredInfo: NetworkRequiredInfo) : Interceptor {

    private val mRequiredInfo: NetworkRequiredInfo = requiredInfo

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder = chain.request().newBuilder()
        builder.addHeader("os", "android")
        builder.addHeader("version", mRequiredInfo.getVersionName())
        builder.addHeader("date", System.currentTimeMillis().toString())
        return chain.proceed(builder.build())
    }
}