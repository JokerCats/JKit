package net.jkcat.jkit.java;

import net.jkcat.network.BaseResult;
import net.jkcat.network.base.NetworkApi;
import net.jkcat.network.handler.ExceptionHandler;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import io.reactivex.functions.Function;
import okhttp3.Interceptor;

/**
 * @author JokerCats on 2021.05.08
 */
public class JNetApi extends NetworkApi {

    private static volatile JNetApi sInstance;

    public static JNetApi getInstance() {
        if (sInstance == null) {
            synchronized (JNetApi.class) {
                sInstance = new JNetApi();
            }
        }
        return sInstance;
    }

    protected JNetApi() {
        super("http://app.rv888.vip/");
    }

    @Nullable
    @Override
    protected Interceptor getInterceptor() {
        return null;
    }

    @NotNull
    @Override
    protected <T> Function<T, T> getAppErrorHandler() {
        return response -> {
            if (response instanceof BaseResult) {
                BaseResult result = (BaseResult) response;
                if (result.getCode() != 0) {
                    ExceptionHandler.ServerException exception =
                            new ExceptionHandler.ServerException(result.getMsg());
                    exception.setCode(result.getCode());
                    throw exception;
                }
            }
            return response;
        };
    }

}
