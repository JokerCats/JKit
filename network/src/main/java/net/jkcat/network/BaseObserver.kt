package net.jkcat.network

import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 * @author JokerCats on 2021.05.05
 */
abstract class BaseObserver<T : BaseResult> : Observer<T> {

    override fun onSubscribe(d: Disposable) {
        onRequestInit(d)
    }

    override fun onNext(t: T) {
        onRequestSuccess(t)
    }

    override fun onError(e: Throwable) {
        onRequestFailure(e)
    }

    override fun onComplete() {

    }

    abstract fun onRequestInit(disposable: Disposable)

    abstract fun onRequestSuccess(result: T)

    abstract fun onRequestFailure(throwable: Throwable)

}