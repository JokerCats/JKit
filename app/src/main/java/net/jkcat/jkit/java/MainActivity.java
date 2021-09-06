package net.jkcat.jkit.java;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import net.jkcat.jkit.R;
import net.jkcat.jkit.bean.ActivityBean;
import net.jkcat.jkit.java.activity.ActionDetailActivity;
import net.jkcat.jkit.java.list.ActionListActivity;
import net.jkcat.jkit.network.ApiService;
import net.jkcat.network.BaseObserver;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.Disposable;

/**
 * @author JokerCats on 2021.05.08
 */
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_request).setOnClickListener(v -> getBanner());

        findViewById(R.id.btn_demo_activity).setOnClickListener(v ->
                startActivity(new Intent(this, ActionDetailActivity.class)));

        findViewById(R.id.btn_demo_list).setOnClickListener(v -> {
            startActivity(new Intent(this, ActionListActivity.class));
        });
    }

    private void getBanner() {
        JNetApi.getInstance().getService(ApiService.class)
                .getActivityInfo()
                .compose(JNetApi.getInstance().applySchedulers())
                .subscribe(new BaseObserver<ActivityBean>() {
                    @Override
                    public void onRequestInit(@NotNull Disposable disposable) {

                    }

                    @Override
                    public void onRequestFailure(@NotNull Throwable e) {
                        Log.i("net", "request error msg is ${e.message}");
                    }

                    @Override
                    public void onRequestSuccess(@NotNull ActivityBean result) {
                        Log.i("net", new Gson().toJson(result));
                    }
                });
    }
}
