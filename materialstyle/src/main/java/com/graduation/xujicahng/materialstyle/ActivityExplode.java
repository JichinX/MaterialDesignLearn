package com.graduation.xujicahng.materialstyle;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Window;
import android.widget.TextView;

/**
 * 项目名称：MaterialDesignLearn
 * 类描述：XXXX.java是趣猜APP中XXXXXXX的类
 * 创建人：Administrator
 * 创建时间：2016/7/12 18:02
 * 修改人：Administrator
 * 修改时间：2016/7/12 18:02
 * 修改备注：
 */
public class ActivityExplode extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 允许使用transitions
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            // 设置一个exit transition
            getWindow().setExitTransition(new Explode());
            //设置进入的 transition
            getWindow().setEnterTransition(new Explode());
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_other);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ((TextView) findViewById(R.id.text)).setText("ActivityExplode");
    }
}
