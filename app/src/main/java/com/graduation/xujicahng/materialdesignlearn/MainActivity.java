package com.graduation.xujicahng.materialdesignlearn;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.NavigationView;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;

public class MainActivity extends AppCompatActivity {

    private AppBarLayout appBarLayout;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setExitTransition(new Slide());
            getWindow().setSharedElementExitTransition(new Slide());
        }
        setContentView(R.layout.activity_main);
        drawerLayout = (DrawerLayout) findViewById(R.id.main_drawer);
        NavigationView navigationView = (NavigationView) findViewById(R.id.design_navigation_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        appBarLayout = (AppBarLayout) findViewById(R.id.AppBarLayout);
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);
        ab.setTitle("");

        final View rect = findViewById(R.id.view_animation_rect);
        final View oval = findViewById(R.id.view_animation_oval);
        rect.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                /**view 操作的视图
                 centerX 动画开始的中心点X
                 centerY 动画开始的中心点Y
                 startRadius 动画开始半径
                 startRadius 动画结束半径
                 **/
                Animator animator = ViewAnimationUtils.createCircularReveal(rect, 0, 0, 0, (float) Math.hypot(rect.getWidth(), rect.getHeight()));
                animator.setInterpolator(new AccelerateInterpolator());
                animator.setDuration(1000);
                animator.start();
            }
        });
        oval.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View v) {
                Animator animator = ViewAnimationUtils.createCircularReveal(oval, oval.getWidth() / 2, oval.getHeight() / 2, 0, oval.getWidth() / 2);
                animator.setInterpolator(new AccelerateDecelerateInterpolator());
                animator.setDuration(1000);
                animator.start();
            }
        });
    }

    /**
     * 普通Transition：
     * explode：从场景的中心移入或移出
     * slide：从场景的边缘移入或移出
     * fade：调整透明度产生渐变效果
     * Shared Elements Transition 共享元素转换：
     * 它的作用就是共享两个acitivity种共同的元素，在Android 5.0下支持如下效果：
     * changeBounds -  改变目标视图的布局边界
     * changeClipBounds - 裁剪目标视图边界
     * changeTransform - 改变目标视图的缩放比例和旋转角度
     * changeImageTransform - 改变目标图片的大小和缩放比例
     **/
    public void startFirstActivity(View view) {
        Intent intent = new Intent(this, ActivityFirst.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void startSecondActivity(View view) {
        Intent intent = new Intent(this, ActivitySecond.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void startThirdActivity(View view) {
        Intent intent = new Intent(this, ActivityThird.class);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        } else {
            startActivity(intent);
        }
    }

    public void startForthActivity(View view) {
        Intent intent = new Intent(this, ActivityForth.class);
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(this, appBarLayout, "AppBarLayout");
            startActivity(intent, options.toBundle());
        } else {
            startActivity(intent);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
                return true;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
