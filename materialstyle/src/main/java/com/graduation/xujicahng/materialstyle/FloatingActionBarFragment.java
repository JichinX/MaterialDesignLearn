package com.graduation.xujicahng.materialstyle;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：MaterialDesignLearn
 * 类描述：XXXX.java是趣猜APP中XXXXXXX的类
 * 创建人：Administrator
 * 创建时间：2016/7/14 14:07
 * 修改人：Administrator
 * 修改时间：2016/7/14 14:07
 * 修改备注：
 */
public class FloatingActionBarFragment extends Fragment {
    private List<Palette.Swatch> swatches;
    private myAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_floating_action_bar, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        swatches = new ArrayList<>();
        adapter = new myAdapter(swatches);
        recyclerView.setAdapter(adapter);
        Drawable drawable = imageView.getDrawable();
        Bitmap bitamp = ((BitmapDrawable) drawable).getBitmap();
        Palette.Builder builder = new Palette.Builder(bitamp);
        builder.maximumColorCount(24);
        builder.generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                swatches.clear();
                swatches.addAll(palette.getSwatches());
                adapter.notifyDataSetChanged();
            }
        });
        return view;
    }

    private class myAdapter extends RecyclerView.Adapter<myViewHolder> {
        private List<Palette.Swatch> swatches;

        public myAdapter(List<Palette.Swatch> swatches) {
            this.swatches = swatches;
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_recycler_view_item, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 50);
            itemView.setLayoutParams(params);
            return new myViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            holder.text.setBackgroundColor(swatches.get(position).getRgb());
            holder.text.setText(String.valueOf(position));
            holder.text.setTextColor(swatches.get(position).getTitleTextColor());
        }

        @Override
        public int getItemCount() {
            return swatches.size();
        }
    }

    private class myViewHolder extends RecyclerView.ViewHolder {
        private TextView text;

        public myViewHolder(View itemView) {
            super(itemView);
            text = (TextView) itemView.findViewById(R.id.text);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

    }
}
