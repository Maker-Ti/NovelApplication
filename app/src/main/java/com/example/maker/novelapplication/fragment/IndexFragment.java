package com.example.maker.novelapplication.fragment;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.activity.BookInfoActivity;
import com.example.maker.novelapplication.apdater.BookListAdapter;
import com.example.maker.novelapplication.apdater.MyAdapter;
import com.example.maker.novelapplication.bean.Book;
import com.example.maker.novelapplication.intface.DoView;
import com.example.maker.novelapplication.request.GetBookList_Request;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.content.Context.INPUT_METHOD_SERVICE;

public class IndexFragment extends Fragment {
    private ViewPager mainContent;
    private int[] imageIds = {R.mipmap.img1,R.mipmap.img2, R.mipmap.img3};
    private Timer timer;
    private TimerTask timerTask;
    private int myIndex = 0;
    private List<View> viewList;
    private List<TextView> rbList;
    private List<Book> data;
    private List<Book> screenData;
    private ListView bookList;
    private BookListAdapter bookListAdapter;
    private int[] rbIds = {R.id.tv1,R.id.tv2,R.id.tv3};
    private EditText ed_search;
    private Button tv_search;
    private RelativeLayout re_content;
    private TextView tv_searchinginfo;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_index,null);
        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {

        loadViewPage(view);
        loadList(view);
        loadSearch(view);
    }

    private void loadSearch(View view) {
        ed_search = view.findViewById(R.id.ed_search);

        re_content = view.findViewById(R.id.re_content);
        tv_search = view.findViewById(R.id.btn_search);
        tv_searchinginfo = view.findViewById(R.id.tv_searchinfo);
        tv_search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                searchBook();
            }
        });
    }
    private void searchBook(){
        String str = ed_search.getText().toString();
        String info = "";
        if(str.equals("")){
            int max = 5;
            if(data.size()<5){
                max = data.size();
            }
            screenData = new ArrayList<>();
            for(int i=0;i<max;i++){
                screenData.add(data.get(i));
            }
            bookListAdapter = new BookListAdapter(screenData,getContext());
            bookList.setAdapter(bookListAdapter);
            info = "最新上架";
        }else {
            screenData = new ArrayList<>();
            for(int i=0;i<data.size();i++){
                if(data.get(i).getName().contains(str)||data.get(i).getCreaotrname().contains(str)){
                    screenData.add(data.get(i));
                }
            }
            bookListAdapter = new BookListAdapter(screenData,getContext());
            bookList.setAdapter(bookListAdapter);
            info = "搜索:"+str;
        }
        tv_searchinginfo.setText(info);
        InputMethodManager imm = (InputMethodManager) getActivity()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(re_content.getWindowToken(), 0);
        Toast.makeText(getContext(), info, Toast.LENGTH_SHORT).show();

    }
    private void loadList(View view) {
        bookList = view.findViewById(R.id.book_list);
        String params = "status=1";
        GetBookList_Request getBookList_request = new GetBookList_Request(getContext(),params);
        getBookList_request.conn(new DoView() {
            @Override
            public void setSuccessfulView(Object object) {
                data = (List<Book>) object;
                screenData = new ArrayList<>();
                int max = 5;
                if(data.size()<5){
                    max = data.size();
                }
                for(int i=0;i<max;i++){
                    screenData.add(data.get(i));
                }
                bookListAdapter = new BookListAdapter(screenData,getContext());
                bookList.setAdapter(bookListAdapter);
            }

            @Override
            public void setFiledView(Object object) {

            }
        });
        bookList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                startActivity(new Intent(getContext(), BookInfoActivity.class));
                ActionController.selectedBook = screenData.get(i);
                Log.e("makerLog","selectedBook="+ActionController.selectedBook.getId());
            }
        });
    }

    private void loadViewPage(View views) {
        mainContent = views.findViewById(R.id.mainContent);
        viewList = new ArrayList<>();
        rbList = new ArrayList<>();
        for(int i=0;i<3;i++){
            View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_viewpager,null);
            ImageView imageView = view.findViewById(R.id.img);
            imageView.setBackgroundResource(imageIds[i]);
            final int finalI = i;
            viewList.add(view);
            TextView radioButton = views.findViewById(rbIds[i]);
            rbList.add(radioButton);
        }
        Log.e("makerLog","imgList size:"+viewList.size());
        mainContent.setAdapter(new MyAdapter(viewList,getContext()));
        timer = new Timer();
        timerTask = new TimerTask() {
            @Override
            public void run() {
                handler.sendEmptyMessage(0);
            }
        };
        timer.schedule(timerTask,0,2000);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            if(myIndex<2){
                myIndex++;
            }else {
                myIndex = 0;
            }
            changeView(myIndex);
        }
    };
    private void changeView(int index){
        mainContent.setCurrentItem(index);
        changeRBChecked(index);
    }
    private void changeRBChecked(int index){
        for(int i=0;i<3;i++){
            rbList.get(i).setBackgroundResource(R.drawable.grey_cicle_bc);
        }
        rbList.get(index).setBackgroundResource(R.drawable.white_cicle_bc);
    }
}
