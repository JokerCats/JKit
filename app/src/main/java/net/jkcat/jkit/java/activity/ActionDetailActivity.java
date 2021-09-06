package net.jkcat.jkit.java.activity;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import net.jkcat.jkit.R;
import net.jkcat.jkit.databinding.ActivityActionDetailBinding;
import net.jkcat.jkit.java.JNetApi;
import net.jkcat.jkit.network.ApiService;
import net.jkcat.network.BaseObserver;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

/**
 * 活动示例
 *
 * @author JokerCats on 2021.09.03
 */
public class ActionDetailActivity extends AppCompatActivity {

    private ActivityActionDetailBinding mBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_action_detail);

        getActionInfo();
    }

    private void getActionInfo() {
        JNetApi.getInstance().getService(ApiService.class)
                .getActionDetail("", "343")
                .compose(JNetApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<ActionDetailResult>() {
                    @Override
                    public void onRequestInit(@NotNull Disposable disposable) {

                    }

                    @Override
                    public void onRequestFailure(@NotNull Throwable e) {
                        Toast.makeText(ActionDetailActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onRequestSuccess(@NotNull ActionDetailResult result) {
                        if (mBinding != null) {
                            mBinding.setViewModel(result);
                            mBinding.executePendingBindings();
                        }
                    }
                });
    }

}
