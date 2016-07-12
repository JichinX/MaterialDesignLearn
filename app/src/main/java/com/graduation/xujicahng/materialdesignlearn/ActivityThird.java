package com.graduation.xujicahng.materialdesignlearn;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Explode;
import android.transition.Fade;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * @author xujichang
 * @version 2016/6/24.
 */
public class ActivityThird extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Explode());
            getWindow().setSharedElementEnterTransition(new Fade());
        }
        setContentView(R.layout.layout_activity_third);
        CollapsingToolbarLayout collapsingToolbarLayout =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_layout);
        collapsingToolbarLayout.setTitle("Title");
        collapsingToolbarLayout.setExpandedTitleColor(Color.GREEN);//设置还没收缩时状态下字体颜色
        collapsingToolbarLayout.setCollapsedTitleTextColor(Color.WHITE);//设置收缩后Toolbar上字体的颜色
        collapsingToolbarLayout.setCollapsedTitleGravity(Gravity.CENTER_HORIZONTAL);
        collapsingToolbarLayout.setExpandedTitleGravity(Gravity.CENTER_HORIZONTAL);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(String.valueOf(i + 1));
        }
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(arrayList));
    }

    private class RecyclerAdapter extends RecyclerView.Adapter<ViewHolder> {
        private ArrayList<String> arrayList;

        public RecyclerAdapter(ArrayList<String> arrayList) {
            this.arrayList = arrayList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = getLayoutInflater().inflate(R.layout.layout_recycler_item_view, null);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            holder.itemView.setLayoutParams(params);
            holder.text.setText(arrayList.get(position));
        }

        @Override
        public int getItemCount() {
            return arrayList.size();
        }
    }

    private class ViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public ViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.item_text);
        }
    }
}
