package com.graduation.xujicahng.materialstyle;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.animation.StateListAnimator;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.graphics.Path;
import android.graphics.drawable.Animatable;
import android.graphics.drawable.AnimatedStateListDrawable;
import android.graphics.drawable.AnimatedVectorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.graphics.drawable.VectorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.Fragment;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.view.animation.PathInterpolator;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
    private ImageView vectorImageTriangle;
    private ImageView vectorImageRect;
    private Interpolator[] interpolator;
    private Path path;
    private LinearLayout ball_container;
    private ObjectAnimator mAnimator;
    private Button preBtn;
    private Button nextbtn;
    private int inerpolatorId = 0;

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
        vectorImageTriangle = (ImageView) view.findViewById(R.id.vector_image_triangle);
        ball_container = (LinearLayout) view.findViewById(R.id.ball_container);
        preBtn = (Button) view.findViewById(R.id.pre_bt);
        nextbtn = (Button) view.findViewById(R.id.next_bt);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            interpolator = new Interpolator[]{
                    AnimationUtils.loadInterpolator(getActivity(),
                            android.R.interpolator.linear),
                    AnimationUtils.loadInterpolator(getActivity(),
                            android.R.interpolator.fast_out_linear_in),
                    AnimationUtils.loadInterpolator(getActivity(),
                            android.R.interpolator.fast_out_slow_in),
                    AnimationUtils.loadInterpolator(getActivity(),
                            android.R.interpolator.linear_out_slow_in)
            };
        }

//        vectorImageRect = (ImageView) view.findViewById(R.id.vector_image_rect);
        slideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getActivity(), ActivitySlide.class);
                    //appBarLayout:需要共享的元素控件引用，
                    //"AppBarLayout":需要共享元素的控件指定的 TransitionName属性指定的字符串值
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), appBarLayout, "AppBarLayout");
                    startActivity(intent, options.toBundle());
                }
            }
        });
        fadeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Pair<View, String> pair1 = new Pair<View, String>(appBarLayout, "AppBarLayout");
                    Pair<View, String> pair2 = new Pair<View, String>(appBarLayout, "AppBarLayout");
                    Intent intent = new Intent(getActivity(), ActivityFade.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), pair1, pair2);
                    startActivity(intent, options.toBundle());
                }
            }
        });
        explodeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(getActivity(), ActivityExplode.class);
                    startActivity(intent, ActivityOptions.makeSceneTransitionAnimation(getActivity()).toBundle());
//                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(getActivity(), appBarLayout, "AppBarLayout");
//                    startActivity(intent, options.toBundle());
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
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    startAnimation(v, flag, v.getWidth() / 2);
                }
            }
        });
        rectView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
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
        vectorImageTriangle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    AnimatedVectorDrawable animatedVectorDrawable = (AnimatedVectorDrawable) vectorImageTriangle.getDrawable();
                    animatedVectorDrawable.start();
                }
            }
        });
        preBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInterploator(-1);
            }
        });
//        VectorDrawableCompat vectorDrawableCompat = VectorDrawableCompat.create(getResources(), R.drawable.vector_animator, null);
//        ImageView imageView = new ImageView(getActivity());
//        imageView.setImageDrawable(vectorDrawableCompat);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changeInterploator(1);
            }
        });
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

//            StateListDrawable stateListDrawable = getResources().getS
            StateListAnimator stateListAnimator = AnimatorInflater.loadStateListAnimator(getContext(), R.drawable.state_change_selector);
            nextbtn.setStateListAnimator(stateListAnimator);
        }
        return view;
    }

    private void changeInterploator(int i) {
        inerpolatorId += i;
        if (inerpolatorId >= interpolator.length) {
            inerpolatorId = 0;
        }
        if (inerpolatorId < 0) {
            inerpolatorId = interpolator.length - 1;
        }
    }

    private void startBalLAnimation(View v) {
        if (path == null) {
            path = new Path();
            int witdh = v.getWidth();
            path.moveTo(v.getLeft(), v.getTop());
            path.lineTo(v.getLeft(), ball_container.getHeight() - witdh);
            path.lineTo(ball_container.getWidth() - witdh, ball_container.getHeight() - witdh);
            path.lineTo(ball_container.getWidth() - witdh, ball.getTop());
            path.lineTo(v.getLeft(), v.getTop());
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
////            PathInterpolator pathInterpolator = new PathInterpolator(path);
//            v.animate().setDuration(2000).translationX(400).translationY(400).setInterpolator(interpolator[1]).setUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
//                @Override
//                public void onAnimationUpdate(ValueAnimator animation) {
//                }
//            }).start();
            Interpolator innerInterpolator = interpolator[inerpolatorId];
            mAnimator = ObjectAnimator.ofFloat(v, View.X, View.Y, path);
            mAnimator.setDuration(3000);
            mAnimator.setInterpolator(innerInterpolator);
            mAnimator.start();
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void startAnimation(View v, boolean flag, float radius) {
        Animator animator = null;
        animator = ViewAnimationUtils.createCircularReveal(
                v,                  // view 操作的视图
                v.getWidth() / 2,   // centerX 动画开始的中心点X
                v.getHeight() / 2,  // centerY 动画开始的中心点Y
                flag ? 0 : radius,  // startRadius 动画开始半径
                flag ? radius : 0); // startRadius 动画结束半径
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(500);
        animator.start();
    }
}
