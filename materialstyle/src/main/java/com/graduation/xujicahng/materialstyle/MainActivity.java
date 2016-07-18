package com.graduation.xujicahng.materialstyle;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.transition.TransitionValues;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MainActivity extends AppCompatActivity {
    private Fragment mainFragment, animation, widgetFragment, floatingActionBarFragment;
    private DrawerLayout drawerlayout;
    private MenuItem currentMenuItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        int themeFlag = getIntent().getIntExtra("themeFlag", 0);
        setTheme(getThemeByFlag(themeFlag));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            // 允许使用transitions
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);

            //设置进入的动画
            getWindow().setEnterTransition(new Fade());
            // 设置一个退出动画
            getWindow().setExitTransition(new Fade());

            //设置共享元素进入的动画
            getWindow().setSharedElementEnterTransition(new Fade());
            //设置共享元素的退出动画
            getWindow().setSharedElementExitTransition(new Fade());
        }
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        drawerlayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerlayout, toolbar, R.string.string_open, R.string.string_close) {
            @Override
            public void onDrawerClosed(View drawerView) {
                if (currentMenuItem != null) {
                    changeFragment(currentMenuItem);
                }
                super.onDrawerClosed(drawerView);
            }
        };
        if (drawerlayout != null) {
            drawerlayout.addDrawerListener(toggle);
        }
        toggle.syncState();
        //解析NavigationView,获取引用
        NavigationView navigationView = (NavigationView) findViewById(R.id.design_navigation_view);
        if (navigationView != null) {
            //设置MenuItem的点击回调
            navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                @Override
                public boolean onNavigationItemSelected(MenuItem item) {
                    currentMenuItem = item;
                    drawerlayout.closeDrawers();
                    return true;
                }
            });
        }
        //默认显示主Fragament
        if (savedInstanceState == null) {
            mainFragment = new MainFragment();
            getSupportFragmentManager().beginTransaction().add(R.id.main_fragment, mainFragment).commit();
        }
    }

    private void changeFragment(MenuItem Item) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        switch (Item.getItemId()) {
            case R.id.menu_style:
                if (mainFragment == null) {
                    mainFragment = new MainFragment();
                }
                fragmentTransaction.replace(R.id.main_fragment, mainFragment);
                break;
            case R.id.menu_animation:
                if (animation == null) {
                    animation = new AnimationFragment();
                }
                fragmentTransaction.replace(R.id.main_fragment, animation);
                break;
            case R.id.menu_widget_recycler:
                if (widgetFragment == null) {
                    widgetFragment = new RecyclerViewFragment();
                }
                fragmentTransaction.replace(R.id.main_fragment, widgetFragment);
                break;
            case R.id.menu_widget_floating_Action_bar:
                if (floatingActionBarFragment == null) {
                    floatingActionBarFragment = new FloatingActionBarFragment();
                }
                fragmentTransaction.replace(R.id.main_fragment, floatingActionBarFragment);
                break;
            default:
                break;
        }
        setTitle(Item.getTitle());
        fragmentTransaction.commitAllowingStateLoss();
    }


    private int getThemeByFlag(int themeFlag) {
        int resourceID;
        switch (themeFlag) {
            case 1:
                resourceID = R.style.AppTheme_Blue;
                break;
            case 2:
                resourceID = R.style.AppTheme_Gray;
                break;
            case 3:
                resourceID = R.style.AppTheme_Yellow;
                break;
            case 4:
                resourceID = R.style.AppTheme_Green;
                break;
            default:
                resourceID = R.style.AppTheme;
                break;
        }
        return resourceID;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
//        if (this instanceof FragmentActivity) {
//            setFragmentActivityMenuColor(this);
//        } else {
//            getLayoutInflater().setFactory(new LayoutInflater.Factory() {
//                                               @Override
//                                               public View onCreateView(String name, Context context, AttributeSet attrs) {
//                                                   try {
//                                                       if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
//                                                               || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
//                                                           LayoutInflater layoutInflater = getLayoutInflater();
//                                                           View view = null;
//
//                                                           view = layoutInflater.createView(name, null, attrs);
//
//                                                           if (view instanceof TextView) {
//                                                               ((TextView) view).setTextColor(Color.WHITE);
//                                                           }
//                                                           return view;
//                                                       }
//                                                   } catch (ClassNotFoundException e) {
//                                                       e.printStackTrace();
//                                                   }
//                                                   return null;
//                                               }
//                                           }
//
//            );
//        }
        inflater.inflate(R.menu.custom_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean consume = false;
        int flag = 0;
        switch (item.getItemId()) {
            case android.R.id.home:
                consume = true;
                break;
            case R.id.theme_default:
                consume = true;
                flag = 0;
                break;
            case R.id.theme_Blue:
                consume = true;
                flag = 1;
                break;
            case R.id.theme_Gray:
                consume = true;
                flag = 2;
                break;
            case R.id.theme_Green:
                consume = true;
                flag = 4;
                break;
            case R.id.theme_Yellow:
                consume = true;
                flag = 3;
                break;
            default:
                consume = false;
                break;
        }
        if (consume) {
            changeTheme(flag);
        }
        return consume;
    }

    private void changeTheme(int flag) {
        Intent intent = getIntent();
        intent.putExtra("themeFlag", flag);
        overridePendingTransition(0, 0);//不设置进入退出动画
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        finish();
        overridePendingTransition(0, 0);
        startActivity(intent);

    }

    private void setFragmentActivityMenuColor(FragmentActivity context) {
        final LayoutInflater layoutInflater = context.getLayoutInflater();
        final LayoutInflater.Factory existingFactory = layoutInflater.getFactory();
        try {

            Field field = LayoutInflater.class.getDeclaredField("mFactorySet");
            field.setAccessible(true);
            field.setBoolean(layoutInflater, false);
            context.getLayoutInflater().setFactory(new LayoutInflater.Factory() {
                @Override
                public View onCreateView(String name, final Context context, AttributeSet attrs) {
                    if (name.equalsIgnoreCase("com.android.internal.view.menu.IconMenuItemView")
                            || name.equalsIgnoreCase("com.android.internal.view.menu.ActionMenuItemView")) {
                        View view = null;
                        // if a factory was already set, we use the returned view
                        if (existingFactory != null) {
                            view = existingFactory.onCreateView(name, context, attrs);
                            if (view == null) {
                                try {
                                    view = layoutInflater.createView(name, null, attrs);
                                    final View finalView = view;
                                    if (view instanceof TextView) {
                                        new Handler().post(new Runnable() {
                                            public void run() {
                                                ((TextView) finalView).setTextColor(Color.WHITE);
                                            }
                                        });
                                    }
                                    return finalView;
                                } catch (ClassNotFoundException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                        return view;
                    }
                    return null;
                }
            });
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private class myFade extends Fade{
        @Override
        public Animator onDisappear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
            return super.onDisappear(sceneRoot, view, startValues, endValues);
        }

        public myFade() {
            super();
        }

        public myFade(int fadingMode) {
            super(fadingMode);
        }

        public myFade(Context context, AttributeSet attrs) {
            super(context, attrs);
        }

        @Override
        public Animator onAppear(ViewGroup sceneRoot, View view, TransitionValues startValues, TransitionValues endValues) {
            return super.onAppear(sceneRoot, view, startValues, endValues);
        }

    }
}
