package net.jkcat.jkit.kotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.gson.Gson
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import net.jkcat.jkit.R
import net.jkcat.jkit.bean.ActivityBean
import net.jkcat.jkit.network.ApiService
import net.jkcat.network.BaseObserver

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_request.setOnClickListener {
            getBanner()

//            UpdaterHelper.Builder(this)
//                .url("https://dl.yzcdn.cn/koudaitong.apk")
//                .title("发现新版本")
//                .content("这是更新的内容")
//                .name("测试更新")
//                .description("这是更新标题")
//                .force(false)
//                .create()
//                .launcher()
        }
    }

    private fun getBanner() {
        JNetApi.getService(ApiService::class.java)
            .getActivityInfo()
            .compose(JNetApi.applySchedulers())
            .subscribe(object : BaseObserver<ActivityBean>() {
                override fun onRequestSuccess(result: ActivityBean) {
                    Log.i("net", Gson().toJson(result))
                }

                override fun onRequestFailure(e: Throwable) {
                    Log.i("net", "request error msg is ${e.message}")
                }

                override fun onRequestInit(disposable: Disposable) {

                }
            })
    }

}