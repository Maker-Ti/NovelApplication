package com.example.maker.novelapplication.apdater;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import java.util.ArrayList;


public class Fragment_pager extends FragmentPagerAdapter {
    ArrayList<Fragment> list;

    //通过构造获取list集合
    public Fragment_pager(FragmentManager fm, ArrayList<Fragment> list) {
        super(fm);
        this.list=list;
    }
    //设置每一个的内容
    @Override
    public Fragment getItem(int arg0) {
        // TODO Auto-generated method stub
        return list.get(arg0);
    }
    //设置有多少内容
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }
}
