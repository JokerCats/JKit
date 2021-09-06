package net.jkcat.jkit.java.list;

import androidx.recyclerview.widget.LinearLayoutManager;

import net.jkcat.core.base.BaseVMActivity;
import net.jkcat.core.vm.ViewStatus;
import net.jkcat.jkit.R;
import net.jkcat.jkit.databinding.ActivityActionListBinding;

/**
 * 列表示例
 *
 * @author JokerCats on 2021.09.03
 */
public class ActionListActivity extends BaseVMActivity<ActivityActionListBinding, ActionListViewModel> {

    private ActionListAdapter mAdapter;

    @Override
    protected void initParameters() {
        mAdapter = new ActionListAdapter();
        dataBinding.rvActionList.setLayoutManager(new LinearLayoutManager(this));
        dataBinding.rvActionList.setAdapter(mAdapter);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_action_list;
    }

    @Override
    public ActionListViewModel getViewModel() {
        return new ActionListViewModel();
    }

    @Override
    public void onChanged(Object o) {
        super.onChanged(o);
        if (o instanceof ViewStatus) {
            switch ((ViewStatus) o) {
                case NORMAL:
                    ActionListResult value = viewModel.dataBox.getValue();
                    if (value != null) {
                        mAdapter.setData(value.page.list);
                    }
                    break;
            }
        }
    }

}
