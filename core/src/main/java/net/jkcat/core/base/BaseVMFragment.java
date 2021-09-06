package net.jkcat.core.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ObservableArrayList;
import androidx.databinding.ViewDataBinding;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import net.jkcat.core.vm.BaseViewModel;
import net.jkcat.core.vm.ViewStatus;
import net.jkcat.core.widget.CenterToast;

import java.util.ArrayList;

public abstract class BaseVMFragment<V extends ViewDataBinding, VM extends BaseViewModel, D> extends Fragment implements Observer {
    protected VM viewModel;
    protected V dataBinding;
    protected String mFragmentTag = "";
    protected CenterToast mToastMaster;

    public abstract int getBindingVariable();

    @LayoutRes
    public abstract int getLayoutId();

    public abstract VM getViewModel();

    public abstract void onItemInserted(ArrayList<D> sender);

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initParameters();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        dataBinding = DataBindingUtil.inflate(inflater, getLayoutId(), container, false);
        return dataBinding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = getViewModel();
        getLifecycle().addObserver(viewModel);
        viewModel.dataBox.observe(this, this);
        viewModel.viewStatus.observe(this, this);
        if (getBindingVariable() > 0) {
            dataBinding.setVariable(getBindingVariable(), viewModel);
            dataBinding.executePendingBindings();
        }
    }

    protected void initParameters() {

    }

    protected abstract void onRetryClick();

    protected abstract String getFragmentTag();

    @Override
    public void onChanged(Object object) {
        if (object instanceof ViewStatus) {
            switch ((ViewStatus) object) {
                case LOADING:
                    mToastMaster.updateTips("加载中").launch();
                    break;
                case EMPTY:
                    mToastMaster.updateTips("数据为空").launch();
                    break;
                case NORMAL:
                    mToastMaster.updateTips("数据正常").launch();
                    break;
                case NO_MORE_DATA:
                    mToastMaster.updateTips("没有更多数据了").launch();
                    break;
                case REFRESH_FAILED:
                    if (((ObservableArrayList) viewModel.dataBox.getValue()).isEmpty()) {
                        mToastMaster.updateTips("刷新失败").launch();
                    } else {
                        mToastMaster.updateTips(viewModel.errorMsg.toString()).launch();
                    }
                    break;
                case LOAD_MORE_FAILED:
                    mToastMaster.updateTips(viewModel.errorMsg.toString()).launch();
                    break;
            }
        } else if (object instanceof ArrayList) {
            onItemInserted((ArrayList<D>) object);
        }
    }

    protected void showLoading() {

    }
}
