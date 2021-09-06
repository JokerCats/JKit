package net.jkcat.network.base

import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import net.jkcat.network.handler.HttpErrorHandler
import net.jkcat.network.interceptor.RequestInterceptor
import net.jkcat.network.interceptor.ResponseInterceptor
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author JokerCats on 2021.05.05
 */
abstract class NetworkApi protected constructor(url: String) {

    companion object {

        private var mNetworkRequiredInfo: NetworkRequiredInfo? = null

        fun init(networkRequiredInfo: NetworkRequiredInfo) {
            mNetworkRequiredInfo = networkRequiredInfo
        }

    }

    private var mBaseUrl: String = url

    private val mRetrofitMap = HashMap<String, Retrofit>()

    private fun getRetrofit(service: Class<*>): Retrofit =
        mRetrofitMap[mBaseUrl + service.name]?.let {
            return it
        } ?: run {
            val builder = Retrofit.Builder()
            builder.baseUrl(mBaseUrl)
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            val retrofit = builder.build()
            mRetrofitMap[mBaseUrl + service.name] = retrofit
            return retrofit
        }

    private fun getOkHttpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        mNetworkRequiredInfo?.let { info ->
            // 自定义拦截器
            getInterceptor()?.let { builder.addInterceptor(it) }
            builder.addInterceptor(RequestInterceptor(info))
            if (info.isDebug()) {
                val loggingInterceptor = HttpLoggingInterceptor()
                loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
                builder.addInterceptor(loggingInterceptor)
            }
        }
        builder.addInterceptor(ResponseInterceptor())

        return builder.build()
    }

    fun <T> applySchedulers(): ObservableTransformer<T, T> {
        return ObservableTransformer { upstream ->
            val observable = upstream.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(getAppErrorHandler<T>())
                .onErrorResumeNext(HttpErrorHandler<T>())
            observable
        }
    }

    fun <T> getService(service: Class<T>): T {
        return getRetrofit(service).create(service)
    }

    protected abstract fun getInterceptor(): Interceptor?

    protected abstract fun <T> getAppErrorHandler(): Function<T, T>

}