package net.jkcat.core.model;

public interface IBaseModelObserver<T> {

    void onSuccess(T response, boolean isFromCache);

    void onFailure(Throwable throwable);

}
