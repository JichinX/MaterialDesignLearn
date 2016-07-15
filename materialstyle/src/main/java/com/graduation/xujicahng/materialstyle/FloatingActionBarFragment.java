package com.graduation.xujicahng.materialstyle;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.graphics.Palette;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        swatches = new ArrayList<>();
        adapter = new myAdapter(swatches);
        recyclerView.setAdapter(adapter);
        Drawable drawable = imageView.getDrawable();
        //通过Drawable 转换
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        //通过资源获取
//        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ic_image4);

//        Palette.Builder builder = new Palette.Builder(bitmap);
//        builder.generate(new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                swatches.clear();
//                swatches.addAll(palette.getSwatches());
//                adapter.notifyDataSetChanged();
//            }
//        });
//        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//                swatches.clear();
//                swatches.addAll(palette.getSwatches());
//                adapter.notifyDataSetChanged();
//            }
//        });
        //异步方法 需在线程内执行 默认样本数为16 可设置
//        Palette.generate(bitmap);
//        Palette.generate(bitmap, 24);//设置样本数为24
//        //同步方法 通过回调
//        Palette.generateAsync(bitmap, new Palette.PaletteAsyncListener() {
//            @Override
//            public void onGenerated(Palette palette) {
//
//            }
//        });
//        //同样可以设置 样本数
//        Palette.generateAsync(bitmap, 24, new Palette.PaletteAsyncListener() {
//
//            @Override
//            public void onGenerated(Palette palette) {
//
//            }
//        });
        Palette.from(bitmap).generate(new Palette.PaletteAsyncListener() {
            @Override
            public void onGenerated(Palette palette) {
                swatches.clear();
                swatches.addAll(palette.getSwatches());
                adapter.notifyDataSetChanged();

//                /**
//                 *Vibrant(充满活力的)
//                 *Vibrant dark(充满活力的黑)
//                 *Vibrant light(充满活力的亮)
//                 *Muted(柔和的)
//                 *Muted dark(柔和的黑)
//                 *Muted lighr(柔和的亮)
//                 */
//                palette.getDarkMutedSwatch();
//                palette.getDarkVibrantSwatch();
//                palette.getLightMutedSwatch();
//                palette.getLightVibrantSwatch();
//                palette.getMutedSwatch();
//                palette.getVibrantSwatch();
                Palette.Swatch swatch = palette.getVibrantSwatch();
                setStatusBarColor(swatch);
                setActionBarColor(swatch);

            }
        });
        return view;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setStatusBarColor(Palette.Swatch swatch) {
        Window window = getActivity().getWindow();
        window.setStatusBarColor(colorBurn(swatch.getRgb()));
//        window.setNavigationBarColor(swatch.getRgb());
    }

    private void setActionBarColor(Palette.Swatch swatch) {
        if (getActivity() instanceof AppCompatActivity) {
            ActionBar actionBar = ((AppCompatActivity) getActivity()).getSupportActionBar();
            if (null != actionBar) {
                actionBar.setBackgroundDrawable(new ColorDrawable(swatch.getRgb()));
            }
        }
    }

    private int colorBurn(int RGBValues) {
        int alpha = RGBValues >> 24;
        int red = RGBValues >> 16 & 0xFF;
        int green = RGBValues >> 8 & 0xFF;
        int blue = RGBValues & 0xFF;
        red = (int) Math.floor(red * (1 - 0.1));
        green = (int) Math.floor(green * (1 - 0.1));
        blue = (int) Math.floor(blue * (1 - 0.1));
        return Color.rgb(red, green, blue);
    }

    private class myAdapter extends RecyclerView.Adapter<myViewHolder> {
        private List<Palette.Swatch> swatches;

        public myAdapter(List<Palette.Swatch> swatches) {
            this.swatches = swatches;
        }

        @Override
        public myViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_palette_test, null);
            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            itemView.setLayoutParams(params);
            return new myViewHolder(itemView);
        }

        @Override
        public void onBindViewHolder(myViewHolder holder, int position) {
            Palette.Swatch swatch = swatches.get(position);

            //获得该颜色在HSL(HSL色彩模式是工业界的一种颜色标准,色相(H)、饱和度(S)、明度(L))标准的显示值;
            swatch.getHsl();
            //android建议的一个用于任何body的颜色
            swatch.getBodyTextColor();
            //android建议的一个用于任何标题的颜色
            swatch.getTitleTextColor();
            //该颜色样本的颜色
            swatch.getRgb();
            // the amount of pixels which this swatch
            swatch.getPopulation();


            holder.bg.setBackgroundColor(swatch.getRgb());
            holder.textWithTitleColor.setText(new StringBuilder("TitleTextColor ").append(String.valueOf(position)).append(" ").append(String.valueOf(swatch.getPopulation())));
            holder.textWithBodyColor.setText(new StringBuilder("BodyTextColor ").append(String.valueOf(position)).append(" ").append(String.valueOf(swatch.getPopulation())));
            holder.textWithBodyColor.setTextColor(swatch.getBodyTextColor());
            holder.textWithTitleColor.setTextColor(swatch.getTitleTextColor());
        }

        @Override
        public int getItemCount() {
            return swatches.size();
        }
    }

    private class myViewHolder extends RecyclerView.ViewHolder {
        private TextView textWithTitleColor;
        private TextView textWithBodyColor;
        private LinearLayout bg;

        public myViewHolder(View itemView) {
            super(itemView);
            textWithTitleColor = (TextView) itemView.findViewById(R.id.text_title_color);
            textWithBodyColor = (TextView) itemView.findViewById(R.id.text_body_text_color);
            bg = (LinearLayout) itemView.findViewById(R.id.bg_card);
        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();

    }
}
