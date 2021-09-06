package net.jkcat.jkit.java.list;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import net.jkcat.core.model.BaseModel;
import net.jkcat.core.vm.IBaseModelListener;
import net.jkcat.core.model.PagingResult;
import net.jkcat.jkit.R;
import net.jkcat.jkit.databinding.ActivityActionListBinding;

import java.util.List;

/**
 * 列表示例
 *
 * @author JokerCats on 2021.09.03
 */
public class ActionListActivity extends AppCompatActivity implements IBaseModelListener<List<ActionListResult.ActionEntity.ActionBean>> {

    private ActionListAdapter mAdapter;
    private ActivityActionListBinding mBinding;
    private ActionListModel mViewModel = new ActionListModel();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_action_list);

        mAdapter = new ActionListAdapter();
        mBinding.rvActionList.setLayoutManager(new LinearLayoutManager(this));
        mBinding.rvActionList.setAdapter(mAdapter);

        mViewModel.register(this);
        mViewModel.getCacheDataAndRequest();
    }

    @Override
    public void onLoadSuccess(BaseModel<?, List<ActionListResult.ActionEntity.ActionBean>> model, List<ActionListResult.ActionEntity.ActionBean> data, PagingResult... pageInfo) {
        mAdapter.setData(data);
    }

    @Override
    public void onLoadFailure(BaseModel<?, List<ActionListResult.ActionEntity.ActionBean>> model, String prompt, PagingResult... pageInfo) {
        Toast.makeText(this, prompt, Toast.LENGTH_SHORT).show();
    }
}
