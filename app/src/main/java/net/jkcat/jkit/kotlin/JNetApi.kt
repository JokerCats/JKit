package net.jkcat.jkit.kotlin

import io.reactivex.functions.Function
import net.jkcat.network.base.NetworkApi
import net.jkcat.network.handler.HandlerFunction
import okhttp3.Interceptor

/**
 * @author JokerCats on 2021.05.08
 */
object JNetApi : NetworkApi("http://106.15.33.253:6385/hz/app/") {

    override fun getInterceptor(): Interceptor? {
        return null
    }

    override fun <T> getAppErrorHandler(): Function<T, T> {
        return HandlerFunction()
    }

}