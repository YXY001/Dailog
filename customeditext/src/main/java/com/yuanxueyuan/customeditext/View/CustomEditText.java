package com.yuanxueyuan.customeditext.View;

import android.content.Context;
import android.support.annotation.DrawableRes;
import android.support.annotation.NonNull;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.yuanxueyuan.customeditext.R;
import com.yuanxueyuan.customeditext.Utils.CheckInput;
import com.yuanxueyuan.customeditext.Utils.ShowMessage;

/**
 * @author yuanxueyuan
 * @Title: TDEditText
 * @Description: 自定义数据框
 * 1、控制表情图标输入
 * 2、支持一键删除功能
 * @date 2018/11/23  12:01
 */
public class CustomEditText extends LinearLayout implements View.OnClickListener, TextWatcher {

    private final String LOG_TAG = "CustomEditText";
    public Context context;

    private EditText editText;
    private ImageView cleanImg, showImg;
    private boolean show = false;
    private ShowMessage showMessage;


    public CustomEditText(Context context) {
        super(context);
        this.context = context;
    }

    public CustomEditText(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        View view = LayoutInflater.from(context).inflate(R.layout.edit, this);
        initView(view);
        showMessage = new ShowMessage(context);
    }


    /**
     * @param view 视图
     * @author yuanxueyuan
     * @Title: initView
     * @Description: 初始化界面
     * @date 2018/11/27 14:49
     */
    private void initView(View view) {
        editText = view.findViewById(R.id.edit_input);
        cleanImg = view.findViewById(R.id.img_input_clean);
        showImg = view.findViewById(R.id.img_input_show);
        cleanImg.setOnClickListener(this);
        showImg.setOnClickListener(this);
    }


    /**************************************内部调用接口**************************************/


    /**
     * @param show 显示输入框中内容
     * @author yuanxueyuan
     * @Title: updateShowImg
     * @Description: 更新显示的图标
     * @date 2019/1/2 19:22
     */
    private void updateShowImg(boolean show) {
        editText.setTransformationMethod(show ? PasswordTransformationMethod.getInstance() : HideReturnsTransformationMethod.getInstance());
        showImg.setImageResource(show ? R.mipmap.eye_close : R.mipmap.eye_open);
    }


    /**
     * @param show 是否显示
     * @author yuanxueyuan
     * @Title: showShowImg
     * @Description: 显示显影密码的按钮
     * @date 2019/1/3 15:02
     */
    private void showShowImg(boolean show) {
        showImg.setVisibility(show ? VISIBLE : GONE);
    }


    @Override
    public void onClick(View view) {
        int id = view.getId();
        if (id == R.id.img_input_clean) {
            if (editText != null) {
                editText.setText("");
            }
        } else if (id == R.id.img_input_show) {
            if (showImg != null) {
                updateShowImg(show);
                show = !show;
            }
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {
        if (editable == null) {
            Log.e(LOG_TAG, "editable == null");
            return;
        }
        if (editable.length() >= 0) {
            if (editText == null) {
                Log.e(LOG_TAG, "editText == null");
                return;
            }
            if (cleanImg == null) {
                Log.e(LOG_TAG, "cleanImg == null");
                return;
            }
            String str = editText.getText().toString().trim();
            if (TextUtils.isEmpty(str)) {
                cleanImg.setVisibility(GONE);
            } else {
                cleanImg.setVisibility(View.VISIBLE);
            }
        }
    }

    /**************************************对外接口**************************************/
    /**
     * @param show 是否显示清除按钮
     * @author yuanxueyuan
     * @Title: showCleanImg
     * @Description: 输入框周边显示删除按钮, 主要是监听文字输入，然后根据文字输入进行显示清除
     * @date 2018/11/27 14:51
     */
    public void showCleanImg(boolean show) {
        if (show) {
            editText.addTextChangedListener(this);
        }
    }

    /**
     * @param enable 使能 false 表示过滤表情
     * @author yuanxueyuan
     * @Title: emojiEnable
     * @Description: 表情图标使能
     * @date 2018/11/23 12:02
     */
    public void setEmojiEnable(boolean enable) {
        if (!enable) {
            InputFilter inputFilter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    if (CheckInput.isEmoji(charSequence.toString())) {
                        return "";
                    }
                    return null;
                }
            };
            editText.setFilters(new InputFilter[]{inputFilter});
        }
    }


