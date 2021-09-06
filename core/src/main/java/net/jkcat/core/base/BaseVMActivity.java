package net.jkcat.core.base;

import android.os.Bundle;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;

import net.jkcat.core.vm.BaseViewModel;
import net.jkcat.core.vm.ViewStatus;

public abstract class BaseVMActivity<V extends ViewDataBinding, VM extends BaseViewModel>
        extends BaseActivity implements Observer {

    protected V dataBinding;
    protected VM viewModel;

    @LayoutRes
    public abstract int getLayoutId();

    public abstract VM getViewModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dataBinding = DataBindingUtil.setContentView(this, getLayoutId());
        viewModel = getViewModel();

        initParameters();
        getLifecycle().addObserver(viewModel);

        viewModel.dataBox.observe(this, this);
        viewModel.viewStatus.observe(this, this);
    }

    @Override
    public void onChanged(Object o) {
        if (o instanceof ViewStatus) {
            switch ((ViewStatus) o) {
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
                    break;
                case LOAD_MORE_FAILED:
                    mToastMaster.updateTips(viewModel.errorMsg.toString()).launch();
                    break;
            }
        }
    }

}
