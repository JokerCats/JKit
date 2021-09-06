package net.jkcat.network.handler

import io.reactivex.functions.Function
import net.jkcat.network.BaseResult

/**
 * 默认异常处理模板
 *
 * @author JokerCats on 2021.05.08
 */
class HandlerFunction<T> : Function<T, T> {
    override fun apply(response: T): T {
        if (response is BaseResult) {
            val result = response as BaseResult
            if (result.code != 200) {
                val exception = ExceptionHandler.ServerException(result.msg)
                exception.code = result.code
                throw exception
            }
        }
        return response
    }
}