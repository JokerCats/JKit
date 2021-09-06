package net.jkcat.core.vm;

import androidx.lifecycle.LifecycleObserver;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import net.jkcat.core.R;
import net.jkcat.core.model.BaseModel;
import net.jkcat.core.model.PagingResult;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseViewModel<R> extends ViewModel implements LifecycleObserver, IBaseModelListener<R> {
    protected BaseModel<R> model;
    public MutableLiveData<R> dataBox = new MutableLiveData<>();
    public MutableLiveData<ViewStatus> viewStatus = new MutableLiveData<>();
    public MutableLiveData<String> errorMsg = new MutableLiveData<>();

    public BaseViewModel() {
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
    public void onLoadSuccess(R data, PagingResult... pageInfo) {
        if (model.isPaging()) {
//            if (pageInfo[0].isFirstPage) {
//                List<D> value = dataBox.getValue();
//                if (value != null && !value.isEmpty()) {
//                    dataBox.getValue().clear();
//                }
//            }
//
//            if (pageInfo[0].isEmpty) {
//                if (pageInfo[0].isFirstPage) {
//                    viewStatus.setValue(ViewStatus.EMPTY);
//                } else {
//                    viewStatus.setValue(ViewStatus.NO_MORE_DATA);
//                }
//            } else {
//                List<D> value = dataBox.getValue();
//                if (value != null && !value.isEmpty()) {
//                    dataBox.getValue().addAll(data);
//                    dataBox.postValue(dataBox.getValue());
//                }
//                viewStatus.setValue(ViewStatus.NORMAL);
//            }
        } else {
            dataBox.setValue(data);
            dataBox.postValue(dataBox.getValue());
            viewStatus.setValue(ViewStatus.NORMAL);
        }
        viewStatus.postValue(viewStatus.getValue());
    }

    @Override
    public void onLoadFailure(String prompt, PagingResult... pageInfo) {
        errorMsg.setValue(prompt);
        if (model.isPaging() && !pageInfo[0].isFirstPage) {
            viewStatus.setValue(ViewStatus.LOAD_MORE_FAILED);
        } else {
            viewStatus.setValue(ViewStatus.REFRESH_FAILED);
        }
        viewStatus.postValue(viewStatus.getValue());
    }

}
