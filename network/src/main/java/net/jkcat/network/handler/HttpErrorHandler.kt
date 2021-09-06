package net.jkcat.network.handler

import io.reactivex.Observable
import io.reactivex.functions.Function

/**
 * @author JokerCats on 2021.05.07
 */
class HttpErrorHandler<T> : Function<Throwable, Observable<T>> {

    override fun apply(throwable: Throwable): Observable<T> {
        return Observable.error(ExceptionHandler.handleException(throwable))
    }

}