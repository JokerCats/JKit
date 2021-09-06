package net.jkcat.core.base;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Bundle;
import android.os.Looper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import net.jkcat.core.widget.CenterToast;

/**
 * @author JokerCats on 2021.09.06
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected Context mContext;

    protected CenterToast mToastMaster;

    @SuppressLint("SourceLockedOrientationActivity")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        super.onCreate(savedInstanceState);
        mContext = this;
        mToastMaster = new CenterToast(mContext);
    }

    @Override
    public void onConfigurationChanged(@NonNull Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        // 限制应用字体比例不随系统发生改变
        if (newConfig.fontScale != 1) {
            getResources();
        }
    }

    @Override
    public Resources getResources() {
        Resources resources = super.getResources();
        if (resources.getConfiguration().fontScale != 1) {
            Configuration newConfig = new Configuration();
            // 设置为默认值，即 fontScale 为 1
            newConfig.setToDefaults();
            resources.updateConfiguration(newConfig, resources.getDisplayMetrics());
        }
        return resources;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mToastMaster != null) {
            mToastMaster.intercept();
        }
    }

    /**
     * 可在子线程中答应 Toast
     *
     * @param prompt 提示内容
     */
    protected void showToast(String prompt) {
        if (Looper.getMainLooper() == Looper.myLooper()) {
            mToastMaster.updateTips(prompt).launch();
        } else {
            Looper.prepare();
            mToastMaster.updateTips(prompt).launch();
            Looper.loop();
        }
    }

    protected abstract void initParameters();

}
