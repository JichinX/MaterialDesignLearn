package com.graduation.xujicahng.materialstyle;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * 项目名称：MaterialDesignLearn
 * 类描述：XXXX.java是趣猜APP中XXXXXXX的类
 * 创建人：Administrator
 * 创建时间：2016/7/12 13:55
 * 修改人：Administrator
 * 修改时间：2016/7/12 13:55
 * 修改备注：
 */
public class MainFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_main, null);
        return view;
    }
}
