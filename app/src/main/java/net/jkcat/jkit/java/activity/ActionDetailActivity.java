package net.jkcat.jkit.java.activity;

import net.jkcat.core.base.BaseVMActivity;
import net.jkcat.core.vm.ViewStatus;
import net.jkcat.jkit.R;
import net.jkcat.jkit.databinding.ActivityActionDetailBinding;

/**
 * 活动示例
 *
 * @author JokerCats on 2021.09.03
 */
public class ActionDetailActivity extends BaseVMActivity<ActivityActionDetailBinding, ActionDetailViewModel> {

    @Override
    public int getLayoutId() {
        return R.layout.activity_action_detail;
    }

    @Override
    public ActionDetailViewModel getViewModel() {
        return new ActionDetailViewModel();
    }

    @Override
    protected void initParameters() {

    }

    @Override
    public void onChanged(Object o) {
        super.onChanged(o);
        if (o instanceof ViewStatus) {
            switch ((ViewStatus)o) {
                case NORMAL:
                    dataBinding.setViewModel(viewModel.dataBox.getValue());
                    dataBinding.executePendingBindings();
                    break;
            }
        }
    }
}