    /**
     * @param enable 使能 false 表示过滤表情
     * @param length 设置输入框最大的长度
     * @author yuanxueyuan
     * @Title: emojiEnable
     * @Description: 表情图标使能
     * @date 2018/11/23 12:02
     */
    public void setEmojiEnable(boolean enable, int length) {
        if (enable) {
            InputFilter inputFilter = new InputFilter() {
                @Override
                public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                    if (CheckInput.isEmoji(charSequence.toString())) {
                        return "";
                    }
                    return null;
                }
            };
            editText.setFilters(new InputFilter[]{inputFilter, new InputFilter.LengthFilter(length)});
        }
    }

    /**
     * @param length 长度
     * @author yuanxueyuan
     * @Title: setInputLength
     * @Description: 设置输入框最大输入长度
     * @date 2019/1/7 10:17
     */
    public void setInputLength(int length) {
        if (editText == null) {
            Log.e(LOG_TAG, "editText == null");
            return;
        }
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(length)});
    }

    /**
     * @author yuanxueyuan
     * @Title: setPhone
     * @Description: 设置成只能输入手机号，并且是11位
     * @date 2019/1/2 18:51
     */
    public void setPhone() {
        editText.setInputType(InputType.TYPE_CLASS_PHONE);
        editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(11)});
    }

    /**
     * @param check 正则表达式
     * @author yuanxueyuan
     * @Title: customInputTye
     * @Description: 自定义可输入内容
     * @date 2019/1/2 18:55
     */
    public void customInputTye(@NonNull final String check) {
        InputFilter inputFilter = new InputFilter() {
            @Override
            public CharSequence filter(CharSequence charSequence, int i, int i1, Spanned spanned, int i2, int i3) {
                if (!charSequence.toString().matches(check)) {
                    return "";
                }
                return null;
            }
        };
        editText.setFilters(new InputFilter[]{inputFilter});
    }

    /**
     * @author yuanxueyuan
     * @Title: setPassword
     * @Description: 设置输入框为密码状态
     * @date 2019/1/2 19:14
     */
    public void setPassword() {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
    }

    /**
     * @author yuanxueyuan
     * @Title: setPassword
     * @Description: 设置输入框为密码状态, 并且可以显示
     * @date 2019/1/2 19:14
     */
    public void setPassword(boolean show) {
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance());
        showShowImg(show);
    }

    /**
     * @param message 错误信息
     * @author yuanxueyuan
     * @Title: showMessage
     * @Description: 显示错误信息
     * @date 2019/1/5 18:19
     */
    public void showMessage(@NonNull String message) {
        if (editText == null) {
            Log.e(LOG_TAG, "editText == null");
            return;
        }
        if (showMessage == null) {
            Log.e(LOG_TAG, "showMessage == null");
            return;
        }
        if (TextUtils.isEmpty(message)) {
            Log.e(LOG_TAG, "message == null");
            return;
        }
        showMessage.showMessage(editText);
        showMessage.setMessageText(message);
    }
    /**
     * @param message 错误信息
     * @author yuanxueyuan
     * @Title: showMessage
     * @Description: 显示错误信息
     * @date 2019/1/5 18:19
     */
    public void showMessage(@NonNull String message, @DrawableRes int icon) {
        if (editText == null) {
            Log.e(LOG_TAG, "editText == null");
            return;
        }
        if (showMessage == null) {
            Log.e(LOG_TAG, "showMessage == null");
            return;
        }
        if (TextUtils.isEmpty(message)) {
            Log.e(LOG_TAG, "message == null");
            return;
        }
        showMessage.showMessage(editText);
        showMessage.setMessageText(message);
        showMessage.setMessageIcon(icon);
    }

    /**
     * @author yuanxueyuan
     * @Title: getText
     * @Description: 获取数据
     * @date 2019/3/11 15:10
     */
    public String getText() {
        if (editText != null) {
            return editText.getText().toString().trim();
        }
        return "";
    }
}
