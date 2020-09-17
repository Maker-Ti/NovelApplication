package com.example.maker.novelapplication.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.activity.BookInfoActivity;
import com.example.maker.novelapplication.apdater.BookGridAdapter;
import com.example.maker.novelapplication.apdater.MyAdapter;
import com.example.maker.novelapplication.bean.Book;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.GetBookByStr_Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class BookFragment extends Fragment {
    private GridView markGird,historyGird;
    private TextView tv_loginCheck;
    private List<Book> historyData,markData;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_book,null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        loadView(view);

    }

    @Override
    public void onResume() {

        if(ActionController.getHistoryString().equals("") == false){
           getHistoryGird();
        }else {
            getMarkGird();
        }

        super.onResume();
    }
    private void getHistoryGird(){
        GetBookByStr_Request getBookByStr_request = new GetBookByStr_Request(getContext(),"str="+ActionController.getHistoryString());
        getBookByStr_request.conn(new DoView() {
            @Override
            public void setSuccessfulView(Object object) {
                historyData = (List<Book>) object;
                BookGridAdapter adapter = new BookGridAdapter(historyData,getContext());
                historyGird.setAdapter(adapter);
                getMarkGird();
            }

            @Override
            public void setFiledView(Object object) {

            }
        });
    }
    private void getMarkGird(){
        if(ActionController.loginCheck){
            if(ActionController.user.getMark().equals("") == false){
                GetBookByStr_Request getBookByStr_request = new GetBookByStr_Request(getContext(),"str="+ActionController.user.getMark());
                getBookByStr_request.conn(new DoView() {
                    @Override
                    public void setSuccessfulView(Object object) {
                        markData = (List<Book>) object;
                        BookGridAdapter adapter = new BookGridAdapter(markData,getContext());
                        markGird.setAdapter(adapter);
                    }

                    @Override
                    public void setFiledView(Object object) {

                    }
                });
            }

        }
    }

    private void loadView(View view) {
        tv_loginCheck = view.findViewById(R.id.mark_login);
        historyGird = view.findViewById(R.id.history_gird);
        markGird = view.findViewById(R.id.mark_gird);
        if(ActionController.loginCheck){
            tv_loginCheck.setVisibility(View.GONE);
        }else {
            markGird.setVisibility(View.GONE);
        }
        historyGird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActionController.selectedBook = historyData.get(i);
                startActivity(new Intent(getContext(), BookInfoActivity.class));
            }
        });
        markGird.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActionController.selectedBook = markData.get(i);
                startActivity(new Intent(getContext(), BookInfoActivity.class));
            }
        });
    }


}
