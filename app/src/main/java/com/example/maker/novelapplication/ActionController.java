package com.example.maker.novelapplication;

import android.content.SharedPreferences;
import android.util.Log;

import com.example.maker.novelapplication.activity.ReadingActivity;
import com.example.maker.novelapplication.bean.Book;
import com.example.maker.novelapplication.bean.Chapter;
import com.example.maker.novelapplication.bean.User;

import java.util.List;

public class ActionController {
    public static String historyString = "";
    public static String markString = "";
    public static SharedPreferences loginShared;
    public static boolean isLogin;
    public static User user;
    public static boolean loginCheck = false;
    public static Book selectedBook;
    public static List<Chapter> selectedChapterData;
    public static Chapter selectedChapter;
    public static ReadingActivity readingActivity;
    public static List<String> singlePageData = null;
    public static String getHistoryString(){
        return loginShared.getString("savedBook","");
    }
    public static String setUserMark(boolean flag){
        //flag 判断是收藏还是取消收藏
        String info = "";
        if(flag){
            if(ActionController.user.getMark().equals("")){
                info = info+ActionController.selectedBook.getId();
            }else {
                info = ActionController.selectedBook.getId()+","+ActionController.user.getMark();
            }
        }else {

            String[] args = ActionController.user.getMark().split(",");

            for(int i=0;i<args.length;i++){
                if(args[i].equals( ActionController.selectedBook.getId())){
                    continue;
                }
                if(info.equals("")){
                    info = info+args[i];
                }else {
                    info = info+","+args[i];
                }
            }
        }

        return info;
    }

    public static String saveBookReadHistory(String id){
        String reslut="";
        String savedBook = loginShared.getString("savedBook","");
        if(savedBook.contains(id)){
            reslut = "this book has been saved";
        }else {
            if(savedBook.equals("")){
                savedBook = savedBook + id;
            }else {
                savedBook = savedBook + ","+id;
            }
            SharedPreferences.Editor editor = loginShared.edit();
            editor.putString("savedBook",savedBook);
            editor.commit();
            reslut = "saved successed";
        }
        return  reslut;
    }
}
