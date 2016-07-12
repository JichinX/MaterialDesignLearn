package com.graduation.xujicahng.materialdesignlearn;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.transition.Fade;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

/**
 * @author xujichang
 * @version 2016/6/24.
 */
public class ActivitySecond extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
            getWindow().setEnterTransition(new Fade());
            getWindow().setSharedElementEnterTransition(new Fade());
        }
        setContentView(R.layout.layout_activity_second);
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < 20; i++) {
            arrayList.add(String.valueOf(i + 1));
        }
        final CoordinatorLayout coordinatorLayout = (CoordinatorLayout) findViewById(R.id.main_container);
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayout.VERTICAL, false));
        recyclerView.setAdapter(new RecyclerAdapter(arrayList));
        findViewById(R.id.float_action_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Snackbar.make(coordinatorLayout, "This is Snack with Action", Snackbar.LENGTH_INDEFINITE).setAction("action", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ActivitySecond.this, "This is Toast", Toast.LENGTH_SHORT).show();
                    }
                }).show();
            }
        });
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
