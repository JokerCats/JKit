package net.jkcat.jkit.java.list;

import android.content.Context;
import android.view.View;
import android.widget.Toast;

import net.jkcat.core.item.BaseCustomView;
import net.jkcat.jkit.R;
import net.jkcat.jkit.databinding.ItemActionListBinding;

public class ActionListView extends BaseCustomView<ItemActionListBinding, ActionListResult.ActionEntity.ActionBean> {

    public ActionListView(Context context) {
        super(context);
    }

    @Override
    protected int setViewLayoutId() {
        return R.layout.item_action_list;
    }

    @Override
    protected void onRootClick(View view, int index) {
        Toast.makeText(getContext(), "click index is " + index, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void bindViewData(ActionListResult.ActionEntity.ActionBean data) {
        getDataBinding().setViewModel(data);
    }
}
