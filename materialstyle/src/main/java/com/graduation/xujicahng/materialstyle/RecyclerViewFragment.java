package com.graduation.xujicahng.materialstyle;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.annotation.UiThread;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 项目名称：MaterialDesignLearn
 * 类描述：XXXX.java是趣猜APP中XXXXXXX的类
 * 创建人：Administrator
 * 创建时间：2016/7/13 16:35
 * 修改人：Administrator
 * 修改时间：2016/7/13 16:35
 * 修改备注：
 */
public class RecyclerViewFragment extends Fragment implements Handler.Callback {

    private RecyclerView recyclerView;
    private SwipeRefreshLayout refreshLayout;
    private List<String> sourceList;
    private customLinearLayoutManager linearLayoutManager;
    private customGridLayoutManager gridLayoutManager;
    private customStaggeredGridLayoutManager staggeredGridLayoutManager;
    private myRecyclerViewAdapter adapter;
    private Handler handler;

    @Override

    public void onCreate(@Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
        createSources();
        createLayoutManager();
        handler = new Handler(this);
    }

    private void createLayoutManager() {
        linearLayoutManager = new customLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        gridLayoutManager = new customGridLayoutManager(getContext(), 2);
        staggeredGridLayoutManager = new customStaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
    }

    private void createSources() {
        if (sourceList == null) {
            sourceList = new ArrayList<>();
        }
        for (int i = 0; i < 20; i++) {
            sourceList.add(String.valueOf(i));
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_widget, null);
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        refreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefresh);
        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                            handler.obtainMessage().sendToTarget();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
        adapter = new myRecyclerViewAdapter(sourceList, recyclerView);
        recyclerView.setAdapter(adapter);
        return view;
    }

    private void addSources() {
        int start = sourceList.size();
        int end = start + 10;
        for (int i = start; i < end; i++) {
            sourceList.add(String.valueOf(i));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.recylerview_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        boolean asume;
        switch (item.getItemId()) {
            case R.id.layout_manager_GridLayoutManager:
                recyclerView.setLayoutManager(gridLayoutManager);
                adapter.notifyDataSetChanged();
                asume = true;
                break;
            case R.id.layout_manager_LinearLayoutManager:
                recyclerView.setLayoutManager(linearLayoutManager);
                adapter.notifyDataSetChanged();
                asume = true;
                break;
            case R.id.layout_manager_StaggeredGridLayoutManager:
                recyclerView.setLayoutManager(staggeredGridLayoutManager);
                adapter.notifyDataSetChanged();
                asume = true;
                break;
            default:
                return false;
        }
        return asume;
    }

    @Override
    public boolean handleMessage(Message msg) {
        addSources();
        refreshLayout.setRefreshing(false);
        adapter.notifyDataSetChanged();
        return false;
    }

    private class myRecyclerViewAdapter extends RecyclerView.Adapter<viewHolder> {
        private List<String> stringList;
        private RecyclerView recyclerView;

        public boolean isHeightRandom() {
            return isHeightRandom;
        }

        public void setHeightRandom(boolean heightRandom) {
            isHeightRandom = heightRandom;
        }

        private boolean isHeightRandom;

        public myRecyclerViewAdapter(List<String> stringList, RecyclerView recyclerView) {
            this.stringList = stringList;
            this.recyclerView = recyclerView;
        }

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_item, null);
            ViewGroup.LayoutParams params;
            if (recyclerView.getLayoutManager() instanceof StaggeredGridLayoutManager) {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, randHeight());
            } else {
                params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 300);
            }
            view.setLayoutParams(params);
            return new viewHolder(view);
        }

        private int randHeight() {
            return (int) (Math.random() * 200 + 200);
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            holder.textView.setText(stringList.get(position));
        }

        @Override
        public int getItemCount() {
            return stringList.size();
        }

        @Override
        public void onAttachedToRecyclerView(RecyclerView recyclerView) {
            super.onAttachedToRecyclerView(recyclerView);
        }

        @Override
        public void onViewAttachedToWindow(viewHolder holder) {
            ItemShowAnimation(holder.bgView);
            super.onViewAttachedToWindow(holder);
        }
    }

    private class viewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private CardView bgView;

        public viewHolder(View itemView) {
            super(itemView);
            textView = (TextView) itemView.findViewById(R.id.text);
            bgView = (CardView) itemView.findViewById(R.id.bg_card);
        }
    }

    private class customLinearLayoutManager extends LinearLayoutManager {

        public customLinearLayoutManager(Context context) {
            super(context);
        }

        public customLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
            super(context, orientation, reverseLayout);
        }

        public customLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }
    }

    private class customGridLayoutManager extends GridLayoutManager {

        public customGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public customGridLayoutManager(Context context, int spanCount) {
            super(context, spanCount);
        }

        public customGridLayoutManager(Context context, int spanCount, int orientation, boolean reverseLayout) {
            super(context, spanCount, orientation, reverseLayout);
        }
    }

    private class customStaggeredGridLayoutManager extends StaggeredGridLayoutManager {

        public customStaggeredGridLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
            super(context, attrs, defStyleAttr, defStyleRes);
        }

        public customStaggeredGridLayoutManager(int spanCount, int orientation) {
            super(spanCount, orientation);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void ItemShowAnimation(View view) {
        Animator animator = null;
        animator = ViewAnimationUtils.createCircularReveal(
                view,
                view.getWidth() / 2, view.getHeight() / 2,
                0, (float) (Math.hypot(view.getWidth() / 2, view.getHeight() / 2) / 2));
        animator.setInterpolator(new AccelerateInterpolator());
        animator.setDuration(200);
        animator.start();
    }
}
