package net.jkcat.jkit.java.activity;

import net.jkcat.core.model.BaseModel;
import net.jkcat.jkit.java.JNetApi;
import net.jkcat.jkit.network.ApiService;
import net.jkcat.network.BaseObserver;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

/**
 * @author JokerCats on 2021.09.06
 */
class ActionDetailModel extends BaseModel<ActionDetailResult> {

    private static final String CACHE_KEY_ACTION_DETAIL = "cache_key_action_detail";

    public ActionDetailModel() {
        super(false, CACHE_KEY_ACTION_DETAIL, null);
    }

    @Override
    protected void requestData() {
        JNetApi.getInstance().getService(ApiService.class)
                .getActionDetail("", "343")
                .compose(JNetApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<ActionDetailResult>() {
                    @Override
                    public void onRequestInit(@NotNull Disposable disposable) {
                        addDisposable(disposable);
                    }

                    @Override
                    public void onRequestFailure(@NotNull Throwable throwable) {
                        onFailure(throwable);
                    }

                    @Override
                    public void onRequestSuccess(@NotNull ActionDetailResult result) {
                        onSuccess(result, false);
                    }
                });
    }

    @Override
    public void onSuccess(ActionDetailResult response, boolean isFromCache) {
        notifyResultToListeners(response, isFromCache);
    }

    @Override
    public void onFailure(Throwable throwable) {
        loadFail(throwable.getMessage());
    }

}
