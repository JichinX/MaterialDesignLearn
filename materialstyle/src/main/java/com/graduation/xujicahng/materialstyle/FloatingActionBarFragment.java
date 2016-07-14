package com.graduation.xujicahng.materialstyle;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

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
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_floating_action_bar, null);
        ImageView imageView = (ImageView) view.findViewById(R.id.image);
        Drawable drawable = imageView.getDrawable();
//        Palette.Builder builder = new Palette.Builder(drawable);
        return view;
    }
}
