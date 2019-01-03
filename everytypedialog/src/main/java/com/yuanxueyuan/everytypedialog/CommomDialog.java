package com.yuanxueyuan.everytypedialog;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.StyleRes;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

public class CommomDialog extends Dialog implements View.OnClickListener {

    /**********控件*********/
    private Context mContext;
    private TextView titleTextView, contentTextView, leftTextView, rightTextView, inputTitleView, inputContentTextView, inputLeftTextView, inputRightTextView;
    private LinearLayout titleLL, inputTitleLL;
    private OnCommonListener onCommonListener;
    private EditText editText;


    /**********常量*********/
    private static final String LOG_TAG = "CommomDialog";


    /**********变量*********/
    private String strTitle, strContent, strRight, strLeft;
    private int DAILOG_TYEP = APPConstantValue.DAILOG_COMMON;


    /**********重构方法*********/

    /**
     * @param context 上下文
     * @author yuanxueyuan
     * @Title: CommomDialog
     * @Description: 弹框，寻常的
     * @date 2018/11/28 15:53
     */
    public CommomDialog(@NonNull Context context) {
        super(context);
        this.mContext = context;
    }


    /**
     * @param context    上下文
     * @param themeResId 主题
     * @author yuanxueyuan
     * @Title: CommomDialog
     * @Description: 弹框，支持设置主题
     * @date 2018/11/28 15:43
     */
    public CommomDialog(@NonNull Context context, @StyleRes int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    /**
     * @param context          上下文
     * @param onCommonListener 点击事件的监听
     * @author yuanxueyuan
     * @Title: CommomDialog
     * @Description: 弹框，只是对于点击事件的监听
     * @date 2018/11/30 9:58
     */
    public CommomDialog(@NonNull Context context, @NonNull OnCommonListener onCommonListener) {
        super(context);
        this.mContext = context;
        this.onCommonListener = onCommonListener;
    }

    /**
     * @param context 上下文
     * @author yuanxueyuan
     * @Title: CommomDialog
     * @Description: 弹框，用户可以指定弹框的类型
     * @date 2018/11/30 11:30
     */
    public CommomDialog(@NonNull Context context, int type, @NonNull OnCommonListener onCommonListener) {
        super(context);
        this.mContext = context;
        DAILOG_TYEP = type;
    }


    /**********控制弹框的方法，下面的方法的优先级比重构中的方法的高*********/

    /**
     * @param titleText 弹框标题文字
     * @author yuanxueyuan
     * @Title: setTitle
     * @Description: 设置标题文字
     * @date 2018/11/28 16:05
     */
    public void setTitle(@NonNull String titleText) {
        strTitle = titleText;
    }

    /**
     * @param content 弹框内容
     * @author yuanxueyuan
     * @Title: setContent
     * @Description: 设置弹框的内容文字
     * @date 2018/11/28 16:06
     */
    public void setContent(@NonNull String content) {
        strContent = content;
    }

    /**
     * @param sureText 弹框中确定的文字
     * @author yuanxueyuan
     * @Title: setSureText
     * @Description: 设置弹框的确定文字
     * @date 2018/11/28 16:07
     */
    public void setSureText(@NonNull String sureText) {
        strRight = sureText;
    }

    /**
     * @param cancelText 弹框的取消文字
     * @author yuanxueyuan
     * @Title: setCancelText
     * @Description: 设置弹框的取消文字
     * @date 2018/11/28 16:08
     */
    public void setCancelText(@NonNull String cancelText) {
        strLeft = cancelText;
    }

    /**
     * @param visible 是否可见变量
     * @author yuanxueyuan
     * @Title: setTitleVisible
     * @Description: 设置标题是否可见
     * @date 2018/11/29 17:09
     */
    public void setTitleVisible(boolean visible) {
        if (titleLL == null) {
            Log.e(LOG_TAG, "need init view first");
            return;
        }
        if (visible) {
            titleLL.setVisibility(View.VISIBLE);
        } else {
            titleLL.setVisibility(View.GONE);
        }
    }

    /**
     * @param visible 是否可见变量
     * @author yuanxueyuan
     * @Title: setContentVisible
     * @Description: 设置内容是否可见
     * @date 2018/11/29 17:13
     */
    public void setContentVisible(boolean visible) {
        if (contentTextView == null) {
            Log.e(LOG_TAG, "need init view first");
            return;
        }
        if (visible) {
            contentTextView.setVisibility(View.VISIBLE);
        } else {
            contentTextView.setVisibility(View.GONE);
        }
    }

    /**
     * @param visible 是否可见变量
     * @author yuanxueyuan
     * @Title: setLeftVisible
     * @Description: 设置左边的按钮显隐
     * @date 2018/11/29 17:15
     */
    public void setLeftVisible(boolean visible) {
        if (leftTextView == null) {
            Log.e(LOG_TAG, "need init view first");
            return;
        }
        if (visible) {
            leftTextView.setVisibility(View.VISIBLE);
        } else {
            leftTextView.setVisibility(View.GONE);
        }
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //根据不同的需求加载不同的布局
        switch (DAILOG_TYEP) {
            //普通的弹框
            case APPConstantValue.DAILOG_COMMON:
                initCommonDialog();
                initCommonValues();
                initCommonListener();
                break;
            //提示弹框
            case APPConstantValue.DAILOG_TIP:
                initCommonDialog();
                initCommonValues();
                initCommonListener();
                setLeftVisible(false);
                break;
            //带有输入框的弹框
            case APPConstantValue.DAILOG_EDIT:
                break;

            default:
                break;
        }

    }


    /**********初始化控件*********/

    /**
     * @author yuanxueyuan
     * @Title: initCommenDialog
     * @Description: 初始化普通的弹框
     * @date 2018/11/29 16:58
     */
    private void initCommonDialog() {
        setContentView(R.layout.dialog_common);
        titleTextView = findViewById(R.id.text_common_title);
        titleLL = findViewById(R.id.ll_common_title);
        contentTextView = findViewById(R.id.text_common_content);
        leftTextView = findViewById(R.id.text_common_left);
        rightTextView = findViewById(R.id.text_common_right);
    }

    private void initInputDialog(){
        setContentView(R.layout.dialog_input);
        inputTitleView = findViewById(R.id.text_input_title);
        inputTitleLL = findViewById(R.id.ll_input_title);
//        inputContentTextView = findViewById(R.id.text_common_content);
//        leftTextView = findViewById(R.id.text_common_left);
//        rightTextView = findViewById(R.id.text_common_right);
    }


    /**********初始化数据*********/

    /**
     * @author yuanxueyuan
     * @Title: initCommonValues
     * @Description: 初始化普通的弹框数据
     * @date 2018/11/30 11:34
     */
    private void initCommonValues() {
        //设置标题
        if (titleTextView != null && titleTextView.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(strTitle)) {
            titleTextView.setText(strTitle);
        }
        if (contentTextView != null && contentTextView.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(strContent)) {
            contentTextView.setText(strContent);
        }
        if (leftTextView != null && leftTextView.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(strLeft)) {
            leftTextView.setText(strContent);
        }
        if (rightTextView != null && rightTextView.getVisibility() == View.VISIBLE && !TextUtils.isEmpty(strRight)) {
            rightTextView.setText(strContent);
        }
    }


    /**********初始化监听*********/
    /**
     * @author yuanxueyuan
     * @Title: initCommonListener
     * @Description: 初始化监听事件
     * @date 2018/11/30 11:34
     */
    private void initCommonListener() {
        if (leftTextView != null && leftTextView.getVisibility() == View.VISIBLE) {
            leftTextView.setOnClickListener(this);
        }
        if (rightTextView != null && rightTextView.getVisibility() == View.VISIBLE) {
            rightTextView.setOnClickListener(this);
        }
    }

    /**
     * @author yuanxueyuan
     * @Title: closeSoftKeyBoard
     * @Description: 关掉键盘
     * @date 2018/9/8 14:24
     */
    private void closeSoftKeyBoard(Context context, View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) context
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.text_common_left) {
            //普通弹框的左边按钮点击事件
            if (leftTextView.getVisibility() == View.VISIBLE) {
                if (onCommonListener != null) {
                    onCommonListener.onClickCommonLeft();
                }
                this.dismiss();
            }
        } else if (id == R.id.text_common_right) {
            //普通弹框的右边按钮点击事件
            if (onCommonListener != null) {
                onCommonListener.onClickCommonRight();
            }
            this.dismiss();
        }
    }


    /*************代理事件*****************/

    /**
     * @author yuanxueyuan
     * @Title: OnCommonListener
     * @Description: 普通弹框的点击事件
     * @date 2018/11/29  17:37
     */
    public interface OnCommonListener {
        void onClickCommonLeft();

        void onClickCommonRight();
    }

}