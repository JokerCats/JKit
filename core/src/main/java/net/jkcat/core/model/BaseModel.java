package net.jkcat.core.model;

import android.text.TextUtils;

import androidx.annotation.CallSuper;

import com.google.gson.Gson;

import net.jkcat.core.utils.GenericUtils;
import net.jkcat.core.utils.SharedPrefsUtils;
import net.jkcat.core.vm.IBaseModelListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public abstract class BaseModel<R, T> implements IBaseModelObserver<R> {

    private final int INIT_PAGE_NUMBER;

    private final boolean mIsPaging;
    protected int pageNumber;
    private final String mCacheKey;
    private final String mPredefinedData;
    private BaseCacheData<R> mCacheData;

    protected WeakReference<IBaseModelListener<T>> mReferenceModelListener;

    private boolean mIsLoading;
    private CompositeDisposable compositeDisposable;

    /**
     * Model 构造方法说明
     *
     * @param isPaging       是否需要分页
     * @param cacheKey       缓存 key
     * @param predefinedData 预置数据
     * @param initPageNumber 起始页码
     */
    public BaseModel(boolean isPaging, String cacheKey, String predefinedData, int... initPageNumber) {
        mIsPaging = isPaging;
        if (initPageNumber != null && initPageNumber.length > 0) {
            INIT_PAGE_NUMBER = initPageNumber[0];
        } else {
            INIT_PAGE_NUMBER = 0;
        }
        pageNumber = INIT_PAGE_NUMBER;

        mCacheKey = cacheKey;
        mPredefinedData = predefinedData;
        if (mCacheKey != null) {
            mCacheData = new BaseCacheData<>();
        }
    }

    public boolean isPaging() {
        return mIsPaging;
    }

    /**
     * 注册监听
     */
    public void register(IBaseModelListener<T> listener) {
        if (listener != null) {
            mReferenceModelListener = new WeakReference<>(listener);
        }
    }

    /**
     * 对数据进行缓存
     */
    protected void saveDataToPreference(R response) {
        if (response != null) {
            mCacheData.data = response;
            mCacheData.updateTimeInMills = System.currentTimeMillis();
            SharedPrefsUtils.putValue(mCacheKey, new Gson().toJson(mCacheData));
        }
    }

    public void refresh() {
        if (!mIsLoading) {
            if (isPaging()) {
                pageNumber = INIT_PAGE_NUMBER;
            }
            mIsLoading = true;
            requestData();
        }
    }

    public void loadNextPage() {
        if (!mIsLoading) {
            mIsLoading = true;
            requestData();
        }
    }

    protected abstract void requestData();

    /**
     * 更新数据策略 默认为每次都进行更新
     */
    protected boolean isNeedToUpdate() {
        return true;
    }

    @CallSuper
    public void cancel() {
        if (compositeDisposable != null && !compositeDisposable.isDisposed()) {
            compositeDisposable.dispose();
        }
    }

    public void addDisposable(Disposable disposable) {
        if (disposable == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }

    public void getCacheDataAndRequest() {
        mIsLoading = true;

        if (mCacheKey != null) {
            // 检查缓存数据
            String cacheStr = SharedPrefsUtils.getValue(mCacheKey, "");
            if (!TextUtils.isEmpty(cacheStr)) {
                try {
                    R cacheData = new Gson().fromJson(new JSONObject(cacheStr).getString("data"), (Class<R>) GenericUtils.getGenericType(BaseModel.this));
                    if (cacheData != null) {
                        onSuccess(cacheData, true);
                        if (isNeedToUpdate()) {
                            requestData();
                        }
                        return;
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            // 检查预置数据
            if (mPredefinedData != null) {
                try {
                    R predefinedData = new Gson().fromJson(mPredefinedData, (Class<R>) GenericUtils.getGenericType(BaseModel.this));
                    if (predefinedData != null) {
                        onSuccess(predefinedData, true);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

        requestData();
    }

    /**
     * 发送消息给 UI 线程
     */
    protected void notifyResultToListeners(R response, T data, boolean isFromCache) {
        IBaseModelListener<T> listener = mReferenceModelListener.get();
        if (listener != null) {
            if (isPaging()) {
                listener.onLoadSuccess(this, data,
                        isFromCache ? new PagingResult(false, true, true) :
                                new PagingResult(data == null ? true : ((List) data).isEmpty(),
                                        pageNumber == INIT_PAGE_NUMBER,
                                        data == null ? false : ((List) data).size() > 0));
            } else {
                listener.onLoadSuccess(this, data);
            }
        }

        if (isPaging()) {
            if (mCacheKey != null && pageNumber == INIT_PAGE_NUMBER && !isFromCache) {
                saveDataToPreference(response);
            }

            if (!isFromCache) {
                if (data instanceof List && ((List) data).size() > 0) {
                    pageNumber += 1;
                }
            }
        } else {
            if (mCacheKey != null && !isFromCache) {
                saveDataToPreference(response);
            }
        }

        if (!isFromCache) {
            mIsLoading = false;
        }
    }

    protected void loadFail(final String errorMsg) {
        IBaseModelListener<T> listener = mReferenceModelListener.get();
        if (listener != null) {
            if (isPaging()) {
                listener.onLoadFailure(BaseModel.this, errorMsg, new PagingResult(true, true, false));
            } else {
                listener.onLoadFailure(BaseModel.this, errorMsg);
            }
        }
        mIsLoading = false;
    }

    protected boolean isRefresh() {
        return pageNumber == INIT_PAGE_NUMBER;
    }

    protected boolean isLoading() {
        return mIsLoading;
    }

}
