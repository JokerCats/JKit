package net.jkcat.network.handler

import com.google.gson.JsonParseException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.text.ParseException
import javax.net.ssl.SSLHandshakeException


/**
 * @author JokerCats on 2021.05.07
 */
object ExceptionHandler {

    private const val UNAUTHORIZED = 401
    private const val FORBIDDEN = 403
    private const val NOT_FOUND = 404
    private const val REQUEST_TIMEOUT = 408
    private const val INTERNAL_SERVER_ERROR = 500
    private const val BAD_GATEWAY = 502
    private const val SERVICE_UNAVAILABLE = 503
    private const val GATEWAY_TIMEOUT = 504
    private const val HTTP_NOT_SUPPORT = 505

    fun handleException(e: Throwable): ResponseThrowable {
        var ex: ResponseThrowable
        if (e is HttpException) {
            val httpException = e as? HttpException
            ex = ResponseThrowable(e, ERROR.HTTP_ERROR)
            when (httpException?.code()) {
                UNAUTHORIZED -> {
                    ex.message = "授权验证失败"
                }

                FORBIDDEN -> {
                    ex.message = "服务器拒绝请求"
                }

                NOT_FOUND -> {
                    ex.message = "未找到指定资源"
                }

                REQUEST_TIMEOUT -> {
                    ex.message = "服务请求超时"
                }

                INTERNAL_SERVER_ERROR -> {
                    ex.message = "服务器内部错误"
                }

                BAD_GATEWAY -> {
                    ex.message = "网关错误"
                }

                SERVICE_UNAVAILABLE -> {
                    ex.message = "服务器不可用"
                }

                GATEWAY_TIMEOUT -> {
                    ex.message = "网关超时"
                }

                HTTP_NOT_SUPPORT -> {
                    ex.message = "HTTP版本存在差异"
                }

                else -> {
                    ex.message = "网络错误"
                }
            }
        } else if (e is ServerException) {
            val resultException = e as? ServerException
            if (resultException == null) {
                ex = ResponseThrowable(e, ERROR.SERVER_ERROR)
                ex.message = "服务异常"
            } else {
                resultException.let {
                    ex = ResponseThrowable(it, it.code)
                    ex.message = it.message ?: "服务异常"
                }
            }
        } else if (e is JsonParseException
            || e is JSONException
            || e is ParseException
        ) {
            ex = ResponseThrowable(e, ERROR.PARSE_ERROR)
            ex.message = "数据解析错误"
        } else if (e is ConnectException) {
            ex = ResponseThrowable(e, ERROR.NETWORK_ERROR)
            ex.message = "网络连接失败"
        } else if (e is SSLHandshakeException) {
            ex = ResponseThrowable(e, ERROR.SSL_ERROR)
            ex.message = "证书验证失败"
        } else if (e is SocketTimeoutException) {
            ex = ResponseThrowable(e, ERROR.TIMEOUT_ERROR)
            ex.message = "连接超时"
        } else {
            ex = ResponseThrowable(e, ERROR.UNKNOWN)
            ex.message = "未知错误"
        }
        return ex
    }


    object ERROR {
        /**
         * 未知错误
         */
        const val UNKNOWN = 1000

        /**
         * 解析错误
         */
        const val PARSE_ERROR = 1001

        /**
         * 网路错误
         */
        const val NETWORK_ERROR = 1002

        /**
         * 协议错误
         */
        const val HTTP_ERROR = 1003

        /**
         * 证书错误
         */
        const val SSL_ERROR = 1004

        /**
         * 连接超时
         */
        const val TIMEOUT_ERROR = 1005

        /**
         * 服务错误
         */
        const val SERVER_ERROR = 1006
    }

    class ResponseThrowable(throwable: Throwable, var code: Int) : Exception() {
        override var message = ""
    }

    class ServerException(msg: String) : RuntimeException(msg) {
        var code = -1
    }

}