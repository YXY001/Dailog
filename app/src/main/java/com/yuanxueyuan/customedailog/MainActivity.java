package com.yuanxueyuan.customedailog;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.yanzhenjie.nohttp.Logger;
import com.yanzhenjie.nohttp.NoHttp;
import com.yanzhenjie.nohttp.URLConnectionNetworkExecutor;
import com.yanzhenjie.nohttp.cache.DBCacheStore;
import com.yanzhenjie.nohttp.cookie.DBCookieStore;
import com.yanzhenjie.nohttp.rest.OnResponseListener;
import com.yanzhenjie.nohttp.rest.Request;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.StringRequest;
import com.yuanxueyuan.customeditext.View.CustomEditText;
import com.yuanxueyuan.everytypedialog.CommomDialog;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity implements View.OnClickListener,OnResponseListener {

    private CustomEditText edt;
    private TextView text;
    Timer timer = new Timer();
    Timer timer1 = new Timer();

    private int  successCount=0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。
        initNoHttp();
        edt = findViewById(R.id.edt);
        edt.setEmojiEnable(false, 100);
        edt.showCleanImg(true);
        edt.setPassword(true);
        edt.showMessage("111111");
        text = findViewById(R.id.text);
        text.setOnClickListener(this);
    }


    private void initNoHttp() {
        Logger.setDebug(true); // 开启NoHttp调试模式。
        Logger.setTag("NoHttpSample"); // 设置NoHttp打印Log的TAG。

        NoHttp.initialize(this, new NoHttp.Config()
                .setConnectTimeout(10 * 1000) // 全局连接超时时间，单位毫秒。
                .setReadTimeout(10 * 1000) // 全局服务器响应超时时间，单位毫秒。
                .setCacheStore(
                        new DBCacheStore(this) // 配置缓存到数据库。
                                .setEnable(false)// true启用缓存，fasle禁用缓存。
                )
                .setCookieStore(
                        new DBCookieStore(this)
                                .setEnable(false) // true启用自动维护Cookie，fasle禁用自动维护Cookie。
                )
                .setNetworkExecutor(new URLConnectionNetworkExecutor()) // 使用HttpURLConnection做网络层。
        );
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.text:
                CommomDialog commomDialog = new CommomDialog(this);
                commomDialog.show();


                break;
            default:
                break;
        }
    }

    @Override
    public void onStart(int i) {

    }

    @Override
    public void onSucceed(int i, Response response) {
        Log.i("QQQQQQQQQQQ","onSucceed"+response.get());
        String result = (String) response.get();
        JSONObject jsonResult = null;
        try {
            jsonResult = new JSONObject(result);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        if (jsonResult.optInt("ret") != 0) {
            successCount++;
            text.setText("失败次数"+successCount);
        }
    }

    @Override
    public void onFailed(int i, Response response) {
        Log.i("QQQQQQQQQQQ","onFailed");
    }

    @Override
    public void onFinish(int i) {

    }
}
