package com.graduation.xujicahng.materialstyle;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.TextView;

/**
 * 项目名称：MaterialDesignLearn
 * 类描述：XXXX.java是趣猜APP中XXXXXXX的类
 * 创建人：Administrator
 * 创建时间：2016/7/12 16:40
 * 修改人：Administrator
 * 修改时间：2016/7/12 16:40
 * 修改备注：
 */
public class AnimationFragment extends Fragment {
    private TextView ovalView, rectView;
    private Button showBt, disappareBt;
    private boolean flag = false;
    private Button slideBtn, fadeBtn, explodeBtn;
    private AppBarLayout appBarLayout;
    private TextView ball;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_animation, null);

        ovalView = (TextView) view.findViewById(R.id.view_oval);
        rectView = (TextView) view.findViewById(R.id.view_rect);
        showBt = (Button) view.findViewById(R.id.disappear);
        disappareBt = (Button) view.findViewById(R.id.show);

        slideBtn = (Button) view.findViewById(R.id.slide_bt);
        fadeBtn = (Button) view.findViewById(R.id.fade_bt);
        explodeBtn = (Button) view.findViewById(R.id.explode_bt);
        ball = (TextView) view.findViewById(R.id.ball);
        appBarLayout = (AppBarLayout) getActivity().findViewById(R.id.appBarLayout);
        slideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getActivity(), ActivitySlide.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), appBarLayout, "AppBarLayout");
                    startActivity(intent, options.toBundle());
                }
            }
        });
        fadeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getActivity(), ActivityFade.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), appBarLayout, "AppBarLayout");
                    startActivity(intent, options.toBundle());
                }
            }
        });
        explodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getActivity(), ActivityExplode.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), appBarLayout, "AppBarLayout");
                    startActivity(intent, options.toBundle());
                }
            }
        });
        showBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = false;
            }
        });
        disappareBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = true;
            }
        });
        ovalView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    startAnimation(v, flag, v.getWidth() / 2);
                }
            }
        });
        rectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    startAnimation(v, flag, (float) Math.hypot(v.getWidth(), v.getHeight()) / 2);
                }
            }
        });
        ball.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startBalLAnimation(v);
            }
        });

        return view;
    }

    private void startBalLAnimation(View v) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
//            Path path = new Path();
//            // Rect的变量使用int类型，而RectF使用float类型 CCW逆时针 CW顺时针
//            path.addRect(new RectF(0f, 0f, 0.5f, 0.5f), Path.Direction.CCW);
//            PathInterpolator pathInterpolator = new PathInterpolator(path);
////            PathInterpolator pathInterpolator = new PathInterpolator(0.5f, 0.5f);
//            v.animate().setDuration(2000).translationX(400).translationY(400).setInterpolator(pathInterpolator).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                }
//            }).start();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimation(View v, boolean flag, float radius) {
//        view 操作的视图
//        centerX 动画开始的中心点X
//        centerY 动画开始的中心点Y
//        startRadius 动画开始半径
//        startRadius 动画结束半径
        Animator animator = null;
        animator = ViewAnimationUtils.createCircularReveal(
                v,
                v.getWidth() / 2, v.getHeight() / 2,
                flag ? 0 : radius, flag ? radius : 0);
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }
}
