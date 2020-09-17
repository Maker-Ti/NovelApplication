package com.example.maker.novelapplication.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.annotation.Nullable;

import com.example.maker.novelapplication.ActionController;
import com.example.maker.novelapplication.R;
import com.example.maker.novelapplication.apdater.ChapterListAdapter;
import com.example.maker.novelapplication.view.ReadingText;

import java.util.ArrayList;
import java.util.List;

public class ReadingActivity extends Activity {
    private TextView tv_title,tv_page,tv_catalog;
    private ReadingText mainText;
    private List<String> chapterLineData = new ArrayList<>();
    private int maxPage = 0;
    public int chinaWidth,englishWidth;
    public int windowWidth,windowHeight;
    private Paint mainPaint;
    private int lineSize;
    private float touchX,touchY;
    private int pageIndex = 0;
    private int chapterIndex;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reading);
        sharedPreferences = getSharedPreferences(ActionController.selectedBook.getId(),MODE_PRIVATE);
        editor = sharedPreferences.edit();
        ActionController.readingActivity = this;
        initView();
        initData();
    }

    private void initData() {
        tv_catalog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showCatalogDialog();
            }
        });
    }
    public void ReadingTextCallBack(int chinaWidth,int englishWidth,int windowWidth,int windowHeight,int lineSize){
        this.chinaWidth = chinaWidth;
        this.englishWidth = englishWidth;
        this.windowWidth = windowWidth;
        this.windowHeight = windowHeight;
        this.lineSize = lineSize;
        mainText = findViewById(R.id.mainText);
        mainText.setOnTouchListener(onTouchListener);
        int readingChapter = sharedPreferences.getInt("chapter",0);
        ActionController.selectedChapter = ActionController.selectedChapterData.get(readingChapter);
        pageIndex = sharedPreferences.getInt("page",0);
        loadChapterContent();
        changePage(pageIndex);
    }
    public  void saveBookReading(int chapter,int page){
        editor.putInt("chapter",chapter);
        editor.putInt("page",page);
        editor.commit();
    }
    private void showCatalogDialog(){
        final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_chapterlist,null);
        alertDialog.setView(view);
        ListView listView = view.findViewById(R.id.list_chapter);
        ChapterListAdapter adapter = new ChapterListAdapter(this);
        listView.setAdapter(adapter);
        listView.setSelection(ActionController.selectedChapter.getIndex());
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                ActionController.selectedChapter = ActionController.selectedChapterData.get(i);
                pageIndex = 0;
                loadChapterContent();
                changePage(pageIndex);
                alertDialog.dismiss();
                Toast.makeText(ReadingActivity.this, "第"+(i+1)+"章", Toast.LENGTH_SHORT).show();
            }
        });
        alertDialog.show();
    }
    private void loadChapterContent(){
        chapterLineData = new ArrayList<>();
        String singleContent = ActionController.selectedChapter.getContent();
        String line = "";
        int width = 0;
        for (int i=0;i<singleContent.length();i++) {
            char a = singleContent.charAt(i);
            int singleWdith = 0;
            if (a == '\n') {
                width = 0;
                chapterLineData.add(line);
                line = "";
                continue;
            }
            if (a >= 0 && a <= 126) {
                singleWdith = englishWidth;
            } else {
                singleWdith = chinaWidth;
            }
            if((windowWidth-width)>2*singleWdith){
                line+=a;
                width+=singleWdith;
                if(i == singleContent.length()-1){
                    chapterLineData.add(line);
                }
            }else {
                width = 0;
                chapterLineData.add(line);
                line = ""+a;
            }
        }
        int max = chapterLineData.size()%lineSize;
        if(max == 0){
            maxPage = chapterLineData.size()/lineSize;
        }else {
            maxPage = chapterLineData.size()/lineSize+1;
        }
        Log.e("makerLog","dataSize"+chapterLineData.size()+",maxline:"+lineSize);
        tv_title.setText("第"+(ActionController.selectedChapter.getIndex()+1)+"章  "+ActionController.selectedChapter.getTitle());
    }
    private void initView() {
        tv_catalog = findViewById(R.id.tv_catalog);
        tv_title = findViewById(R.id.tv_title);
        tv_page = findViewById(R.id.tv_page);
    }

    private void changeIndex(int index){
        tv_page.setText(""+index+"/"+maxPage);
    }
    public void changePage(int index) {
        if(pageIndex<0){
            if(ActionController.selectedChapter.getIndex() == 0){
                Toast.makeText(this, "已经是第一章了", Toast.LENGTH_SHORT).show();
                pageIndex = 0;
            }else {
                ActionController.selectedChapter = ActionController.selectedChapterData.get(ActionController.selectedChapter.getIndex()-1);
                loadChapterContent();
                pageIndex = maxPage-1;
                changePage(pageIndex);
                Toast.makeText(this, "第"+(ActionController.selectedChapter.getIndex()+1)+"章", Toast.LENGTH_SHORT).show();
            }
            return;
        }else if(pageIndex>=maxPage){
            if(ActionController.selectedChapter.getIndex() == (ActionController.selectedChapterData.size()-1)){
                Toast.makeText(this, "已经是最后一章了", Toast.LENGTH_SHORT).show();
                pageIndex = maxPage-1;
            }else {
                ActionController.selectedChapter = ActionController.selectedChapterData.get(ActionController.selectedChapter.getIndex()+1);
                loadChapterContent();
                pageIndex = 0;
                changePage(pageIndex);
                Toast.makeText(this, "第"+(ActionController.selectedChapter.getIndex()+1)+"章", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        List<String> onePageData = new ArrayList<>();
        int max = 0;
        if(chapterLineData.size()-index*lineSize>=lineSize){
            max = index*lineSize+lineSize;
        }else {
            max = chapterLineData.size();
        }
        for(int i=index*lineSize;i<max;i++){
            if(chapterLineData.get(i)!=null){
                onePageData.add(chapterLineData.get(i));
            }
        }
        changeIndex(index+1);
        ActionController.singlePageData = onePageData;
        mainText.invalidate();
        saveBookReading(ActionController.selectedChapter.getIndex(),index);
    }
    View.OnTouchListener onTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if(event.getAction() == MotionEvent.ACTION_DOWN){
                touchX = event.getX();
                touchY = event.getY();
            }
            if(event.getAction() == MotionEvent.ACTION_UP){
                Log.e("makerLog","up");
                float x = event.getX();
                float y = event.getY();
                float num = 0;

                num = touchX - x;
                Log.e("makerLog","touch:"+touchX+","+x+","+windowWidth/2);
                if(num<50&&num>-50){
                    if(touchX>windowWidth/2){
                        changePage(++pageIndex);
                    }else {
                        changePage(--pageIndex);
                    }
                }else if(num>50){
                    changePage(++pageIndex);
                }else {
                    changePage(--pageIndex);
                }
            }
            return true;
        }
    };

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        ActionController.singlePageData = null;
    }
}
