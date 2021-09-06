package net.jkcat.core.vm;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.jkcat.core.model.BaseModel;
import net.jkcat.core.model.PagingResult;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewModel<M extends BaseModel<?, ?>, D> extends ViewModel implements LifecycleObserver, IBaseModelListener<List<D>> {
    protected M model;
    public MutableLiveData<List<D>> dataBox = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public BaseViewModel() {
        dataBox.setValue(new ArrayList<>());
        viewStatus.setValue(ViewStatus.LOADING);
        errorMsg.setValue("");
    }

    public void tryToRefresh() {
        if (model != null) {
            model.refresh();
        }
    }

    public void tryToLoadNextPage() {
        if (model != null) {
            model.loadNextPage();
        }
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (model != null) {
            model.cancel();
        }
    }

    @Override
    public void onLoadSuccess(BaseModel<?, List<D>> model, List<D> data, PagingResult... pageInfo) {
        if (model == this.model) {
            if (model.isPaging()) {
                if (pageInfo[0].isFirstPage) {
                    dataBox.getValue().clear();
                }

                if (pageInfo[0].isEmpty) {
                    if (pageInfo[0].isFirstPage) {
                        viewStatus.setValue(ViewStatus.EMPTY);
                    } else {
                        viewStatus.setValue(ViewStatus.NO_MORE_DATA);
                    }
                } else {
                    dataBox.getValue().addAll(data);
                    dataBox.postValue(dataBox.getValue());
                    viewStatus.setValue(ViewStatus.NORMAL);
                }
            } else {
                dataBox.getValue().clear();
                dataBox.setValue(data);
                dataBox.postValue(dataBox.getValue());
                viewStatus.setValue(ViewStatus.NORMAL);
            }
            viewStatus.postValue(viewStatus.getValue());
        }
    }

    @Override
    public void onLoadFailure(BaseModel<?, List<D>> model, String prompt, PagingResult... pageInfo) {
        errorMsg.setValue(prompt);
        if (model == this.model) {
            if (model.isPaging() && !pageInfo[0].isFirstPage) {
                viewStatus.setValue(ViewStatus.LOAD_MORE_FAILED);
            } else {
                viewStatus.setValue(ViewStatus.REFRESH_FAILED);
            }
            viewStatus.postValue(viewStatus.getValue());
        }
    }

}
