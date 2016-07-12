package com.graduation.xujicahng.materialdesignlearn;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputLayout;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Fade;
import android.transition.Slide;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * XXXX.java是趣猜APP的XXXX类
 *
 * @author xujichang
 * @version 2016/6/24.
 */
public class ActivityFirst extends Activity {
    private TextInputLayout nameInputLayout;
    private TextInputLayout pwdInputLayout;
    private Button snackBar;
    private Button snackBarWithAction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Slide());
            getWindow().setSharedElementEnterTransition(new Fade());
        }
        setContentView(R.layout.layout_activity_first);

        nameInputLayout = (TextInputLayout) findViewById(R.id.name_input_layout);
        pwdInputLayout = (TextInputLayout) findViewById(R.id.pwd_input_layout);
        TextView login = (TextView) findViewById(R.id.login);
        snackBar = (Button) findViewById(R.id.snack_bar);
        snackBarWithAction = (Button) findViewById(R.id.snack_bar_with_action);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkLogin();
            }
        });
        nameInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdInputLayout.getEditText().addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        snackBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar snackbar = Snackbar.make(getContentView(), "This is Snack without Action", Snackbar.LENGTH_SHORT);
                Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();
                snackbarLayout.setAlpha(0.5f);
                //设置位置
                ViewGroup.LayoutParams params = snackbarLayout.getLayoutParams();
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(params.width, params.height);
                layoutParams.gravity = Gravity.CENTER_HORIZONTAL;
                snackbarLayout.setLayoutParams(layoutParams);
                //设置动画
//                snackbarLayout.setAnimation();
                snackbar.show();
            }
        });
        snackBarWithAction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(getContentView(), "This is Snack with Action", Snackbar.LENGTH_INDEFINITE).setAction("action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ActivityFirst.this, "This is Toast", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
    }

    private View getContentView() {

        ViewGroup viewGroup = (ViewGroup) getWindow().getDecorView();
        LinearLayout content = (LinearLayout) viewGroup.getChildAt(0);
        return content.getChildAt(0);
    }

    public void checkLogin() {
        String name = nameInputLayout.getEditText().getText().toString();
        String pwd = pwdInputLayout.getEditText().getText().toString();
        if (!name.equals("xjc")) {
            nameInputLayout.setError("用户名错误");
            nameInputLayout.setErrorEnabled(true);


        } else if (!pwd.equals("123")) {
            pwdInputLayout.setError("密码错误");
            pwdInputLayout.setErrorEnabled(true);


        } else {
            pwdInputLayout.setErrorEnabled(false);
            nameInputLayout.setErrorEnabled(false);
        }
    }
}
