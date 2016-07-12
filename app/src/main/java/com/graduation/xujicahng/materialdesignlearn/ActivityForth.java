package com.graduation.xujicahng.materialdesignlearn;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.transition.Slide;
import android.view.Window;

/**
 * XXXX.java是趣猜APP的XXXX类
 *
 * @author xujichang
 * @version 2016/6/24.
 */
public class ActivityForth extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Slide());
            getWindow().setSharedElementEnterTransition(new Slide());
        }
        setContentView(R.layout.layout_activity_forth);
    }
}
