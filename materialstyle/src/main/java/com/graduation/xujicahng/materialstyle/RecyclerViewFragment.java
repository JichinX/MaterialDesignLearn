package com.graduation.xujicahng.materialstyle;

import android.animation.Animator;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.AttributeSet;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

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

    private SwipeRefreshLayout refreshLayout;
    private List<String> sourceList;
    private customLinearLayoutManager linearLayoutManager;
    private RecyclerView recyclerView;
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
        linearLayoutManager = new customLinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, true);
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
        //获取RecyclerView控件的引用
        recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        //设置布局管理器
        recyclerView.setLayoutManager(linearLayoutManager);
        //创建Adapter
        adapter = new myRecyclerViewAdapter(sourceList, recyclerView);
        //设置Adapter
        recyclerView.setAdapter(adapter);
        //设置Item添加移除动画
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        //设置分割线
        recyclerView.addItemDecoration(new myItemDecoration());

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
        //注册上下文菜单
//        getActivity().registerForContextMenu(recyclerView);
//        recyclerView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
//            @Override
//            public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
//                //获取打气筒
//                MenuInflater inflater = getActivity().getMenuInflater();
//                //解析菜单文件
//                inflater.inflate(R.menu.context_menu, menu);
//                menu.setHeaderTitle("Item 操作");
//            }
//        });
        registerForContextMenu(recyclerView);
        return view;
    }

    private void addSources() {
        int start = sourceList.size();
        int end = start + 10;
        for (int i = start; i < end; i++) {
            sourceList.add(String.valueOf(i));
        }
        //从指定行插入
        adapter.notifyItemInserted(start);
        adapter.setOnItemClickedListener(new ItemClickListener() {
            @Override
            public void onItemLongClicked(int position) {

            }

            @Override
            public void onItemClicked(int position) {

            }
        });
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
        public void onBindViewHolder(viewHolder holder, final int position) {
            holder.textView.setText(stringList.get(position));
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (null != listener) {
                        listener.onItemClicked(position);
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    if (null != listener) {
                        listener.onItemLongClicked(position);
                    }
                    return false;
                }
            });
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

        @Override
        public void onViewRecycled(viewHolder holder) {
            holder.itemView.setOnCreateContextMenuListener(null);
            super.onViewRecycled(holder);
        }

        private ItemClickListener listener;

        public void setOnItemClickedListener(ItemClickListener listener) {
            this.listener = listener;
        }
    }

    public interface ItemClickListener {
        void onItemLongClicked(int position);

        void onItemClicked(int position);
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

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        boolean consume;
        switch (item.getItemId()) {
            case R.id.menu_item_insert:
                consume = true;
                break;
            case R.id.menu_item_refresh:
                consume = true;
                break;
            case R.id.menu_item_replace:
                consume = true;
                break;
            case R.id.menu_item_delete:
                consume = true;
                break;
            default:
                consume = false;
                break;
        }
        return super.onContextItemSelected(item);
    }

    /**
     * @param menu     　Menu对象
     * @param v        触发ContextMenu的View 可以根据不同的View 加载不同的Menu配置
     * @param menuInfo
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        //获取打气筒
        MenuInflater inflater = getActivity().getMenuInflater();
        //解析菜单文件
        inflater.inflate(R.menu.context_menu, menu);
        menu.setHeaderTitle("Item 操作");
        super.onCreateContextMenu(menu, v, menuInfo);
//        menu.setHeaderIcon();
//        menu.setHeaderView()
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

    private class myItemDecoration extends RecyclerView.ItemDecoration {

    }

    private class testAdapter extends RecyclerView.Adapter<viewHolder> {
        private int size = 10;

        @Override
        public viewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            //解析作为ViewHolder的布局，即Item的布局
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_item, null);
            //并创建ViewHolder对象，返回
            return new viewHolder(view);
        }

        @Override
        public void onBindViewHolder(viewHolder holder, int position) {
            //对布局填充数据
            holder.textView.setText(".....");
        }

        @Override
        public int getItemCount() {
            //返回作为数据源的集合的大小
            return size;
        }
    }
}
