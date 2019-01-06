package com.yuanxueyuan.customeditext.Utils;

import android.content.Context;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.yuanxueyuan.customeditext.R;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.CountDownLatch;

/**
 * @author yuanxueyuan
 * @Title: ShowMessage
 * @Description: 显示最新的提示信息
 * @date 2018/12/14  14:02
 */
public class ShowMessage extends LinearLayout {

    private final String TAG_LOG = "ShowMessage";
    private Context context;
    private View view;

    private LinearLayout messageLL;
    private ImageView messageImg;
    private TextView messageText;
    private EditText editText;
    private Handler myHandler;
    private Runnable runnable;
    private boolean isShake;//是否正在抖动

    public ShowMessage(Context context) {
        super(context);
        myHandler = new Handler();
        this.context = context;
        runnable = new Runnable() {
            @Override
            public void run() {
                if (editText != null) {
                    isShake = false;
                    editText.setVisibility(VISIBLE);
                }
            }
        };
    }

    /**
     * @param view 界面
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化布局
     * @date 2018/12/14 14:11
     */
    private void initView(@NonNull View view) {
        if (view == null) {
            Log.e(TAG_LOG,"view == null");
            return;
        }
        messageLL = (LinearLayout) view.findViewById(R.id.ll_message);
        messageImg = (ImageView) view.findViewById(R.id.img_message);
        messageText = (TextView) view.findViewById(R.id.text_message);
    }

    /**
     * @param text 显示的文字
     * @author yuanxueyuan
     * @Title: setMessageText
     * @Description: 要显示的文字
     * @date 2018/12/14 14:14
     */
    public void setMessageText(@NonNull String text) {
        if (TextUtils.isEmpty(text)) {
            Log.e(TAG_LOG,"TextUtils.isEmpty(text)");
            return;
        }
        if (messageText == null) {
            Log.e(TAG_LOG,"messageText == null");
            return;
        }

        messageText.setText(text);
        //TODO 没有进行错误新和编辑框长度的比较
    }


    /**
     * @param icon 图标地址
     * @author yuanxueyuan
     * @Title: setMessageIcon
     * @Description: 设置信息的前方图标
     * @date 2019/1/5 18:24
     */
    public void setMessageIcon(@DrawableRes int icon) {
        if (messageImg == null) {
            Log.e(TAG_LOG, "messageImg == null");
            return;
        }
        messageImg.setImageResource(icon);
    }

    /**
     * @param editText 输入框
     * @author yuanxueyuan
     * @Title: showMessage
     * @Description: 展示提示信息，包括动画
     * @date 2018/12/14 16:30
     */
    public void showMessage(@NonNull final EditText editText) {
        if (editText == null || context == null) {
            Log.e(TAG_LOG,"editText == null");
            return;
        }
        if (myHandler != null) {
            myHandler.removeCallbacks(runnable);
        }
        if (isShake) {
            return;
        }
        this.editText = editText;
        editText.setVisibility(GONE);
        LayoutInflater inflater = LayoutInflater.from(context);
        view = inflater.inflate(R.layout.view_show_message, (ViewGroup) editText.getParent());
        initView(view);
        Animation shake = AnimationUtils.loadAnimation(context, R.anim.shake_left);
        view.startAnimation(shake);
        isShake = true;

        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                myHandler.postDelayed(runnable,500);
            }
            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

}
