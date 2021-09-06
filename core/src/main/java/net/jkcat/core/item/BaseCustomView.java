package net.jkcat.core.item;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseCustomView<T extends ViewDataBinding, S extends BaseCustomViewModel> extends LinearLayoutCompat implements ICustomView<S>, View.OnClickListener {

    private T dataBinding;
    private S viewModel;

    protected int index = -1;

    public BaseCustomView(Context context) {
        super(context);
        initView();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public BaseCustomView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @Override
    public void setData(S data) {
        viewModel = data;
        bindViewData(viewModel);
        if (dataBinding != null) {
            dataBinding.executePendingBindings();
        }
    }

    @Override
    public void setIndex(int position) {
        index = position;
    }

    private void initView() {
        LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        dataBinding = DataBindingUtil.inflate(inflater, setViewLayoutId(), this, false);
        dataBinding.getRoot().setOnClickListener(view -> onRootClick(view, index));
        setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        addView(dataBinding.getRoot());
    }

    @Override
    public void onClick(View v) {

    }

    public View getRootView() {
        return dataBinding.getRoot();
    }

    protected T getDataBinding() {
        return dataBinding;
    }

    protected S getViewModel() {
        return viewModel;
    }

    protected abstract int setViewLayoutId();

    protected abstract void onRootClick(View view, int index);

    protected abstract void bindViewData(S data);
}
