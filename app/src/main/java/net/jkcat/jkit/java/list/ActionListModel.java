package net.jkcat.jkit.java.list;

import net.jkcat.common.preset.Constant;
import net.jkcat.core.model.BaseModel;
import net.jkcat.jkit.java.JNetApi;
import net.jkcat.jkit.network.ApiService;
import net.jkcat.network.BaseObserver;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.disposables.Disposable;

public class ActionListModel extends BaseModel<ActionListResult, List<ActionListResult.ActionEntity.ActionBean>> {

    private static final String CACHE_KEY_ACTION_LIST = "cache_key_action_list";

    public ActionListModel() {
        super(false, CACHE_KEY_ACTION_LIST, null);
    }

    @Override
    public void requestData() {
        JNetApi.getInstance().getService(ApiService.class)
                .getActionList(String.valueOf(pageNumber), Constant.REQUEST_PAGE_SIZE, "")
                .compose(JNetApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<ActionListResult>() {
                    @Override
                    public void onRequestFailure(@NotNull Throwable throwable) {
                        onFailure(throwable);
                    }

                    @Override
                    public void onRequestSuccess(@NotNull ActionListResult result) {
                        onSuccess(result, false);
                    }

                    @Override
                    public void onRequestInit(@NotNull Disposable disposable) {
                        addDisposable(disposable);
                    }
                });
    }

    @Override
    public void onSuccess(ActionListResult response, boolean isFromCache) {
        notifyResultToListeners(response, response.page.list, isFromCache);
    }

    @Override
    public void onFailure(Throwable throwable) {
        loadFail(throwable.getMessage());
    }

}
